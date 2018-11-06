package medigram.medigram;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PatientProfileActivity extends Activity {

    private TextView DisplayUserID;
    private TextView DisplayEmail;
    private TextView DisplayPhone;
    private String userID;
    private String email;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        DisplayUserID = findViewById(R.id.DisplayUserID);
        DisplayEmail = findViewById(R.id.DisplayEmail);
        DisplayPhone = findViewById(R.id.DisplayPhone);

        Patient account = (Patient) getIntent().getSerializableExtra("Patient");
        userID = account.getUserID();
        email = account.getEmailAddress();
        phoneNumber = account.getPhoneNumber();

        DisplayUserID.setText(userID);
        DisplayEmail.setText(email);
        DisplayPhone.setText(phoneNumber);
    }
}
