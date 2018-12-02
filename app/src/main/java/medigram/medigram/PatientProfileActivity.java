package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PatientProfileActivity extends Activity {
    private Button editProfileButton, viewProblemsButton;
    private TextView displayUserID, displayPhone, displayEmail;
    private String userID, phoneNumber, email;
    private Patient account;
    private Boolean accountDeleted;
    private AccountManager accountManager;
    private EditText searchBox;
    private CareProvider careProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_patient_profile);
        displayUserID = findViewById(R.id.DisplayUserID);
        displayEmail = findViewById(R.id.DisplayEmail);
        displayPhone = findViewById(R.id.DisplayPhone);
        editProfileButton = findViewById(R.id.patientEditProfileButton);
        viewProblemsButton = findViewById(R.id.patientViewProblemButton);
        searchBox = findViewById(R.id.searchBox);

        accountManager = new AccountManager(getApplicationContext());
        if (getIntent().hasExtra("Patient")) {
            account = (Patient) getIntent().getSerializableExtra("Patient");
        }
        if (getIntent().hasExtra("CareProvider")) {
            account = (Patient) getIntent().getSerializableExtra("Patient");
            careProvider = (CareProvider) getIntent().getSerializableExtra("CareProvider");
            editProfileButton.setVisibility(View.GONE);
        }
        userID = account.getUserID();
        email = account.getEmailAddress();
        phoneNumber = account.getPhoneNumber();

        displayUserID.setText(userID);
        displayEmail.setText(email);
        displayPhone.setText(phoneNumber);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                intent.putExtra("Patient", account);
                startActivityForResult(intent, 1);
            }
        });

        viewProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProblemListActivity.class);
                if (getIntent().hasExtra("Patient")) {
                    intent.putExtra("Patient", account);

                }
                if (getIntent().hasExtra("CareProvider")) {
                    intent.putExtra("CareProvider", careProvider);
                    intent.putExtra("Patient", account);

                }
                intent.putExtra("body location", "");
                startActivity(intent);
            }
        });

        searchBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Intent intent = new Intent(getApplicationContext(), ProblemListActivity.class);
                    intent.putExtra("Patient", account);
                    intent.putExtra("body location", searchBox.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        this.account = accountManager.findPatient(this.account.getUserID());
    }

    protected void updateProfile(){
        /* Refreshes the display info on Start */
        accountManager.patientUpdater(account.getUserID(), account);
        displayUserID.setText(account.getUserID());
        displayEmail.setText(account.getEmailAddress());
        displayPhone.setText(account.getPhoneNumber());
    }

    @Override
    protected void onActivityResult(int requestCode, int code, Intent intent){
        if (requestCode == 1 ){
            if (code == RESULT_OK){
                accountDeleted = intent.getBooleanExtra("deleted", false);
                if (accountDeleted){
                    finish();
                }else {
                    this.account = (Patient) intent.getSerializableExtra("updated");
                    updateProfile();
                }
            }
        }
    }
}
