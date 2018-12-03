package medigram.medigram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class LoginActivity extends Activity {
    protected EditText inputCode;
    protected Button signInButton;
    protected Button signUpButton;
    protected ProgressBar progressBar;
    private String code;
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountManager = new AccountManager(getApplicationContext());

        inputCode = findViewById(R.id.InputCode);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

        String userID = accountManager.autoLoginCheck(); //GG9FCT
        // Checks if there is already a user account logged in on the device.
        if (userID != null) {
            ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Logging In...");
            progressDialog.show();

            // Adds a loading screen, by delaying login process by 100ms.
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            CareProvider careProvider = accountManager.findCareProvider(userID);
                            if (careProvider != null) {
                                Log.d("JestID", careProvider.getJestID());
                                Intent intent = new Intent(getApplicationContext(), CareProviderProfileActivity.class);
                                intent.putExtra("CareProvider", careProvider);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finishAfterTransition();
                                progressDialog.dismiss();
                            }
                            else {
                                Patient patient = accountManager.findPatient(userID);
                                if (patient != null) {
                                    Log.d("JestID", patient.getJestID());
                                    Intent intent = new Intent(getApplicationContext(), PatientProfileActivity.class);
                                    intent.putExtra("Patient", patient);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finishAfterTransition();
                                    progressDialog.dismiss();
                                }
                            }
                        }
                    }, 100);
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishAfterTransition();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                code = inputCode.getText().toString();
                Patient patient = accountManager.findPatientByCode(code);

                if (patient != null) {
                    Intent intent = new Intent(getApplicationContext(), PatientProfileActivity.class);
                    intent.putExtra("Patient", patient);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finishAfterTransition();
                } else {
                    CareProvider careProvider = accountManager.findCareProviderByCode(code);
                    if (careProvider != null) {
                        Intent intent = new Intent(getApplicationContext(), CareProviderProfileActivity.class);
                        intent.putExtra("CareProvider", careProvider);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finishAfterTransition();
                    } else {
                        Toast toast = Toast.makeText(LoginActivity.this, "No user account found. Please make sure you have Internet connection first.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();
                    }
                }

            }
        });

    }

}
