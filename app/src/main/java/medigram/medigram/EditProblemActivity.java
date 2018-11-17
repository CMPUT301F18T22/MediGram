package medigram.medigram;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;

/**
 * This activity receives a problem from ProblemListActivity, and edits its contents.
 *  * When it is finished, the problem is bundled back to its Parent activity.
 *
 * @author Jarred
 */
public class EditProblemActivity extends AppCompatActivity {
    private Problem chosenProblem;
    private TextView dateTextView;
    private DatePickerDialog.OnDateSetListener dateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem);

        // Get problem list from the bundle from ProblemListActivity
        Bundle bundleObject = getIntent().getExtras();
        chosenProblem = (Problem) bundleObject.getSerializable("chosenProblem");

        // if problem contents are blank, then it is a new problem and we text box hints
        // if it is not blank, we display its contents in the text boxes
        if (chosenProblem.getProblemTitle() != "") {
            ((TextView) findViewById(R.id.problemTitle)).setText(chosenProblem.getProblemTitle());
            ((TextView) findViewById(R.id.problemDescription)).setText(chosenProblem.getDescription());
            ((TextView) findViewById(R.id.problemBodyLocation)).setText(chosenProblem.getBodyLocation());
        }
        ((TextView) findViewById(R.id.problemDate)).setText(chosenProblem.getDateString());

        // confirmBtn saves all text to the Problem and returns to parent activity
        Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText problemTitle = (EditText) findViewById(R.id.problemTitle);
                chosenProblem.setProblemTitle(problemTitle.getText().toString());

                EditText description = (EditText) findViewById(R.id.problemDescription);
                chosenProblem.setDescription(description.getText().toString());

                EditText bodyLocation = (EditText) findViewById(R.id.problemBodyLocation);
                chosenProblem.setBodyLocation(bodyLocation.getText().toString());

                chosenProblem.setDateStarted(dateTextView.getText().toString());

                Intent openEditor = new Intent();
                Bundle problem_bundle = new Bundle();
                problem_bundle.putSerializable("editedProblem", chosenProblem);
                openEditor.putExtras(problem_bundle);

                setResult(Activity.RESULT_OK, openEditor);
                finish();
            }
        });
        // display a date dialog when entering the date, so that entered date is always
        // in the correct format
        dateTextView = (TextView) findViewById(R.id.problemDate);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditProblemActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListener,
                        year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();


            }
        });
        // when user is done choosing date, dateListener enters the date into the text box
        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                dateTextView.setText(day+"/"+month+"/"+year);
            }
        };


    }

}
