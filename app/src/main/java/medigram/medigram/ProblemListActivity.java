package medigram.medigram;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

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
    private ProblemList problemList, filteredProblems;
    private List<String> problemString;
    private ListView problemsView;
    private Problem chosenProblem;
    private ArrayAdapter<String> adapter;
    private String bodyLocation, keyword;
    private int lastPosition, index;
    private EditText keySearch;
    private Patient patient;
    private AccountManager accountManager;
    private User user;
    private View.OnClickListener myClickListener;
    private Bitmap photo1;
    private CareProvider careProvider;


    /**
     * After EditProblemActivity is finished, this method handles data received when returning
     * back to this activity. The edited or newly created problem by the child activity is added to
     * the problem list.
     * @param requestCode The number key that the child activity was opened with.
     *                    1 = Edit problem button, 2 = Add problem button, 3 = ListView item
     * @param resultCode The result code given by the child activity
     *                   RESULT_OK = activity was successful
     * @param data The intent data sent by the child activity. This holds the problem that
     *             was edited.
     */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter.notifyDataSetChanged();
        if (resultCode == Activity.RESULT_OK && data != null) {
            // if EditProblemActivity was opened with edit button
            if (requestCode == 1) {
                problemList.getList().remove(chosenProblem);
                filteredProblems.removeProblem(chosenProblem);
                adapter.remove(chosenProblem.toString());
                //problemString.remove(lastPosition);
                adapter.notifyDataSetChanged();
            }

            // get the new edited problem from child activity
            Bundle bundleObject = data.getExtras();
            chosenProblem = (Problem) bundleObject.getSerializable("editedProblem");
            Log.d("JestID", patient.getJestID());

            // Add the edited problem to the correct index, depending on its date
            for (Problem p: filteredProblems.getList()){
                if (chosenProblem.getDate().before(p.getDate())){
                    index = filteredProblems.getIndex(p);
                    filteredProblems.getList().add(index, chosenProblem);
                    adapter.insert(chosenProblem.toString(),index);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
            // if problem hasn't yet been added, then add it to the very end
            if (!filteredProblems.getList().contains(chosenProblem)){
                filteredProblems.getList().add(chosenProblem);
                adapter.add(chosenProblem.toString());
                adapter.notifyDataSetChanged();
            }

            for (Problem p: filteredProblems.getList()){
                Log.d(p.getProblemTitle(), p.toString());
            }


            // add the new problem to Patient's list
            problemList.addProblem(chosenProblem);
            adapter.notifyDataSetChanged();

            keySearch.setText("");


            accountManager.patientUpdater(patient.getUserID(), patient);

        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            // some stuff that will happen if there's no result
        }

        // if returning from record list activity
        if (requestCode == 3){
            super.onRestart();
            keySearch.setText("");
            patient = accountManager.findPatient(patient.getUserID());
            problemList = patient.getProblems();
            filteredProblems = new ProblemList();
            for (Problem p: problemList.getList()){
                if (keyword.equals("")){
                    filteredProblems.addProblem(p);
                }
                else if (p.getBodyLocation().equals(bodyLocation)) {
                    filteredProblems.addProblem(p);
                }
            }
            problemString = filteredProblems.getList().stream().map(Problem::toString).collect(Collectors.toList());
            adapter.clear();
            adapter.addAll(problemString);
            adapter.notifyDataSetChanged();
            keySearch.setText("");
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountManager = new AccountManager(getApplicationContext());

        setContentView(R.layout.activity_problem_list);
        Button addProblemBtn = (Button) findViewById(R.id.addProblemBtn);
        keySearch = (EditText) findViewById(R.id.keySearch);

        // If user is a patient, then do this:
        //user = (User) getIntent().getSerializableExtra("User");
        if (getIntent().hasExtra("Patient")){
            patient = (Patient) getIntent().getSerializableExtra("Patient");
            problemList = patient.getProblems();
            bodyLocation = (String) getIntent().getSerializableExtra("body location");
            keyword = bodyLocation;
        }
        // If user is a care provider, then do this:
        if (getIntent().hasExtra("CareProvider")){
            patient = (Patient) getIntent().getSerializableExtra("Patient");
            careProvider = (CareProvider) getIntent().getSerializableExtra("CareProvider");
            problemList = patient.getProblems();
            bodyLocation = (String) getIntent().getSerializableExtra("body location");
            keyword = bodyLocation;
            addProblemBtn.setVisibility(View.GONE);
        }

        problemsView = (ListView) findViewById(R.id.ProblemListView);
        problemsView.setTextFilterEnabled(true);

        // filter the problems based off the user specified body part
        // new list, filteredProblems, is used to display list
        filteredProblems = new ProblemList();
        for (Problem p: problemList.getList()){
            if (keyword.equals("")){
                filteredProblems.addProblem(p);
            }
            else if (p.getBodyLocation().equals(keyword)) {
                filteredProblems.addProblem(p);
            }
        }

        // create a list of strings from Problem.getString, and uses it on the list adapter
        problemString = filteredProblems.getList().stream().map(Problem::toString).collect(Collectors.toList());
        adapter = new ProblemListAdapter(this,
                R.layout.problem_list_item, problemString);
        problemsView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // add problem button Initializes a new problem and opens EditProblemActivity with it
        View.OnClickListener addProblemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.requestFocusFromTouch();

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

        /*
        View.OnClickListener myClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                //code to be written to handle the click event

            }
        };
        */

        problemsView.setClickable(true);
        problemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = adapter.getPosition(adapter.getItem(position));
                //index = problemString.indexOf(adapter.getItem(position));
                chosenProblem = filteredProblems.getProblem(index);
                String problemtitle = chosenProblem.getProblemTitle();

                // open record activity here. Add patient or careProvider and chosenProblem
                // as extra
                Intent intent = new Intent(getApplicationContext(), RecordListActivity.class);

                if (getIntent().hasExtra("CareProvider")) {
                    intent.putExtra("CareProvider", careProvider);

                }

                intent.putExtra("Patient", patient);
                intent.putExtra("Problem", chosenProblem);
                intent.putExtra("problemIndex", index);

                //startActivity(intent);
                startActivityForResult(intent, 3);

                Log.d("PROBLEM", chosenProblem.toString());
                Log.d("ADAPTER GET ITEM", adapter.getItem(position));


            }
        });

        // Filters the adapter whenever the user inputs a character in the keyboard
        keySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ProblemListActivity.this.adapter.getFilter().filter(charSequence.toString().replaceAll("\\s+",""));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.notifyDataSetChanged();
            }
        });


    }




    // ListView adapter is from https://www.youtube.com/watch?v=ZEEYYvVwJGY

    /**
     * ListView adapter is from https://www.youtube.com/watch?v=ZEEYYvVwJGY
     * Adapter implements Filterable so that the search bar can filter adapter elements
     */
    private class ProblemListAdapter extends ArrayAdapter<String> implements Filterable{
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
                // Remove these buttons if user is a Care Provider, so they can't edit
                if (getIntent().hasExtra("CareProvider")){
                    viewHolder.deleteBtn.setVisibility(View.INVISIBLE);
                    viewHolder.deleteBtn.setClickable(false);
                    viewHolder.editBtn.setVisibility(View.INVISIBLE);
                    viewHolder.editBtn.setClickable(false);

                }
            }
            mainViewholder = (ViewHolder) convertView.getTag();


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
/*
                    for (Problem p: filteredProblems.getList()){
                        if (p.toString() == adapter.getItem(position)){
                            chosenProblem = p;
                            break;
                        }
                    }
                    */
                    openEditor.putExtra("chosenProblem", chosenProblem);
                    startActivityForResult(openEditor, 1);

                }
            });

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
