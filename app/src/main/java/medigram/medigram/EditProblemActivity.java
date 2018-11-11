package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditProblemActivity extends AppCompatActivity {
    public Problem chosenProblem;
    public Date parsedDate;
    public int REQUEST_CODE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);

        // Get problem list from the bundle from ProblemListActivity
        Bundle bundleObject = getIntent().getExtras();
        chosenProblem = (Problem) bundleObject.getSerializable("chosenProblem");

        Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        View.OnClickListener onClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText problemTitle = (EditText) findViewById(R.id.problemTitle);
                chosenProblem.setProblemTitle(problemTitle.getText().toString());

                EditText description = (EditText) findViewById(R.id.description);
                chosenProblem.setDescription(description.getText().toString());
                /*
                date = (EditText) findViewById(R.id.date);
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddThh:mmZ", Locale.US);
                    Date parsedDate = dateFormat.parse(date.getText().toString());
                }
                catch(ParseException pe ) {
                    // If not successful

                }
                */
                parsedDate = new Date();
                chosenProblem.setDateStarted(parsedDate);

                Intent openEditor = new Intent();

                Bundle problem_bundle = new Bundle();
                problem_bundle.putSerializable("editedProblem", chosenProblem);
                openEditor.putExtras(problem_bundle);

                setResult(Activity.RESULT_OK, openEditor);
                finish();
            }
        };
        confirmBtn.setOnClickListener(onClickListener);


    }

}
