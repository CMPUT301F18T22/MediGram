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
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountManager  = new AccountManager(getApplicationContext());

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
                Patient patient = accountManager.findPatient(userID);


                if (patient != null) {
                    Intent intent = new Intent(getApplicationContext(), PatientProfileActivity.class);
                    intent.putExtra("Patient", patient);
                    startActivity(intent);
                } else {
                    CareProvider careProvider = accountManager.findCareProvider(userID);
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

}
