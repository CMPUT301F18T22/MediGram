package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CareProviderProfileActivity extends Activity {
    private AccountManager accountManager;
    private Button editProfileButton;
    private Button viewPatientsButton;
    private TextView DisplayUserID;
    private TextView DisplayEmail;
    private TextView DisplayPhone;
    private String userID;
    private String email;
    private String phoneNumber;
    private CareProvider account;
    private Boolean accountDeleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careprovider_profile);

        accountManager  = new AccountManager(getApplicationContext());

        account = (CareProvider) getIntent().getSerializableExtra("CareProvider");
        userID = account.getUserID();
        email = account.getEmailAddress();
        phoneNumber = account.getPhoneNumber();

        DisplayUserID = findViewById(R.id.DisplayUserID);
        DisplayEmail = findViewById(R.id.DisplayEmail);
        DisplayPhone = findViewById(R.id.DisplayPhone);
        editProfileButton = findViewById(R.id.providerEditProfileButton);
        viewPatientsButton = findViewById(R.id.patientListButton);

        DisplayUserID.setText(userID);
        DisplayEmail.setText(email);
        DisplayPhone.setText(phoneNumber);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                intent.putExtra("CareProvider", account);
                startActivityForResult(intent, 1);
            }
        });

    }

    protected void updateProfile(CareProvider account){
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
                }else{
                    account = (CareProvider) intent.getSerializableExtra("updated");
                    updateProfile(account);
                }
            }
        }
    }

}
