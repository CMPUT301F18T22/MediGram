package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPatientActivity extends Activity {

    private Button confirmAddingBut;
    private EditText inputUserID;
    private String userID;
    private CareProvider careProvider = new CareProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        inputUserID = findViewById(R.id.input_userid);


        confirmAddingBut = findViewById(R.id.confirm_adding);
        // jump back to patient List activity
        confirmAddingBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get user's input
                inputUserID = findViewById(R.id.input_userid);
                // transform it to string for better readability
                userID = inputUserID.getText().toString();
                CareProvider.assign

                Intent intent = new Intent(AddPatientActivity.this, PatientListActivity.class);
                Toast toast = Toast.makeText(AddPatientActivity.this,
                                        "Patient Added Successfully!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 320);
                toast.show();
                finish();
            }
        });
    }



}
