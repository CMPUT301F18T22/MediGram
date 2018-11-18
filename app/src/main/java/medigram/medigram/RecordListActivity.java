package medigram.medigram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



public class RecordListActivity  extends AppCompatActivity {     private ListView recordsView;
    private ArrayAdapter<Record> adapter;
    private ArrayList<Record> recordList;
    private AccountManager accountManager;
    private Patient patient;
    private Problem problem;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        accountManager = new AccountManager(getApplicationContext());


        patient = (Patient) getIntent().getSerializableExtra("Patient");
        problem = (Problem) getIntent().getSerializableExtra("Problem");

        String problem_tile = problem.getProblemTitle();
        String date = problem.getDateString();
        String description = problem.getDescription();
        TextView Displayproblemtile = findViewById(R.id.problemtitle);
        TextView Displaydate = findViewById(R.id.timestamp);
        TextView Displaydes = findViewById(R.id.Description);


        Displayproblemtile.setText(problem_tile);
        Displaydate.setText(date);
        Displaydes.setText(description);
        recordsView = (ListView) findViewById(R.id.RecordListView);
        recordsView.setTextFilterEnabled(true);

        problemsView.setClickable(true);
        problemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
}


