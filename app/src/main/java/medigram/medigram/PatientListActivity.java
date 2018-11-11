package medigram.medigram;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

public class PatientListActivity extends Activity {

    private Button addPatientBut;
    // TODO get current logged in care provider's info from "putextra" in CareproviderProfileActivity
    public CareProvider careProvider = new CareProvider("careprovider2",
                                            "cp2@gmail.com", "7807166859");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        addPatientBut = findViewById(R.id.add_patient); // the add patient button
        // jump to add patient activity
        addPatientBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientListActivity.this, AddPatientActivity.class);
                intent.putExtra("CareProvider", (Parcelable) careProvider);
                startActivity(intent);
            }
        });

    }

}
