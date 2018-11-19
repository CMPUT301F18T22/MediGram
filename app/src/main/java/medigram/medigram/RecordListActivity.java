package medigram.medigram;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RecordListActivity  extends AppCompatActivity {
    //private ListView recordsView;
    private ArrayAdapter<String> adapter;
    private ArrayList<Record> recordList;
    private AccountManager accountManager;
    private Patient patient;
    private Problem problem;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String items[] = new String[] {"apple", "pen"};
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

        Button button = findViewById(R.id.addrecordbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordListActivity.this, RecordActivity.class);
                intent.putExtra("Patient", patient);
                startActivity(intent);
            }
        });
        // for testing only, need to be deleted
        //ListView listView = (ListView) findViewById(R.id.RecordListView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_record,items);
        //listView.setAdapter(adapter);

    }
}


