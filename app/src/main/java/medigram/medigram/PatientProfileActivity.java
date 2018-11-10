package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PatientProfileActivity extends Activity {
    private Button editProfileButton;
    private Button viewProblemsButton;
    private TextView DisplayUserID;
    private TextView DisplayEmail;
    private TextView DisplayPhone;
    private String userID;
    private String email;
    private String phoneNumber;
    private Patient account;
    private Boolean accountDeleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        account = (Patient) getIntent().getSerializableExtra("Patient");
        userID = account.getUserID();
        email = account.getEmailAddress();
        phoneNumber = account.getPhoneNumber();

        DisplayUserID = findViewById(R.id.DisplayUserID);
        DisplayEmail = findViewById(R.id.DisplayEmail);
        DisplayPhone = findViewById(R.id.DisplayPhone);
        editProfileButton = findViewById(R.id.patientEditProfileButton);
        viewProblemsButton = findViewById(R.id.patientViewProblemButton);

        DisplayUserID.setText(userID);
        DisplayEmail.setText(email);
        DisplayPhone.setText(phoneNumber);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                intent.putExtra("Patient", account);
                startActivityForResult(intent, 1);
            }
        });

    }

    protected void updateProfile(Patient account){
        /* Refreshes the display info on Start */
        DisplayUserID.setText(account.getUserID());
        DisplayEmail.setText(account.getEmailAddress());
        DisplayPhone.setText(account.getPhoneNumber());
    }

    @Override
    protected void onActivityResult(int requestCode, int code, Intent intent){
        if (requestCode == 1 ){
            if (code == RESULT_OK){
                accountDeleted = intent.getBooleanExtra("deleted", false);
                if (accountDeleted){
                    finish();
                }else {
                    account = (Patient) intent.getSerializableExtra("updated");
                    updateProfile(account);
                }
            }
        }
    }
}
