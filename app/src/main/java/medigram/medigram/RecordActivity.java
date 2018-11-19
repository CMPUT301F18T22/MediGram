package medigram.medigram;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecordActivity extends AppCompatActivity {
    private Patient patient;
    private Button addCommentBTN;
    private EditText editText;
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 1) {
                patient = (Patient) data.getSerializableExtra("newPatient");
                patients.addPatient(patient); // add the patient
                accountManager.careProviderUpdater(careProvider.getUserID(), careProvider);

                String userID = patient.getUserID();
                int numOfProblems = patient.getNumOfProblems();
                searchInfo = new PatientSearchInfo(userID, numOfProblems);
                searchInfos.add(searchInfo);

                searchAdapter = new SearchPatientAdapter(this, searchInfos);
                listViewPatients.setAdapter(searchAdapter);
            }
        }
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        patient = (Patient) getIntent().getSerializableExtra("Patient");


        addCommentBTN = findViewById(R.id.addComment);
        addCommentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_add_comment, null);
                editText = (EditText) view.findViewById(R.id.enterComment);
                Button confirmbtn = (Button) view.findViewById(R.id.confirm);
                confirmbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!editText.getText().toString().isEmpty()){
                            Toast.makeText(RecordActivity.this,
                                    R.string.success_addComment_msg,
                                    Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(RecordActivity.this,
                                    R.string.no_comment_msg,
                                    Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(RecordActivity.this, RecordActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //Intent intent = new Intent(RecordActivity.this, RecordActivity.class);
                //intent.putExtra("Patient", patient);
                //startActivityForResult(intent,1);
            }
        });



    }
}
