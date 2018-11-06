package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    private ElasticSearchController.GetPatient getPatient = new ElasticSearchController.GetPatient();
    private ElasticSearchController.GetCareProvider getCareProvider = new ElasticSearchController.GetCareProvider();

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
                userID = inputUserID.getText().toString();
                Patient patient = findPatient(userID);
                if (patient != null) {
                    Intent intent = new Intent(getApplicationContext(), PatientProfileActivity.class);
                    intent.putExtra("Patient", patient);
                    startActivity(intent);
                } else {
                    CareProvider careProvider = findCareProvider(userID);
                    if (careProvider != null) {
                        Intent intent = new Intent(getApplicationContext(), CareProviderProfileActivity.class);
                        intent.putExtra("CareProvider", careProvider);
                        startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(LoginActivity.this, "No user account found.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();
                    }
                }

            }
        });


    }

    public Patient findPatient(String userID){
        try {
            getPatient.execute(userID);
            patientsResults = getPatient.get();
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
            getCareProvider.execute(userID);
            careProvidersResults = getCareProvider.get();
            if (careProvidersResults.size() != 0) {
                return careProvidersResults.get(0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
