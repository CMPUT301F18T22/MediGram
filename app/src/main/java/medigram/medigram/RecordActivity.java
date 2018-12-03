package medigram.medigram;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecordActivity extends AppCompatActivity implements AddCommentDialog.AddCommentDialogListener, EditCommentDialog.EditCommentDialogListener{
    private Patient patient;
    private CareProvider careProvider;
    private Button addCommentBTN;
    private Comment comment;
    public ListView commentView;
    public List<String> commentListString;
    private Button addPicture;
    private Button addGeo;
    private Button viewPicture, setTitle;
    private EditText recordTitle;
    private Record record;
    private CommentList commentList;
    private CommentListAdapter adapter;
    private Integer recordIndex;
    private Integer problemIndex;
    private AccountManager accountManager;
    private Integer index;
    private Integer editIndex;
    private static final int CAMERA_REQUEST = 1888;
    private static final int cameraCode = 100;
    private Bitmap photo;
    private Photo serialPhoto;
    private Uri imageUri;
    private int width = 480, height = 800;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent openEditor = new Intent();
        setResult(Activity.RESULT_OK, openEditor);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        accountManager = new AccountManager(getApplicationContext());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        addPicture = (Button) findViewById(R.id.addPicture);
        addCommentBTN = (Button) findViewById(R.id.addComment);
        addGeo = (Button) findViewById(R.id.addGeo);
        setTitle = (Button) findViewById(R.id.setTitleBtn);
        viewPicture = (Button) findViewById(R.id.viewPicture);
        recordTitle = (EditText) findViewById(R.id.recordTitle);

        // patientComment = (TextView) findViewById(R.id.patientComment);
        //careProviderComment = (TextView) findViewById(R.id.carproviderComment);
        Bundle extras = getIntent().getExtras();
        careProvider = (CareProvider) extras.getSerializable("CareProvider");
        patient = (Patient) extras.getSerializable("Patient");
        recordIndex = extras.getInt("RecordIndex", -1);
        problemIndex = extras.getInt("ProblemIndex", -1);
        record = patient.getProblems().getProblem(problemIndex).getRecordList().getRecord(recordIndex);
        commentList = record.getComments();
        commentView = (ListView) findViewById(R.id.commentListView);
        recordTitle.setText(record.getRecordTitle());
        commentView.setTextFilterEnabled(true);

        addCommentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        if (careProvider != null){
            recordTitle.setInputType(0);
            setTitle.setVisibility(View.GONE);
            addPicture.setVisibility(View.GONE);
        }

        if (record.getGeoLocation() != null ){
            if (careProvider != null){
                addGeo.setText("View Geo-location");
            }
            else {
                addGeo.setText("View/Edit Geo-location");
            }
        }else{
            if (careProvider != null){
                addGeo.setVisibility(View.GONE);
            }
            else {
                addGeo.setText("Add Geo-location");
            }
        }

        setTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("printbefore", patient.getProblems().getProblem(problemIndex).getRecordList().toString());
                record.setRecordTitle(recordTitle.getText().toString());
                accountManager.patientUpdater(patient.getUserID(), patient);
                Log.d("printafter", patient.getProblems().getProblem(problemIndex).getRecordList().toString());
                Toast.makeText(RecordActivity.this,
                        "Title updated successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });


        addGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (record.getGeoLocation() != null){
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("Single Location", record.getGeoLocation());
                    intent.putExtra("CareProvider", getIntent().hasExtra("CareProvider"));
                    startActivityForResult(intent, 1);
                }else{
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("CareProvider", getIntent().hasExtra("CareProvider"));
                    startActivityForResult(intent, 1);
                }
            }
        });

        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()
                        ,patient.getJestID() + Integer.toString(record.getPhotos().size()) + ".jpg"));
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, cameraCode);
            }
        });

        viewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(getApplicationContext(), GalleryActivity.class);
                gallery.putExtra("record", record);
