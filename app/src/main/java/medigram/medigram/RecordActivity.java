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
        setContentView(R.layout.activity_view_record);
    }
}
