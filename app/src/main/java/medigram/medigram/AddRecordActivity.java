package medigram.medigram;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddRecordActivity extends Activity {
    /**
     * This activity displays the problems specified by the user as a list. The list of problems is
     * retrieved from the User given by the parent activity, and then filtered by keywords. Adding or
     * editing a problem is done by a child activity.
     * After adding or editing is done, the User data is updated over the network if available.
     *
     * @author Jiaqi Liu, Zeyu Liu
     */

    private Problem problem;
    private Button geolocation;
    private Button addcomment;
    private Button addpicture;
    private Button adddate;
    private AccountManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        am = new AccountManager(getApplicationContext());
        addcomment = findViewById(R.id.Comment_Add);
        geolocation = findViewById(R.id.Geo_location);
        addpicture = findViewById(R.id.Picture_Add);
        adddate = findViewById(R.id.Date);


        // get current problem
        problem = Patient.getProblems().getProblem(0);
        Record newrecord = new Record();

        // add a new geolocation
        geolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationManager locationManager;
                String context = Context.LOCATION_SERVICE;
                locationManager = (LocationManager) getSystemService(context);
                String provider = LocationManager.GPS_PROVIDER;
                if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION );
                }
                Location location = locationManager.getLastKnownLocation(provider);
                Double longitude = location.getLongitude();
                Double latitude = location.getLatitude();
                ArrayList<Double> geoLocation = new ArrayList<>();
                geoLocation.add(latitude);
                geoLocation.add(longitude);
                newrecord.setGeoLocation(geolocation);
            }
        });

        addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //this part will have the same coding as the add comment for the care provider part

            }
        });

        addpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        adddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                newrecord.setDateStarted(timeStamp);
            }
        });



    }
}
