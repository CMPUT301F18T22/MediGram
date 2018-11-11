/**
 * This is the adapter enabling the patient list to be shown as a ListView
 * Sources:
 * Nguyen Duc Hoang, https://www.youtube.com/watch?v=Q_fDWhqKX7g
 */
package medigram.medigram;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;

public class PatientListActivity extends Activity {

    private Button addPatientBut;
    private CareProvider careProvider;
    private PatientList patients;
    private ListView listViewPatients;
    private PatientListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);


        // TODO get current logged in care provider's info from "putextra" in CareproviderProfileActivity
        careProvider = new CareProvider("careprovider2"
                                        ,"cp2@gmail.com"
                                        ,"7807166859");
        patients = careProvider.getAssignedPatients();
        listViewPatients = findViewById(R.id.patient_listview);
        addPatientBut = findViewById(R.id.add_patient);  // the add patient button

        populateListView();

        // go to add patient activity
        addPatientBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientListActivity.this, AddPatientActivity.class);
                intent.putExtra("CareProvider", careProvider);
                startActivity(intent);
            }
        });

    }

    public void populateListView() {
        listViewPatients = (ListView) findViewById(R.id.patient_listview);

        // TODO delete this part
        Patient test = new Patient("patientone", "p1@gmail.com", "7807166666");
        patients.addPatient(test);
        test = new Patient("patienttwo", "p2@gmail.com", "7807167777");
        patients.addPatient(test);


        adapter = new PatientListAdapter(this, patients);
        listViewPatients.setAdapter(adapter);

        // handles clicking on one of the patient in list view
        listViewPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PatientListActivity.this, "Click to patient: " + i
                                , Toast.LENGTH_SHORT).show();  // shows which patient is clicked
            }
        });
    }


}
