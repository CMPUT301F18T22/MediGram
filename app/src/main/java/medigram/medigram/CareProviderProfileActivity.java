package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CareProviderProfileActivity extends Activity {
    private Button editProfileButton;
    private Button viewPatientsButton;
    private TextView DisplayUserID;
    private TextView DisplayEmail;
    private TextView DisplayPhone;
    private DrawerLayout drawerLayout;
    private TextView drawerHeaderUserID;
    private TextView drawerHeaderEmail;
    private EditText drawerDisplayCode;
    private Button drawerGenerateButton;
    private String userID;
    private String email;
    private String phoneNumber;
    private CareProvider account;
    private Boolean accountDeleted;
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careprovider_profile);

        accountManager = new AccountManager(getApplicationContext());

        account = (CareProvider) getIntent().getSerializableExtra("CareProvider");
        Log.d("CareProvider", account.getJestID());
        userID = account.getUserID();
        email = account.getEmailAddress();
        phoneNumber = account.getPhoneNumber();

        drawerLayout = findViewById(R.id.careProviderDrawer);

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

        viewPatientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatientListActivity.class);
                intent.putExtra("CareProvider", account);
                startActivity(intent);
            }
        });



        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                drawerHeaderUserID = findViewById(R.id.nav_header_userID);
                drawerHeaderEmail = findViewById(R.id.nav_header_email);
                drawerDisplayCode = findViewById(R.id.drawerCode);
                drawerGenerateButton = findViewById(R.id.generateCodeBtn);

                drawerHeaderUserID.setText(account.getUserID());
                drawerHeaderEmail.setText(account.getEmailAddress());
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

                drawerGenerateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String code = account.generateCode();
                        drawerDisplayCode.setText(code);
                    }
                });
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    protected void updateProfile(CareProvider account){
        /* Refreshes the display info on Start */
        accountManager.careProviderUpdater(account.getUserID(), account);

        DisplayUserID.setText(account.getUserID());
        DisplayEmail.setText(account.getEmailAddress());
        DisplayPhone.setText(account.getPhoneNumber());
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.account = accountManager.findCareProvider(account.getUserID());
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
