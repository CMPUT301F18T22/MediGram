package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends Activity {
    protected EditText inputUserID;
    protected Button signInButton;
    protected Button signUpButton;
    private String userID;
    private ArrayList<Patient> patientsResults;
    private ArrayList<CareProvider> careProvidersResults;
    private ElasticsearchAccountController.GetPatientAccount getPatientAccount = new ElasticsearchAccountController.GetPatientAccount();
    private ElasticsearchAccountController.GetCareProviderAccount getCareProviderAccount = new ElasticsearchAccountController.GetCareProviderAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUserID = findViewById(R.id.InputUserID);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //TODO handle user login including separation of user types
                userID = inputUserID.getText().toString();
                Patient patient = findPatient(userID);
                if (patient != null) {
                    Toast toast = Toast.makeText(LoginActivity.this, "Patient User Account Found.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 320);
                    toast.show();
                }
                else {
                    CareProvider careProvider = findCareProvider(userID);
                    if ( careProvider != null ) {
                        Toast toast = Toast.makeText(LoginActivity.this, "Care Provider Found.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();
                    }
                    else{
                        Toast toast = Toast.makeText ( LoginActivity.this, "No user account found.", Toast.LENGTH_LONG );
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();
                    }
                }

            }
        });


    }

    public Patient findPatient(String userID){
        try {
            getPatientAccount.execute(userID);
            patientsResults = getPatientAccount.get();
            if (patientsResults.size() != 0){
                return patientsResults.get(0);
            }
        }catch (Exception e){
            //TODO better error handling
            e.printStackTrace();
        }
        return null;
    }

    public CareProvider findCareProvider(String userID){
        try {
            getCareProviderAccount.execute(userID);
            careProvidersResults = getCareProviderAccount.get();
            if (careProvidersResults.size() != 0) {
                return careProvidersResults.get(0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
