package medigram.medigram;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends Activity {
    protected EditText inputUserID;
    protected EditText inputEmail;
    protected EditText inputPhoneNumber;
    protected Button signUpButton;
    protected CheckBox patientCheckBox;
    protected CheckBox careProviderCheckBox;
    private AccountManager accountManager = new AccountManager();
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

        patientCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                careProviderCheckBox.setChecked(false);
            }
        });

        careProviderCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientCheckBox.setChecked(false);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                newUserID = inputUserID.getText().toString();
                newUserEmail = inputEmail.getText().toString();
                newUserPhoneNumber = inputPhoneNumber.getText().toString();

                if (careProviderCheckBox.isChecked()){
                    CareProvider careProvider = new CareProvider(newUserID, newUserEmail, newUserPhoneNumber);
                    if (accountManager.addCareProvider(careProvider) == null){
                        Toast toast = Toast.makeText(CreateAccountActivity.this, "Created Account Successfully.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();
                        finish();
                    }else{
                        Toast toast = Toast.makeText(CreateAccountActivity.this, accountManager.addCareProvider(careProvider), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();
                    }
                }
                else if (patientCheckBox.isChecked()){
                    Patient patient = new Patient(newUserID, newUserEmail, newUserPhoneNumber);
                    if ( accountManager.addPatient(patient) == null){
                        Toast toast = Toast.makeText(CreateAccountActivity.this, "Created Account Successfully.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();
                        finish();
                    }else{
                        Toast toast = Toast.makeText(CreateAccountActivity.this, accountManager.addPatient(patient), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();
                    }
                }
            }
        });


    }
}
