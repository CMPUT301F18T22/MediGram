package medigram.medigram;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CareProviderProfileActivity extends Activity {
    private TextView DisplayUserID;
    private TextView DisplayEmail;
    private TextView DisplayPhone;
    private String userID;
    private String email;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careprovider_profile);

        DisplayUserID = findViewById(R.id.DisplayUserID);
        DisplayEmail = findViewById(R.id.DisplayEmail);
        DisplayPhone = findViewById(R.id.DisplayPhone);

        CareProvider account = (CareProvider) getIntent().getSerializableExtra("CareProvider");
        userID = account.getUserID();
        email = account.getEmailAddress();
        phoneNumber = account.getPhoneNumber();

        DisplayUserID.setText(userID);
        DisplayEmail.setText(email);
        DisplayPhone.setText(phoneNumber);

        //TODO add buttons for Lists, handle account Editing, and add the Body map for Patients

    }


}
