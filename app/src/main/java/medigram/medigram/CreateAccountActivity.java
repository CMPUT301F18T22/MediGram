package medigram.medigram;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class CreateAccountActivity extends Activity {
    protected EditText inputUserID;
    protected EditText inputEmail;
    protected EditText inputPhoneNumber;
    protected Button signUpButton;
    protected CheckBox patientCheckBox;
    protected CheckBox careProviderCheckBox;
    private ElasticsearchAccountController.CreateCareProviderAccount addCareProvider = new ElasticsearchAccountController.CreateCareProviderAccount();
    private ElasticsearchAccountController.CreatePatientAccount addPatient = new ElasticsearchAccountController.CreatePatientAccount();
    private String newUserID;
    private String newUserEmail;
    private String newUserPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        inputUserID = findViewById(R.id.signUpUserID);
        inputEmail = findViewById(R.id.signUpEmail);
        inputPhoneNumber = findViewById(R.id.signUpPhoneNumber);
        signUpButton = findViewById(R.id.FinishSignUpButton);
        patientCheckBox = findViewById(R.id.PatientCheckBox);
        careProviderCheckBox = findViewById(R.id.CareProviderCheckBox);

        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //TODO handle account restrictions - min 8 chars for userID, email format, phone number length.
                newUserID = inputUserID.getText().toString();
                newUserEmail = inputEmail.getText().toString();
                newUserPhoneNumber = inputPhoneNumber.getText().toString();

                if (careProviderCheckBox.isChecked()){
                    CareProvider careProvider = new CareProvider(newUserID, newUserEmail, newUserPhoneNumber);
                    addCareProvider.execute(careProvider);
                }
                else if (patientCheckBox.isChecked()){
                    Patient patient = new Patient(newUserID, newUserEmail, newUserPhoneNumber);
                    addPatient.execute(patient);
                }
                finish();
            }
        });


    }
}
