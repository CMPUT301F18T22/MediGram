package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PatientProfileActivity extends Activity {
    private Button editProfileButton, viewProblemsButton;
    private TextView DisplayUserID, DisplayPhone, DisplayEmail;
    private String userID, phoneNumber, email;
    private Patient account;
    private Boolean accountDeleted;
    private EditText searchBox;

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
        searchBox = findViewById(R.id.searchBox);

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

        viewProblemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProblemListActivity.class);
                intent.putExtra("User", account);
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
                    intent.putExtra("User", account);
                    intent.putExtra("body location", searchBox.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
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
