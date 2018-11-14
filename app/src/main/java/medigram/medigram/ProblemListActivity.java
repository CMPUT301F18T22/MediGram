package medigram.medigram;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This activity displays the problems specified by the user as a list. The list of problems is
 * retrieved from the User given by the parent activity, and then filtered by keywords. Adding or
 * editing a problem is done by a child activity.
 * After adding or editing is done, the User data is updated over the network if available.
 *
 * @author Jarred Mahinay
 */
public class ProblemListActivity extends AppCompatActivity {
    final Context context = this;
    public ProblemList problemList, filteredProblems;
    public List<String> problemString;
    public ListView problemsView;
    public Problem chosenProblem;
    public ArrayAdapter<String> adapter;
    public String bodyLocation, keyword;
    public int lastPosition, index;


    /**
     * After EditProblemActivity is finished, this method handles data received when returning
     * back to this activity. The edited or newly created problem by the child activity is added to
     * the problem list.
     * @param requestCode The number key that the child activity was opened with.
     *                    1 = Edit problem button, 2 = Add problem button
     * @param resultCode The result code given by the child activity
     *                   RESULT_OK = activity was successful
     * @param data The intent data sent by the child activity. This holds the problem that
     *             was edited.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            // if EditProblemActivity was opened with edit button
            if (requestCode == 1) {
                problemList.getList().remove(filteredProblems.getProblem(lastPosition));
                filteredProblems.removeIndex(lastPosition);
                problemString.remove(lastPosition);
            }

            // get the new edited problem from child activity
            Bundle bundleObject = data.getExtras();
            chosenProblem = (Problem) bundleObject.getSerializable("editedProblem");


            // Add the edited problem to the correct index, depending on its date
            for (Problem p: filteredProblems.getList()){
                if (chosenProblem.getDate().before(p.getDate())){
                    index = filteredProblems.getIndex(p);
                    filteredProblems.getList().add(index, chosenProblem);
                    problemString.add(index, chosenProblem.toString());
                    break;
                }
            }
            // if problem hasn't yet been added, then add it to the very end
            if (!filteredProblems.getList().contains(chosenProblem)){
                filteredProblems.getList().add(chosenProblem);
                problemString.add(chosenProblem.toString());
            }

            // add the new problem to Patient's list
            problemList.addProblem(chosenProblem);
            adapter.notifyDataSetChanged();


        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            // some stuff that will happen if there's no result
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* test: replace this with patient from Anas' activities*/
        Patient patient = new Patient("111111","test@gmail.com", "911");
        problemList = patient.getProblems();
        bodyLocation = "left arm";
        keyword = bodyLocation;
        Date date = new Date();

        Problem testproblem = new Problem("TestTitle1",
                "TestDescription1", date, "left arm");
        Problem testproblem2 = new Problem("TestTitle2",
                "TestDescription2", date, "left arm");
        Problem testproblem3 = new Problem("TestTitle3",
                "TestDescription3", date, "right arm");
        Problem testproblem4 = new Problem("TestTitle4",
                "TestDescription4", date, "right arm");

        problemList.addProblem(testproblem);
        problemList.addProblem(testproblem2);
        problemList.addProblem(testproblem3);
        problemList.addProblem(testproblem4);
         /*end test*/



        setContentView(R.layout.activity_problem_list);
        problemsView = (ListView) findViewById(R.id.ProblemListView);

        // filter the problems based off the user specified body part
        // new list, filteredProblems, is used to display list
        filteredProblems = new ProblemList();
        for (Problem p: problemList.getList()){
            if (p.getBodyLocation().equals(keyword)) {
                filteredProblems.addProblem(p);
            }
        }

        // create a list of strings from Problem.getString, and uses it on the list adapter
        problemString = filteredProblems.getList().stream().map(Problem::toString).collect(Collectors.toList());
        adapter = new ProblemListAdapter(this,
                R.layout.problem_list_item, problemString);
        problemsView.setAdapter(adapter);

        // add problem button Initializes a new problem and opens EditProblemActivity with it
        Button addProblemBtn = (Button) findViewById(R.id.addProblemBtn);
        View.OnClickListener addProblemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openEditor = new Intent(getApplicationContext(), EditProblemActivity.class);
                Bundle problem_bundle = new Bundle();

                Date date = new Date(); // creates problem with today's date
                Problem newProblem = new Problem("", "", date, bodyLocation);

                problem_bundle.putSerializable("chosenProblem", newProblem);
                openEditor.putExtras(problem_bundle);
                startActivityForResult(openEditor, 2);
            }
        };
        addProblemBtn.setOnClickListener(addProblemListener);
    }

    // ListView adapter is from https://www.youtube.com/watch?v=ZEEYYvVwJGY
    private class ProblemListAdapter extends ArrayAdapter<String> {
        private int layout;

        private ProblemListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            ViewHolder mainViewholder = null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.infoText = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.titleText = (TextView) convertView.findViewById(R.id.titleText);
                viewHolder.deleteBtn = (Button) convertView.findViewById(R.id.deleteBtn);
                viewHolder.editBtn = (Button) convertView.findViewById(R.id.editBtn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();

            // deleteBtn deletes problem from all 3 lists
            mainViewholder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filteredProblems.removeIndex(position);
                    problemString.remove(position);
                    problemList.getList().remove(filteredProblems.getProblem(position));
                    notifyDataSetChanged();
                }
            });

            // editBtn opens child activity with the chosen problems bundles as extra
            mainViewholder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // index of edited problem is saved so we can delete it later
                    lastPosition = position;

                    Intent openEditor = new Intent(getApplicationContext(), EditProblemActivity.class);
                    // Pass list of emotion objects by using serializable
                    chosenProblem = filteredProblems.getProblem(position);
                    Bundle problem_bundle = new Bundle();
                    problem_bundle.putSerializable("chosenProblem", chosenProblem);
                    openEditor.putExtras(problem_bundle);
                    startActivityForResult(openEditor, 1);

                }
            });
            // display the problem title and info
            mainViewholder.infoText.setText(getItem(position));
            mainViewholder.titleText.setText(filteredProblems.getProblem(position).getProblemTitle());

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
