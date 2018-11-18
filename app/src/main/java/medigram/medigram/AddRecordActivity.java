package medigram.medigram;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddRecordActivity extends Activity {
    /**
     * This activity displays the problems specified by the user as a list. The list of problems is
     * retrieved from the User given by the parent activity, and then filtered by keywords. Adding or
     * editing a problem is done by a child activity.
     * After adding or editing is done, the User data is updated over the network if available.
     *
     * @author Jiaqi Liu
     */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private Uri imageUri;
    private LocationManager locationManager;
    private String provider;
    public static final int TAKE_PHOTO = 1;
    private Button geolocation;
    private Button addcomment;
    private Button addpicture;
    private Button adddate;
    private Button save;
    private AccountManager accoutmanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        accoutmanager = new AccountManager(getApplicationContext());
        addcomment = findViewById(R.id.Comment_Add);
        geolocation = findViewById(R.id.Geo_location);
        addpicture = findViewById(R.id.Picture_Add);
        adddate = findViewById(R.id.Date);
        save = findViewById(R.id.save);



        // get current problem(using intent from the record view part to get problem id and patient id )
        //problem = Patient.getProblems().getProblem(0);
        String patient1 = getIntent().getStringExtra("Patient");
        String problem = getIntent().getStringExtra("Problem");
        Record newrecord = new Record();

        // add a new geolocation
        geolocation.setOnClickListener(new View.OnClickListener() {
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

                /**
                 * ******************************************************************************************************************
                 * something wrong with this part
                 * the permission part , cannot use this as the contenxt
                 * if this part is deleted getLastKnowLocation will occur an error
                 */
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
                }
                //*******************************************************************************************************************


                Location location = locationManager.getLastKnownLocation(provider);
                Double longitude = location.getLongitude();
                Double latitude = location.getLatitude();
                ArrayList<Double> geoLocation1 = new ArrayList<>();
                geoLocation1.add(latitude);
                geoLocation1.add(longitude);
                newrecord.setGeoLocation(geoLocation1);
            }
        });

        //add comment part will be pretty similliar to the care provider add comment
        addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**this part will have the same coding as the add comment for the care provider part
                 *
                 */

            }
        });

        //here we are going to take a picture use the camera
        addpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
             // build file to save the image
                String saveingname = "JPEG_Record" + timeStamp + ".jpg";
                File outputImage = new File(Environment.
                        getExternalStorageDirectory(), saveingname);
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.media.action. IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO); // activite the camera
                newrecord.setPhotos(outputImage);
            }
        });

        //this is the part to get the date
        adddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                newrecord.setDateStarted(timeStamp);
            }
        });


        //this is the part to get the date
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accoutmanager.patientUpdater(patient1, Patient);
            }
        });



    }
}
