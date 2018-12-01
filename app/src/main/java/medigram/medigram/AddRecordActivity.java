package medigram.medigram;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static medigram.medigram.EditProblemActivity.verifyStoragePermissions;

public class AddRecordActivity extends AppCompatActivity {
    /**
     * This activity displays the problems specified by the user as a list. The list of problems is
     * retrieved from the User given by the parent activity, and then filtered by keywords. Adding or
     * editing a problem is done by a child activity.
     * After adding or editing is done, the User data is updated over the network if available.
     */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private Uri imageUri;
    private LocationManager locationManager;
    private String provider;
    public static final int TAKE_PHOTO = 1;
    private Button geolocation;
    private Button addpicture;
    private Button save;
    private EditText commentEditText;
    private EditText titleEditText;
    private AccountManager accoutmanager;
    private Problem problem;
    private Record newrecord;
    private Comment comment;
    private Patient patient;
    private Uri imageUri1, imageUri2;
    private Bitmap photo1, photo2;
    private int width = 300, height = 300;
    private Photo serialPhoto1, serialPhoto2;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };
    /**
     * Handles returning from camera activity.
     * @param data  Not used
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            photo1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        photo1 = Bitmap.createScaledBitmap(photo1, width, height, false);
        serialPhoto1 = new Photo(photo1);
        Toast.makeText(getApplicationContext(), "image has been saved", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(AddRecordActivity.this);
        setContentView(R.layout.activity_add_record);


        accoutmanager = new AccountManager(getApplicationContext());

        commentEditText = findViewById(R.id.commentText);
        titleEditText = findViewById(R.id.recordTitle);
        geolocation = findViewById(R.id.Geo_location);
        addpicture = findViewById(R.id.Picture_Add);
        save = findViewById(R.id.save);



        // get current problem(using intent from the record view part to get problem id and patient id )
        //problem = Patient.getProblems().getProblem(0);
        patient = (Patient) getIntent().getSerializableExtra("Patient");
        problem = (Problem )getIntent().getSerializableExtra("Problem");
        newrecord = new Record();

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
                newrecord.setGeoLocation(geoLocation1);
            }
        });


      //here we are going to take a picture use the camera
       addpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
             // build file to save the image
                String saveingname = "JPEG_Record" + timeStamp + ".jpg";
                imageUri1 = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()
                        ,saveingname));
                Intent intent = new Intent("android.media.action. IMAGE_CAPTURE");
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri1);
                startActivityForResult(intent, TAKE_PHOTO); // activite the camera
            }
        });

        //this is the part to get the date
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String comments = commentEditText.getText().toString();
                newrecord = new Record(title, new Date());
                comment = new Comment(comments, patient.getUserID());
                Intent intent = new Intent();
                intent.putExtra("newRecord", newrecord);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });



    }
}
