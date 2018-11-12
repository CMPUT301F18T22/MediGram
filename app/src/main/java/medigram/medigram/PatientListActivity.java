package medigram.medigram;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This is the adapter enabling the patient list to be shown as a ListView
 * Sources:
 * Nguyen Duc Hoang, https://www.youtube.com/watch?v=Q_fDWhqKX7g
 * https://github.com/mitchtabian/ListViews
 * @author: Xiaohui Liu
 */
public class PatientListActivity extends Activity {

    private Button addPatientBut;
    private CareProvider careProvider;
    private PatientList patients;
    private ListView listViewPatients;
    private PatientListAdapter adapter;
    private EditText search_patient;
    private ArrayList<String> userIDs = new ArrayList<>();
    private ArrayAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);


        careProvider = (CareProvider) getIntent().getSerializableExtra("CareProvider");
        patients = careProvider.getAssignedPatients();
        listViewPatients = findViewById(R.id.patient_listview);
        addPatientBut = findViewById(R.id.add_patient);  // the add patient button

        populateListView(patients);

        // search patient
        search_patient = findViewById(R.id.search_patient);
        userIDs = patients.getUserIDs();
        searchAdapter = new ArrayAdapter(PatientListActivity.this
                                            , R.layout.search_patient_list_item
                                            , userIDs);
        listViewPatients.setAdapter(searchAdapter);
        search_patient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (PatientListActivity.this).searchAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

    /**
     * Handles showing the patients as a well-organized list view
     * @see: PatientList
     */

    public void populateListView(PatientList patients) {
        listViewPatients = (ListView) findViewById(R.id.patient_listview);

        // TODO this is for testing purpose only, must be deleted later
        Patient patient1 = new Patient("patientone", "p1@gmail.com", "7701111111");
        patients.addPatient(patient1);
        Patient patient2 = new Patient("patienttwo", "p2@gmail.com", "7702222222");
        patients.addPatient(patient2);
        Patient patient3 = new Patient("3rdpatient", "p3@gmail.com", "7703333333");
        patients.addPatient(patient3);
        Patient patient4 = new Patient("patient4", "p4@gmail.com", "7704444444");
        patients.addPatient(patient4);

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
