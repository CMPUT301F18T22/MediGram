package medigram.medigram;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    private Patient patient;
    private CareProvider careProvider;
    private Button addCommentBTN;
    private EditText editText;
    private TextView patientComment;
    private TextView careProviderComment;
    private Comment comment;
    public ListView commentView;
    public ArrayAdapter<String> adapter;
    public ArrayList<String> commentString = new ArrayList<>();
    private Button addPicture;
    private Button addGeo;
    private Button viewPicture;
    private TextView recordTitle;
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        addPicture = (Button) findViewById(R.id.addPicture);
        addGeo = (Button) findViewById(R.id.addGeo);
        viewPicture = (Button) findViewById(R.id.viewPicture);
        recordTitle = (TextView) findViewById(R.id.Recordtitle);

        if (getIntent().hasExtra("CareProvider")) {
            addPicture.setVisibility(View.INVISIBLE);
            addPicture.setClickable(false);
            String naming = "VIEW GEOLOCATION";
            addGeo.setText(naming);
        }
        // patientComment = (TextView) findViewById(R.id.patientComment);
        //careProviderComment = (TextView) findViewById(R.id.carproviderComment);
        careProvider = (CareProvider) getIntent().getSerializableExtra("CareProvider");
        patient = (Patient) getIntent().getSerializableExtra("Patient");
        Integer recordIndex = getIntent().getIntExtra("RecordIndex", -1);
        Integer problemIndex = getIntent().getIntExtra("ProblemIndex", -1);
        record = patient.getProblems().getProblem(problemIndex).getRecordList().getRecord(recordIndex);
        commentView = (ListView) findViewById(R.id.commentListView);
        recordTitle.setText(record.getRecordTitle());
        commentView.setTextFilterEnabled(true);
        //adapter = new CommentListAdapter(this,R.layout.comments_list_items, commentString);
        commentString.add("sdfsss");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,commentString );

        commentView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //commentView.setClickable(true);
        addCommentBTN = (Button) findViewById(R.id.addComment);
/*
        addCommentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }

        });
        */

    }


    private class CommentListAdapter extends ArrayAdapter<String> {
        private int layout;

        private CommentListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.infoText = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.titleText = (TextView) convertView.findViewById(R.id.titleText);
                viewHolder.deleteBtn = (Button) convertView.findViewById(R.id.deleteBtn);
                viewHolder.editBtn = (Button) convertView.findViewById(R.id.editBtn);
                convertView.setTag(viewHolder);
                // Remove these buttons if user is a Care Provider, so they can't edit
                if (getIntent().hasExtra("CareProvider")) {
                    viewHolder.deleteBtn.setVisibility(View.GONE);
                    viewHolder.editBtn.setVisibility(View.GONE);
                }
            }else {
                mainViewholder = (ViewHolder) convertView.getTag();
                mainViewholder.titleText.setText(getItem(position));
            }

/*
            // deleteBtn deletes problem from all 3 lists
            mainViewholder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.notifyDataSetChanged();
                    //index = problemString.indexOf(getItem(position));
                    index = adapter.getPosition(getItem(position));


                    Problem test = filteredProblems.getProblem(index);
                    problemList.removeProblem(test);
                    filteredProblems.removeProblem(test);

                    adapter.remove(adapter.getItem(position));
                    adapter.notifyDataSetChanged();
                    adapter.getFilter().filter(null);


                    //Log.d("Problem", problemList.getList().toString());
                    notifyDataSetChanged();
                    accountManager.patientUpdater(patient.getUserID(), patient);


                }
            });


            // editBtn opens child activity with the chosen problems bundles as extra
            mainViewholder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // index of edited problem is saved so we can delete it later
                    index = adapter.getPosition(getItem(position));
                    lastPosition = problemString.indexOf(getItem(position));

                    Intent openEditor = new Intent(getApplicationContext(), EditProblemActivity.class);
                    // Pass list of emotion objects by using serializable
                    chosenProblem = filteredProblems.getProblem(index);

                    for (Problem p: filteredProblems.getList()){
                        if (p.toString() == adapter.getItem(position)){
                            chosenProblem = p;
                            break;
                        }
                    }

                    Bundle problem_bundle = new Bundle();
                    problem_bundle.putSerializable("chosenProblem", chosenProblem);
                    openEditor.putExtras(problem_bundle);
                    startActivityForResult(openEditor, 1);

                }
            });

            // display the problem title and info
            notifyDataSetChanged();
            String[] parts = getItem(position).split("~");
            mainViewholder.infoText.setText(parts[1]);
            mainViewholder.titleText.setText(parts[0]);

*/
            notifyDataSetChanged();
            mainViewholder.infoText.setText("sdfsd");
            mainViewholder.titleText.setText("sdf");

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
