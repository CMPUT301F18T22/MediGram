package medigram.medigram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.stream.Collectors;

public class RecordListActivity  extends AppCompatActivity {
    private ListView recordsView;
    private CareProvider careProvider;
    private RecordListAdapter adapter;
    private RecordList recordList;
    private List<String> recordListString;
    private Record chosenRecord;
    private AccountManager accountManager;
    private Patient patient;
    private Problem problem;
    private String date, description, problem_tile;
    private TextView Displayproblemtile, Displaydate, Displaydes;
    private Button addRecordButton;
    private int index;
    private int problemIndex;
    private ImageButton imageButton1, imageButton2;

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent openEditor = new Intent();
        setResult(Activity.RESULT_OK, openEditor);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        accountManager = new AccountManager(getApplicationContext());
        addRecordButton = findViewById(R.id.addrecordbtn);
        if (getIntent().hasExtra("CareProvider")){
            careProvider = (CareProvider) getIntent().getSerializableExtra("CareProvider");
            addRecordButton.setVisibility(View.INVISIBLE);
            addRecordButton.setClickable(false);
        }
        patient = (Patient) getIntent().getSerializableExtra("Patient");
        problem = (Problem) getIntent().getSerializableExtra("Problem");
        problemIndex = getIntent().getIntExtra("problemIndex", -1);
        problem = patient.getProblems().getProblem(problemIndex);
        Displayproblemtile = findViewById(R.id.problemtitle);
        Displaydate = findViewById(R.id.timestamp);
        Displaydes = findViewById(R.id.Description);
        recordsView = findViewById(R.id.recordListView);


        problem_tile = problem.getProblemTitle();
        date = problem.getDateString();
        description = problem.getDescription();

        Displayproblemtile.setText(problem_tile);
        Displaydate.setText(date);
        Displaydes.setText(description);

        recordList = problem.getRecordList();

        recordListString = recordList.getRecordList().stream().map(Record::toString).collect(Collectors.toList());
        adapter = new RecordListAdapter(this, R.layout.record_list_item, recordListString);

        recordsView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recordsView.setTextFilterEnabled(true);
        recordsView.setClickable(true);

        // Display problem images in the imageButtons
        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        if (problem.getBodyLocationPhoto(0) != null){
            Bitmap photo = problem.getBodyLocationPhoto(0).getBitmap();
            imageButton1.setImageBitmap(photo);
        }

        if (problem.getBodyLocationPhoto(1) != null){
            Bitmap photo = problem.getBodyLocationPhoto(1).getBitmap();
            imageButton2.setImageBitmap(photo);
        }


        recordsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = recordListString.indexOf(adapter.getItem(i));
                chosenRecord = recordList.getRecord(index);
                String recordTitle = chosenRecord.getRecordTitle();
                if (getIntent().hasExtra("CareProvider")) {
                    Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
                    intent.putExtra("CareProvider", careProvider);
                    intent.putExtra("Patient", patient);
                    intent.putExtra("Record", chosenRecord);
                    intent.putExtra("RecordIndex", index);
                    intent.putExtra("ProblemIndex", problemIndex);
                    startActivityForResult(intent,2);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
                    intent.putExtra("Patient", patient);
                    intent.putExtra("Record", chosenRecord);
                    intent.putExtra("RecordIndex", index);
                    intent.putExtra("ProblemIndex", problemIndex);
                    startActivityForResult(intent,2);
                }

            }
        });

        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddRecordActivity.class);
                intent.putExtra("Patient", patient);
                intent.putExtra("Problem", problem);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        patient = accountManager.findPatient(patient.getUserID());
        problem = patient.getProblems().getProblem(problemIndex);
        recordList = problem.getRecordList();

        recordListString = recordList.getRecordList().stream().map(Record::toString).collect(Collectors.toList());
        adapter.clear();
        adapter.addAll(recordListString);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 1) {
                Record newRecord = (Record) data.getSerializableExtra("newRecord");
                recordList.addRecord(newRecord);
                patient.getProblems().updateProblem(problemIndex, problem);
                Log.d("Record List", problem.getRecordList().toString());
                accountManager.patientUpdater(patient.getUserID(), patient);
                Log.d("Updated Patient", patient.getJestID());
                Log.d("New Record", newRecord.getRecordTitle());
                adapter.add(newRecord.toString());
                adapter.notifyDataSetChanged();
            }
        }
    }

    private class RecordListAdapter extends ArrayAdapter<String> implements Filterable {
        private int layout;

        private RecordListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            RecordListActivity.ViewHolder mainViewholder = null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);

                RecordListActivity.ViewHolder viewHolder = new RecordListActivity.ViewHolder();
                viewHolder.infoText = (TextView) convertView.findViewById(R.id.recordDescription);
                viewHolder.titleText = (TextView) convertView.findViewById(R.id.recordTitleText);
                viewHolder.deleteBtn = (Button) convertView.findViewById(R.id.recordDeleteBtn);
                viewHolder.editBtn = (Button) convertView.findViewById(R.id.recordEditBtn);
                convertView.setTag(viewHolder);
                // Remove these buttons if user is a Care Provider, so they can't edit
                if (getIntent().hasExtra("CareProvider")){
                    viewHolder.deleteBtn.setVisibility(View.GONE);
                    viewHolder.editBtn.setVisibility(View.GONE);
                }
            }
            mainViewholder = (RecordListActivity.ViewHolder) convertView.getTag();





            // deleteBtn deletes problem from all 3 lists
            mainViewholder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.notifyDataSetChanged();
                    //index = problemString.indexOf(getItem(position));
                    index = adapter.getPosition(getItem(position));


                    Record record = recordList.getRecord(index);
                    recordList.removeRecord(record);

                    adapter.remove(adapter.getItem(position));
                    adapter.notifyDataSetChanged();
                    adapter.getFilter().filter(null);


                    //Log.d("Problem", problemList.getList().toString());
                    notifyDataSetChanged();

                    patient.getProblems().updateProblem(problemIndex, problem);
                    accountManager.patientUpdater(patient.getUserID(), patient);
                }
            });


            // editBtn opens child activity with the chosen record as extra

            // display the problem title and info
            notifyDataSetChanged();
            String[] parts = getItem(position).split("~");
            mainViewholder.infoText.setText(parts[1]);
            mainViewholder.titleText.setText(parts[0]);

            return convertView;

        }

    }

    public class ViewHolder {
        TextView infoText;
        Button deleteBtn;
        Button editBtn;
        TextView titleText;
    }

}


