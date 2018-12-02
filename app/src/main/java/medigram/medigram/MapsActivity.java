package medigram.medigram;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;
    private Button saveButton;
    private LatLng currentLocation;
    private PlaceAutocompleteFragment autocompleteFragment;
    private Boolean permissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        saveButton = findViewById(R.id.saveMapBtn);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Log.d("CurrentLocation", currentLocation.toString());
                intent.putExtra("Location", currentLocation);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.getView().setBackgroundColor(0xFFe0e0e0);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i("Location AutoComplete", "Place: " + place.getName());

                currentLocation = place.getLatLng();

                if (!getIntent().hasExtra("All Locations")) {
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(currentLocation).title(place.getName().toString()));
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f));
            }

            @Override
            public void onError(Status status) {
                Log.i("Location AutoComplete", "An error occurred: " + status);
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionGranted = false;
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
            Log.d("Permission", "Location permission went through");
        }else{
            permissionGranted = true;
        }

        if (permissionGranted) {
            Log.d("Permission", "Location permission already granted");
            if (getIntent().hasExtra("All Locations")) {
                displayAllLocations();
            }

            else if (getIntent().hasExtra("Single Location")) {
                saveButton.setVisibility(View.GONE);
                currentLocation = getIntent().getParcelableExtra("Single Location");

                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Record Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f));
            }

            else{
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                                    mMap = googleMap;

                                    mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f));
                                }
                            }
                        });
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng clickedLocation) {
                        mMap.clear();
                        currentLocation = new MarkerOptions().position(clickedLocation).getPosition();

                        mMap.addMarker(new MarkerOptions().position(clickedLocation));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(clickedLocation, 15.0f));
                    }
                });
            }
        }
    }

    private void displayAllLocations() {
        saveButton.setVisibility(View.GONE);
        ArrayList<LatLng> locations = getIntent().getParcelableArrayListExtra("All Locations");
        ArrayList<Marker> markers = new ArrayList<>();

        for (LatLng latLng : locations){
            markers.add(mMap.addMarker(new MarkerOptions().position(latLng)));
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120));
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        if (requestCode == MY_PERMISSION_ACCESS_FINE_LOCATION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                permissionGranted = true;
                onMapReady(mMap);
            }
//                Log.d("Permission", "Location permission accepted");
//                if (getIntent().hasExtra("All Locations")){
//                    displayAllLocations();
//                }else {
//                    mFusedLocationClient.getLastLocation()
//                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                                @Override
//                                public void onSuccess(Location location) {
//                                    // Got last known location. In some rare situations this can be null.
//                                    if (location != null) {
//                                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
//
//                                        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
//                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f));
//                                    }
//                                }
//                            });
//                }
//            }else{
//                Log.d("Permission", "Location permission rejected");
//            }
        }
    }
}
