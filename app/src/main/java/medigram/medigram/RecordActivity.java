package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This activity displays the comments between specific patient and patient's
 * assigned careProvider.  The comment list is retrieved from the User given
 * by the parent activity. Adding comment is done by a dialog box. After
 * adding is done, the User data is updated over the network if available.
 *
 * @author Jeremy Xie
 */
public class RecordActivity extends AppCompatActivity implements AddCommentDialog.AddCommentDialogListener {
    private Patient patient;
    private CareProvider careProvider;
    private Button addCommentBTN;
    private EditText editText;
    private TextView patientComment;
    private TextView careProviderComment;
    private Comment comment;

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
        patientComment = (TextView) findViewById(R.id.patientComment);
        careProviderComment = (TextView) findViewById(R.id.carproviderComment);
        careProvider = (CareProvider) getIntent().getSerializableExtra("CareProvider");
        patient = (Patient) getIntent().getSerializableExtra("Patient");
        addCommentBTN = (Button) findViewById(R.id.addComment);
        addCommentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }

        });
    }

    public void openDialog(){
        AddCommentDialog addCommentDialog = new AddCommentDialog();
        addCommentDialog.show(getSupportFragmentManager(),"addcomment dialog");
    }

    @Override
    public void applyTexts(String commentEntered) {
        if (getIntent().hasExtra("CareProvider")) {
            careProviderComment.setText(commentEntered);
            comment = new Comment(commentEntered,careProvider.getUserID());
        }
        else{
            patientComment.setText(commentEntered);
            comment = new Comment(commentEntered,patient.getUserID());
        }
        Toast.makeText(RecordActivity.this,
                "Comments added successfully",
                Toast.LENGTH_SHORT).show();
    }
}
