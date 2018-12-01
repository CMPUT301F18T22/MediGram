package medigram.medigram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditRecordActivity extends AppCompatActivity {
    private Button edittitle;
    private Button editpicture;
    private Button editcomment;
    private Button editdate;
    private Button editgeolocation;
    private Button showmap;
    private LocationManager locationManager;
    private String provider;
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private EditText commentEditText;
    private EditText titleEditText;
    private Comment comment;
    private Patient patient;



    //getting the specific record from record view
    private Record editrecord = (Record )getIntent().getSerializableExtra("Record");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        edittitle = findViewById(R.id.edit_title);
        editpicture = findViewById(R.id.edit_picture);
        editcomment = findViewById(R.id.edit_comment);
        editgeolocation = findViewById(R.id.edit_geolocation);
        editdate = findViewById(R.id.edit_dates);
        showmap = findViewById(R.id.map_view);




//handle the edit title,suppose to jump to the edit record title activity and user can edit the title and then update the record
        edittitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditRecordTitle .class);
                intent.putExtra("socool", editrecord);
                startActivity(intent);
            }
        });

        //handle the edit picture activity the user  can go to the galary and view all the picture that have been taken and try to add or delete picture
        //not sure useing gallery or new activity  need discussion
        editpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecordEditPicture .class);
                intent.putExtra("socool1", editrecord);
                startActivity(intent);
            }
        });



        editcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecordEditComment.class);
                intent.putExtra("socool", editrecord);
                startActivity(intent);

            }
        });

        editgeolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                // get the avilable geolocation provider
                List<String> providerList = locationManager.getProviders(true);
                if (providerList.contains(LocationManager.GPS_PROVIDER)) {
                    provider = LocationManager.GPS_PROVIDER;
                } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                    provider = LocationManager.NETWORK_PROVIDER;
                } else {
                    // when there is no geolocation provider
                    Toast.makeText(getApplicationContext(), "No location provider to use", Toast.LENGTH_SHORT).show();
                    return;
                }
                Activity activity = (Activity) getApplicationContext();

                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
                }

                Location location = locationManager.getLastKnownLocation(provider);
                Double longitude = location.getLongitude();
                Double latitude = location.getLatitude();
                ArrayList<Double> geoLocation1 = new ArrayList<>();
                geoLocation1.add(latitude);
                geoLocation1.add(longitude);
                editrecord.setGeoLocation(geoLocation1);

            }
        });

        editdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String comments = commentEditText.getText().toString();
                editrecord = new Record(title, new Date());
                comment = new Comment(comments, patient.getUserID());
                Intent intent = new Intent();
                intent.putExtra("newRecord", editrecord);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditRecordDisplayMap.class);
                intent.putExtra("socool", editrecord);
                startActivity(intent);

            }
        });



    }
}
