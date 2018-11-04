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
    private ArrayList<Patient> patientsSearchResults;
    private ArrayList<CareProvider> careProvidersSearchResults;
    private ElasticsearchAccountController.GetPatientAccount patientsElasticSearcher = new ElasticsearchAccountController.GetPatientAccount();
    private ElasticsearchAccountController.GetCareProviderAccount careProvidersElasticSearcher = new ElasticsearchAccountController.GetCareProviderAccount();

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
                CareProvider careProvider = findUser(userID);
                if (careProvider != null) {
                    Toast toast = Toast.makeText(LoginActivity.this, "User Account of Type Care Provider Found.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 320);
                    toast.show();
                }
//                if (user instanceof Patient){
//                    Toast toast = Toast.makeText ( LoginActivity.this, "User Account of Type Patient Found.", Toast.LENGTH_LONG );
//                    toast.setGravity(Gravity.CENTER, 0, 320);
//                    toast.show();
//                }
//                else if (careProvider instanceof CareProvider){
//                    Toast toast = Toast.makeText ( LoginActivity.this, "User Account of Type Care Provider Found.", Toast.LENGTH_LONG );
//                    toast.setGravity(Gravity.CENTER, 0, 320);
//                    toast.show();
//                }
                else{
                    Toast toast = Toast.makeText ( LoginActivity.this, "No user account found.", Toast.LENGTH_LONG );
                    toast.setGravity(Gravity.CENTER, 0, 320);
                    toast.show();
                }
            }
        });


    }

    public CareProvider findUser(String userID){
        try {
//            patientsElasticSearcher.execute(userID);
//            patientsSearchResults = patientsElasticSearcher.get();
//            if (patientsSearchResults.size() != 0){
//                return patientsSearchResults.get(0);
//            }
//            else{
            careProvidersElasticSearcher.execute(userID);
            careProvidersSearchResults = careProvidersElasticSearcher.get();
            if (patientsSearchResults.size() != 0){
                return careProvidersSearchResults.get(0);
            }
//            }
        }catch (Exception e){
            //TODO better error handling
            e.printStackTrace();
        }
        return null;
    }
}
