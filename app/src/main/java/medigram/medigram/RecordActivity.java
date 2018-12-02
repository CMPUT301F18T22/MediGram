package medigram.medigram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

public class RecordActivity extends AppCompatActivity implements AddCommentDialog.AddCommentDialogListener, EditCommentDialog.EditCommentDialogListener{
    private Patient patient;
    private CareProvider careProvider;
    private Button addCommentBTN;
    private EditText editText;
    private TextView patientComment;
    private TextView careProviderComment;
    private Comment comment;
    public ListView commentView;
    public List<String> commentListString;
    private Button addPicture;
    private Button addGeo;
    private Button viewPicture;
    private TextView recordTitle;
    private Record record;
    private CommentList commentList;
    private CommentListAdapter adapter;
    private Integer recordIndex;
    private Integer problemIndex;
    private AccountManager accountManager;
    private Integer index;
    private Integer editIndex;


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
        setContentView(R.layout.activity_view_record);
        accountManager = new AccountManager(getApplicationContext());

        addPicture = (Button) findViewById(R.id.addPicture);
        addGeo = (Button) findViewById(R.id.addGeo);
        viewPicture = (Button) findViewById(R.id.viewPicture);
        recordTitle = (TextView) findViewById(R.id.recordTitle);

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
        recordIndex = getIntent().getIntExtra("RecordIndex", -1);
        problemIndex = getIntent().getIntExtra("ProblemIndex", -1);
        record = patient.getProblems().getProblem(problemIndex).getRecordList().getRecord(recordIndex);
        commentList = record.getComments();
        commentView = (ListView) findViewById(R.id.commentListView);
        recordTitle.setText(record.getRecordTitle());
        commentView.setTextFilterEnabled(true);

