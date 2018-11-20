package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

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
    private AccountManager accountManager;
    private Patient patient;
    private String id;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 1) {
                patient = (Patient) data.getSerializableExtra("newPatient");
                patients.addPatient(patient); // add the patient
                accountManager.careProviderUpdater(careProvider.getUserID(), careProvider);

                String userID = patient.getUserID();
                int numOfProblems = patient.getNumOfProblems();
                searchInfo = new PatientSearchInfo(userID, numOfProblems);
                searchInfos.add(searchInfo);

                searchAdapter = new SearchPatientAdapter(this, searchInfos);
                listViewPatients.setAdapter(searchAdapter);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        accountManager = new AccountManager(getApplicationContext());
        careProvider = (CareProvider) getIntent().getSerializableExtra("CareProvider");
        patients = careProvider.getAssignedPatients();
        listViewPatients = findViewById(R.id.patient_listview);
        addPatientBut = findViewById(R.id.add_patient);  // the add patient button

        search_patient = findViewById(R.id.search_patient);
        search_patient.addTextChangedListener(this);  // handles searching patient

        searchInfos = new ArrayList<>();
        userIDs = patients.getUserIDs();
        numOfProblemList = patients.getAllNumsOfProblems();

        Log.d("Patentss", patients.getAllNumsOfProblems().toString());

  
        for (int i = 0; i < patients.getSize(); i++) {
            patient = accountManager.findPatient(userIDs.get(i));
            int numOfProblems = patient.getNumOfProblems();
            searchInfo = new PatientSearchInfo(userIDs.get(i), numOfProblems);
            searchInfos.add(searchInfo);
        }

        searchAdapter = new SearchPatientAdapter(this, searchInfos);
        listViewPatients.setAdapter(searchAdapter);

        // handles clicking on one of the patient in list view
        listViewPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                id = userIDs.get(i);
                patient = accountManager.findPatient(id);
                Intent intent = new Intent(getApplicationContext(), PatientProfileActivity.class);
                intent.putExtra("CareProvider",patient);
                startActivity(intent);
//                Toast.makeText(PatientListActivity.this, "Click to patient: " + i
//                        , Toast.LENGTH_SHORT).show();  // shows which patient is clicked
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
                startActivityForResult(intent,1);
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

}
