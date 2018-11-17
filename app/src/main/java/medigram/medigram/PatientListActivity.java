package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
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
 * https://www.youtube.com/watch?v=NMTUsrBHCrA
 * https://stackoverflow.com/questions/12276138/android-listview-not-scrolling
 * @see CareProviderProfileActivity
 * @author Xiaohui Liu
 */
public class PatientListActivity extends Activity implements TextWatcher {

    private Button addPatientBut;
    private CareProvider careProvider;
    private PatientList patients;
    private ListView listViewPatients;
    private EditText search_patient;
    private SearchPatientAdapter searchAdapter;
    private PatientSearchInfo searchInfo;
    private ArrayList<PatientSearchInfo> searchInfos;
    private ArrayList<String> userIDs = new ArrayList<>();
    private int [] numOfProblemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);


        careProvider = (CareProvider) getIntent().getSerializableExtra("CareProvider");
        patients = careProvider.getAssignedPatients();
        listViewPatients = findViewById(R.id.patient_listview);
        addPatientBut = findViewById(R.id.add_patient);  // the add patient button

        // TODO this is for testing purpose only, must be deleted later (since adding patient not working yet)
        Patient patient1 = new Patient("patientone", "p1@gmail.com", "7701111111");
        patients.addPatient(patient1);
        Patient patient2 = new Patient("patienttwo", "p2@gmail.com", "7702222222");
        patients.addPatient(patient2);
        Patient patient3 = new Patient("3rdpatient", "p3@gmail.com", "7703333333");
        patients.addPatient(patient3);
        Patient patient4 = new Patient("patient4", "p4@gmail.com", "7704444444");
        patients.addPatient(patient4);
        Patient patient5 = new Patient("patient4", "p4@gmail.com", "7704444444");
        patients.addPatient(patient5);
        Patient patient6 = new Patient("patienttwo", "p2@gmail.com", "7702222222");
        patients.addPatient(patient6);
        Patient patient7 = new Patient("3rdpatient", "p3@gmail.com", "7703333333");
        patients.addPatient(patient7);
        Patient patient8 = new Patient("patient4", "p4@gmail.com", "7704444444");
        patients.addPatient(patient8);
        Patient patient9 = new Patient("patient4", "p4@gmail.com", "7704444444");
        patients.addPatient(patient9);
        Patient patient10 = new Patient("patient4", "p4@gmail.com", "7704444444");
        patients.addPatient(patient10);

        search_patient = findViewById(R.id.search_patient);
        search_patient.addTextChangedListener(this);  // handles searching patient
        searchInfos = new ArrayList<>();
        userIDs = patients.getUserIDs();
        numOfProblemList = patients.getAllNumsOfProblems();

        for (int i = 0; i < patients.getSize(); i++) {
            String userID = userIDs.get(i);
            int numOfProblems = numOfProblemList[i];
            searchInfo = new PatientSearchInfo(userID, numOfProblems);
            searchInfos.add(searchInfo);
        }

        searchAdapter = new SearchPatientAdapter(this, searchInfos);
        listViewPatients.setAdapter(searchAdapter);

        // handles clicking on one of the patient in list view
        listViewPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PatientListActivity.this, "Click to patient: " + i
                        , Toast.LENGTH_SHORT).show();  // shows which patient is clicked
            }
        });

        listViewPatients.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
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
     * Handles filtering user's searching input
     * @param charSequence
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        this.searchAdapter.getFilter().filter(charSequence);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
    // TODO may want to add a method that refresh the list every time user come back to this activity
}
