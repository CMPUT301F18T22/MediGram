package medigram.medigram;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * This activity receives a problem from ProblemListActivity, and edits its contents.
 * When it is finished, the problem is bundled back to its Parent activity.
 *
 * @author Jarred
 */
public class EditProblemActivity extends AppCompatActivity {
    private Problem chosenProblem;
    private TextView dateTextView;
    private DatePickerDialog.OnDateSetListener dateListener;
    private Button confirmBtn;
    private static final int CAMERA_REQUEST = 1888;
    private static final int cameraCode = 100;
    private static final int cameraCode2 = 101;
    private Uri imageUri1, imageUri2;
    private Bitmap photo1, photo2;
    private ImageButton problemPicBtn1, problemPicBtn2;
    private ByteArrayOutputStream stream;
    private String encodedImage;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == cameraCode) {
            try {
                photo1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri1);
                Photo serialPhoto = new Photo(photo1);
                serialPhoto.compressor();
                chosenProblem.setBodyLocationPhoto(serialPhoto, 0);


                Log.d("Working", photo1.toString());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            Bitmap photo = chosenProblem.getBodyLocationPhoto(0).getBitmap();
            Log.d("Count", Integer.toString(photo.getByteCount()));
            problemPicBtn1.setImageBitmap(photo);


        }

        if (requestCode == cameraCode2) {
            try {
                photo2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri2);
                Photo serialPhoto = new Photo(photo2);
                serialPhoto.compressor();
                chosenProblem.setBodyLocationPhoto(serialPhoto, 1);


                Log.d("Working", photo2.toString());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Bitmap photo = chosenProblem.getBodyLocationPhoto(1).getBitmap();
            Log.d("Count", Integer.toString(photo.getByteCount()));
            problemPicBtn2.setImageBitmap(photo);


        }
        else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(EditProblemActivity.this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        setContentView(R.layout.activity_edit_problem);

        // Get problem list from the bundle from ProblemListActivity

        chosenProblem = (Problem) getIntent().getSerializableExtra("chosenProblem");

        // if problem contents are blank, then it is a new problem and we text box hints
        // if it is not blank, we display its contents in the text boxes
        if (chosenProblem.getProblemTitle() != "") {
            ((TextView) findViewById(R.id.problemTitle)).setText(chosenProblem.getProblemTitle());
            ((TextView) findViewById(R.id.problemDescription)).setText(chosenProblem.getDescription());
            ((TextView) findViewById(R.id.problemBodyLocation)).setText(chosenProblem.getBodyLocation());
        }
        ((TextView) findViewById(R.id.problemDate)).setText(chosenProblem.getDateString());


        problemPicBtn1 = (ImageButton) findViewById(R.id.imageButton1);
        problemPicBtn2 = (ImageButton) findViewById(R.id.imageButton2);

        if (chosenProblem.getBodyLocationPhoto(0) != null) {
            try {
                Bitmap photo = chosenProblem.getBodyLocationPhoto(0).getBitmap();
                problemPicBtn1.setImageBitmap(photo);
            } catch (Exception ex) {
            }
        }
        if (chosenProblem.getBodyLocationPhoto(1) != null) {
            try {
                Bitmap photo = chosenProblem.getBodyLocationPhoto(1).getBitmap();
                problemPicBtn2.setImageBitmap(photo);
            } catch (Exception ex) {
            }
        }



        problemPicBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageUri1 = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()
                        ,"problemPhoto1.jpg"));
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri1);
                startActivityForResult(cameraIntent, cameraCode);
            }
        });

        problemPicBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageUri2 = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()
                        ,"problemPhoto2.jpg"));
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri2);
                startActivityForResult(cameraIntent, cameraCode2);
            }
        });


        // confirmBtn saves all text to the Problem and returns to parent activity
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText problemTitle = (EditText) findViewById(R.id.problemTitle);
                chosenProblem.setProblemTitle(problemTitle.getText().toString());

                EditText description = (EditText) findViewById(R.id.problemDescription);
                chosenProblem.setDescription(description.getText().toString());

                EditText bodyLocation = (EditText) findViewById(R.id.problemBodyLocation);
                chosenProblem.setBodyLocation(bodyLocation.getText().toString());

                chosenProblem.setDateStarted(dateTextView.getText().toString());

                Intent openEditor = new Intent();
                Bundle problem_bundle = new Bundle();
                problem_bundle.putSerializable("editedProblem", chosenProblem);
                openEditor.putExtras(problem_bundle);

                setResult(Activity.RESULT_OK, openEditor);
                finish();
            }
        });
        // display a date dialog when entering the date, so that entered date is always
        // in the correct format
        dateTextView = (TextView) findViewById(R.id.problemDate);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditProblemActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListener,
                        year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();


            }
        });
        // when user is done choosing date, dateListener enters the date into the text box
        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                dateTextView.setText(day+"/"+month+"/"+year);
            }
        };


    }
    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


}