        addCommentBTN = (Button) findViewById(R.id.addComment);
        addCommentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }

        });

        viewPicture = (Button) findViewById(R.id.viewPicture);
        viewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(getApplicationContext(), GalleryActivity.class);
                gallery.putExtra("patient", patient);
                gallery.putExtra("recordIndex", recordIndex);
                gallery.putExtra("problemIndex", problemIndex);
                startActivity(gallery);
            }
        });


        commentListString = commentList.getList().stream().map(Comment::toString).collect(Collectors.toList());
        adapter = new RecordActivity.CommentListAdapter(this, R.layout.comments_list_items, commentListString);
        //commentString.add("sdfsss");
        //ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,commentString );

        commentView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    public void openDialog(){
        AddCommentDialog addCommentDialog = new AddCommentDialog();
        addCommentDialog.show(getSupportFragmentManager(),"addComment dialog");
    }

    @Override
    public void applyTexts(String commentEntered) {
        //record = patient.getProblems().getProblem(problemIndex).getRecordList().getRecord(recordIndex);
        //commentList = record.getComments();
        if (getIntent().hasExtra("CareProvider")) {
            comment = new Comment(commentEntered,careProvider.getUserID());
            commentList.addComment(comment);
            adapter.add(comment.toString());
            adapter.notifyDataSetChanged();
            accountManager.careProviderUpdater(careProvider.getUserID(), careProvider);
            Log.d("checkComment1",commentList.getList().toString());
        }
        else{
            comment = new Comment(commentEntered,patient.getUserID());
            commentList.addComment(comment);
            adapter.add(comment.toString());
            adapter.notifyDataSetChanged();
            accountManager.patientUpdater(patient.getUserID(), patient);

        }
        Toast.makeText(RecordActivity.this,
                "Comments added successfully",
                Toast.LENGTH_SHORT).show();
    }

    private class CommentListAdapter extends ArrayAdapter<String> {
        private int layout;

        private CommentListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            RecordActivity.ViewHolder mainViewholder = null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);

                RecordActivity.ViewHolder viewHolder = new RecordActivity.ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.titleRecord);
                viewHolder.commentText = (TextView) convertView.findViewById(R.id.list_item_comment);
                viewHolder.deleteBtn = (Button) convertView.findViewById(R.id.deleteButton);
                viewHolder.editBtn = (Button) convertView.findViewById(R.id.editButton);
                convertView.setTag(viewHolder);
                // Remove these buttons if user is a Care Provider, so they can't edit

            }
            mainViewholder = (RecordActivity.ViewHolder) convertView.getTag();
            notifyDataSetChanged();
            String[] parts = getItem(position).split("~");
            // deleteBtn deletes problem from all 3 lists
            mainViewholder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.notifyDataSetChanged();
                    //index = problemString.indexOf(getItem(position));
                    index = adapter.getPosition(getItem(position));

                    Comment comment = commentList.getComment(index);
                    commentList.removeComment(comment);

                    adapter.remove(adapter.getItem(position));
                    adapter.notifyDataSetChanged();

                    patient.getProblems().getProblem(problemIndex).getRecordList().updateRecord(recordIndex,record);
                    accountManager.patientUpdater(patient.getUserID(), patient);
                    if (getIntent().hasExtra("CareProvider")) {
                        accountManager.careProviderUpdater(careProvider.getUserID(), careProvider);
                    }
                }
            });

            // editBtn opens child activity with the chosen comment as extra
            mainViewholder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editIndex = adapter.getPosition(getItem(position));
                    Bundle args = new Bundle();
                    args.putString("comment", parts[1]);
                    EditCommentDialog dialog=new EditCommentDialog();
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager(),"dialog");
                    adapter.notifyDataSetChanged();

                    // index of edited problem is saved so we can delete it later

                }
            });

            if (getIntent().hasExtra("CareProvider")){
                if (accountManager.findPatient(parts[0]) != null){
                    mainViewholder.deleteBtn.setVisibility(View.INVISIBLE);
                    mainViewholder.deleteBtn.setClickable(false);
                    mainViewholder.editBtn.setVisibility(View.INVISIBLE);
                    mainViewholder.editBtn.setClickable(false);
                }
                else{
                    mainViewholder.deleteBtn.setVisibility(View.VISIBLE);
                    mainViewholder.deleteBtn.setClickable(true);
                    mainViewholder.editBtn.setVisibility(View.VISIBLE);
                    mainViewholder.editBtn.setClickable(true);
                }
            }
            else{
                if (accountManager.findPatient(parts[0]) == null) {
                    mainViewholder.deleteBtn.setVisibility(View.INVISIBLE);
                    mainViewholder.deleteBtn.setClickable(false);
                    mainViewholder.editBtn.setVisibility(View.INVISIBLE);
                    mainViewholder.editBtn.setClickable(false);
                }
                else{
                    mainViewholder.deleteBtn.setVisibility(View.VISIBLE);
                    mainViewholder.deleteBtn.setClickable(true);
                    mainViewholder.editBtn.setVisibility(View.VISIBLE);
                    mainViewholder.editBtn.setClickable(true);
                }
            }
            mainViewholder.title.setText(parts[0]);
            mainViewholder.commentText.setText(parts[1]);

            return convertView;

        }

    }

    public class ViewHolder {
        TextView title;
        Button deleteBtn;
        Button editBtn;
        TextView commentText;
    }

    @Override
    public void applyEditTexts(String commentEntered) {
        //record = patient.getProblems().getProblem(problemIndex).getRecordList().getRecord(recordIndex);
        //commentList = record.getComments();
        if (getIntent().hasExtra("CareProvider")) {
            commentList.getComment(editIndex).editText(commentEntered);
            adapter.remove(adapter.getItem(editIndex));
            adapter.insert(commentList.getComment(editIndex).toString(),editIndex);
            adapter.notifyDataSetChanged();
            accountManager.careProviderUpdater(careProvider.getUserID(), careProvider);
            Log.d("checkComment1",commentList.getList().toString());
        }
        else{
            commentList.getComment(editIndex).editText(commentEntered);
            adapter.remove(adapter.getItem(editIndex));
            adapter.insert(commentList.getComment(editIndex).toString(),editIndex);
            adapter.notifyDataSetChanged();
            accountManager.patientUpdater(patient.getUserID(), patient);

        }
        Toast.makeText(RecordActivity.this,
                "Comments edited successfully",
                Toast.LENGTH_SHORT).show();

    }
}
