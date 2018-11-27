package medigram.medigram;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecordActivity extends AppCompatActivity {
    private Patient patient;
    private CareProvider careProvider;
    private Button addCommentBTN;
    private EditText editText;
    private TextView patientComment;
    private TextView careProviderComment;
    private Comment comment;
    public ListView commentView;
    public ArrayList<String> commentString = new ArrayList<>();
    private Button addPicture;
    private Button addGeo;
    private Button viewPicture;
    private TextView recordTitle;
    private Record record;
    private CommentList commentList;
    private String[] commentsList= {"s"};
    private TextView commentText;

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
        // test
        comment = new Comment("sdsdf",patient.getUserID());
        commentList = record.getComments();
        commentList.addComment(comment);
        System.out.println(commentList.getList().get(0).getText());

        CommentListAdapter adapter = new CommentListAdapter();
        //commentString.add("sdfsss");
        //ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,commentString );

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


    private class CommentListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return commentList.getSize();
        }

        @Override
        public Object getItem(int i) {
            return commentList.getComment(i).toString();
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.comments_list_items,null);
            TextView title = (TextView) findViewById(R.id.titleText);
            commentText = (TextView) findViewById(R.id.list_item_text);
            Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
            Button editBtn = (Button) findViewById(R.id.editBtn);
            String[] parts = getItem(i).toString().split("~");
            Log.d("parts",parts.toString());
            //title.setText(commentList.getComment(i).getSender());
            commentText.setText(parts[1]);
            return view;

        }
    }

}