//                gallery.putExtra("patient", patient);
                gallery.putExtra("recordIndex", recordIndex);
                gallery.putExtra("problemIndex", problemIndex);
                startActivity(gallery);
            }
        });


        commentListString = commentList.getList().stream().map(Comment::toString).collect(Collectors.toList());
        adapter = new RecordActivity.CommentListAdapter(this, R.layout.comments_list_items, commentListString);
        //commentString.add("sdfsss");
        //ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,commentString );

        commentView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (requestCode == 1 ) {
            if (resultCode == RESULT_OK) {
                LatLng location = intent.getParcelableExtra("Location");
                record.setGeoLocation(location);
                addGeo.setText("View Geo-location");
                accountManager.patientUpdater(patient.getUserID(), patient);
            }
        }
        if (requestCode == cameraCode && resultCode == RESULT_OK) {
            try {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                photo = Bitmap.createScaledBitmap(photo, width, height, false);
                serialPhoto = new Photo(photo);

                record.getPhotos().add(serialPhoto);
                accountManager.patientUpdater(patient.getUserID(), patient);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void openDialog(){
        AddCommentDialog addCommentDialog = new AddCommentDialog();
        addCommentDialog.show(getSupportFragmentManager(),"addComment dialog");
    }

    @Override
    public void applyTexts(String commentEntered) {
        //record = patient.getProblems().getProblem(problemIndex).getRecordList().getRecord(recordIndex);
        //commentList = record.getComments();
        if (getIntent().hasExtra("CareProvider")) {
            comment = new Comment(commentEntered,careProvider.getUserID());
            commentList.addComment(comment);
            adapter.add(comment.toString());
            adapter.notifyDataSetChanged();
            accountManager.patientUpdater(patient.getUserID(), patient);
            accountManager.careProviderUpdater(careProvider.getUserID(), careProvider);
            Log.d("checkComment1",commentList.getList().toString());
        }
        else{
            comment = new Comment(commentEntered,patient.getUserID());
            commentList.addComment(comment);
            adapter.add(comment.toString());
            adapter.notifyDataSetChanged();
            accountManager.patientUpdater(patient.getUserID(), patient);

        }
        Toast.makeText(RecordActivity.this,
                "Comments added successfully",
                Toast.LENGTH_SHORT).show();
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

    private class CommentListAdapter extends ArrayAdapter<String> {
        private int layout;

        private CommentListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            RecordActivity.ViewHolder mainViewholder = null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);

                RecordActivity.ViewHolder viewHolder = new RecordActivity.ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.titleRecord);
                viewHolder.commentText = (TextView) convertView.findViewById(R.id.list_item_comment);
                viewHolder.deleteBtn = (Button) convertView.findViewById(R.id.deleteButton);
                viewHolder.editBtn = (Button) convertView.findViewById(R.id.editButton);
                convertView.setTag(viewHolder);
                // Remove these buttons if user is a Care Provider, so they can't edit

            }
            mainViewholder = (RecordActivity.ViewHolder) convertView.getTag();
            notifyDataSetChanged();
            String[] parts = getItem(position).split("~");
            // deleteBtn deletes problem from all 3 lists
            mainViewholder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.notifyDataSetChanged();
                    //index = problemString.indexOf(getItem(position));
                    index = adapter.getPosition(getItem(position));

                    Comment comment = commentList.getComment(index);
                    commentList.removeComment(comment);

                    adapter.remove(adapter.getItem(position));
                    adapter.notifyDataSetChanged();

                    patient.getProblems().getProblem(problemIndex).getRecordList().updateRecord(recordIndex,record);
                    accountManager.patientUpdater(patient.getUserID(), patient);
                    if (getIntent().hasExtra("CareProvider")) {
                        accountManager.careProviderUpdater(careProvider.getUserID(), careProvider);
                    }
                }
            });

            // editBtn opens child activity with the chosen comment as extra
            mainViewholder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editIndex = adapter.getPosition(getItem(position));
                    Bundle args = new Bundle();
                    args.putString("comment", parts[1]);
                    EditCommentDialog dialog=new EditCommentDialog();
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager(),"dialog");
                    adapter.notifyDataSetChanged();

                    // index of edited problem is saved so we can delete it later

                }
            });

            if (getIntent().hasExtra("CareProvider")){
                if (accountManager.findPatient(parts[0]) != null){
                    mainViewholder.deleteBtn.setVisibility(View.INVISIBLE);
                    mainViewholder.deleteBtn.setClickable(false);
                    mainViewholder.editBtn.setVisibility(View.INVISIBLE);
                    mainViewholder.editBtn.setClickable(false);
                }
                else{
                    mainViewholder.deleteBtn.setVisibility(View.VISIBLE);
                    mainViewholder.deleteBtn.setClickable(true);
                    mainViewholder.editBtn.setVisibility(View.VISIBLE);
                    mainViewholder.editBtn.setClickable(true);
                }
            }
            else{
                if (accountManager.findPatient(parts[0]) == null) {
                    mainViewholder.deleteBtn.setVisibility(View.INVISIBLE);
                    mainViewholder.deleteBtn.setClickable(false);
                    mainViewholder.editBtn.setVisibility(View.INVISIBLE);
                    mainViewholder.editBtn.setClickable(false);
                }
                else{
                    mainViewholder.deleteBtn.setVisibility(View.VISIBLE);
                    mainViewholder.deleteBtn.setClickable(true);
                    mainViewholder.editBtn.setVisibility(View.VISIBLE);
                    mainViewholder.editBtn.setClickable(true);
                }
            }
            if (parts.length > 1) {
                mainViewholder.title.setText(parts[0]);
                mainViewholder.commentText.setText(parts[1]);
            }

            return convertView;

        }

    }

    public class ViewHolder {
        TextView title;
        Button deleteBtn;
        Button editBtn;
        TextView commentText;
    }

    @Override
    public void applyEditTexts(String commentEntered) {
        //record = patient.getProblems().getProblem(problemIndex).getRecordList().getRecord(recordIndex);
        //commentList = record.getComments();
        if (getIntent().hasExtra("CareProvider")) {
            commentList.getComment(editIndex).editText(commentEntered);
            adapter.remove(adapter.getItem(editIndex));
            adapter.insert(commentList.getComment(editIndex).toString(),editIndex);
            adapter.notifyDataSetChanged();
            accountManager.patientUpdater(patient.getUserID(), patient);
            accountManager.careProviderUpdater(careProvider.getUserID(), careProvider);
            Log.d("checkComment1",commentList.getList().toString());
        }
        else{
            commentList.getComment(editIndex).editText(commentEntered);
            adapter.remove(adapter.getItem(editIndex));
            adapter.insert(commentList.getComment(editIndex).toString(),editIndex);
            adapter.notifyDataSetChanged();
            accountManager.patientUpdater(patient.getUserID(), patient);

        }
        Toast.makeText(RecordActivity.this,
                "Comments edited successfully",
                Toast.LENGTH_SHORT).show();

    }
}
