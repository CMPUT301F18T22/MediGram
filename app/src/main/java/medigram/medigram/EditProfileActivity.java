package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileActivity extends Activity {
    private EditText updateUserID;
    private EditText updateEmail;
    private EditText updatePhone;
    private Button updateButton;
    private Button deleteButton;
    private String oldUserID;
    private String newUserID;
    private String newEmail;
    private String newPhone;
    private String jestIDtoDelete;
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        accountManager  = new AccountManager(getApplicationContext());

        updateUserID = findViewById(R.id.updateUserID);
        updateEmail = findViewById(R.id.updateEmail);
        updatePhone = findViewById(R.id.updatePhone);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        if (getIntent().hasExtra("Patient")){
            Patient account = (Patient) getIntent().getSerializableExtra("Patient");
            oldUserID = account.getUserID();
            updateUserID.setText(oldUserID);
            updateEmail.setText(account.getEmailAddress());
            updatePhone.setText(account.getPhoneNumber());
        }
        else if (getIntent().hasExtra("CareProvider")){
            CareProvider account = (CareProvider) getIntent().getSerializableExtra("CareProvider");
            oldUserID = account.getUserID();
            updateUserID.setText(oldUserID);
            updateEmail.setText(account.getEmailAddress());
            updatePhone.setText(account.getPhoneNumber());
        }


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUserID = updateUserID.getText().toString();
                newEmail = updateEmail.getText().toString();
                newPhone = updatePhone.getText().toString();
                Intent intent = new Intent();
                if (getIntent().hasExtra("Patient")){
                    Patient account = (Patient) getIntent().getSerializableExtra("Patient");

                    account.setUserID(newUserID);
                    account.setEmailAddress(newEmail);
                    account.setPhoneNumber(newPhone);
                    String response = accountManager.patientUpdater(oldUserID, account);

                    if (response == null){
                        Toast toast = Toast.makeText(EditProfileActivity.this, "Account Updated Successfully.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();

                        intent.putExtra("updated", account);
                        intent.putExtra("deleted", false);

                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        Toast toast = Toast.makeText(EditProfileActivity.this, response, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();
                    }
                }
                else if (getIntent().hasExtra("CareProvider")){
                    CareProvider account = (CareProvider) getIntent().getSerializableExtra("CareProvider");

                    account.setUserID(newUserID);
                    account.setEmailAddress(newEmail);
                    account.setPhoneNumber(newPhone);
                    String response = accountManager.careProviderUpdater(oldUserID, account);

                    if (response == null){
                        Toast toast = Toast.makeText(EditProfileActivity.this, "Account Updated Successfully.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();

                        intent.putExtra("updated", account);
                        intent.putExtra("deleted", false);

                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        Toast toast = Toast.makeText(EditProfileActivity.this, response, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 320);
                        toast.show();
                    }
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().hasExtra("Patient")){
                    Patient patient = (Patient) getIntent().getSerializableExtra("Patient");
                    jestIDtoDelete = patient.getUserID();
                }
                else if (getIntent().hasExtra("CareProvider")){
                    CareProvider careProvider = (CareProvider) getIntent().getSerializableExtra("CareProvider");
                    jestIDtoDelete = careProvider.getUserID();
                }
                accountManager.accountDeleter(jestIDtoDelete);

                Intent intent = new Intent();
                intent.putExtra("deleted", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
