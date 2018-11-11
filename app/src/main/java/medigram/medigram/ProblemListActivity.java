package medigram.medigram;


import android.content.Context;
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

public class ProblemListActivity extends AppCompatActivity {
    final Context context = this;
    public ProblemList problemList;
    public List<String> problemString;
    public ListView problemsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* test */
        Date date = new Date();

        Problem testproblem = new Problem("TestTitle",
                "TestDescription", date, "Test Bodylocation");
        problemList = new ProblemList();
        problemList.addProblem(testproblem);
        /* end test*/

        setContentView(R.layout.activity_problem_list);

        problemsView = (ListView) findViewById(R.id.ProblemListView);
        problemString = problemList.getList().stream().map(Problem::toString).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new MyListAdapter(this,
                R.layout.problem_list_item, problemString);
        problemsView.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;

        public MyListAdapter(Context context, int resource, List<String> objects) {
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
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.deleteBtn = (Button) convertView.findViewById(R.id.deleteBtn);
                viewHolder.editBtn = (Button) convertView.findViewById(R.id.editBtn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                // remove emotion from emotion list and string from string list
                @Override
                public void onClick(View v) {
                    problemList.removeProblem(position);
                    problemString.remove(position);
                    notifyDataSetChanged();

                }
            });
            mainViewholder.editBtn.setOnClickListener(new View.OnClickListener() {
                // remove emotion from emotion list and string from string list
                @Override
                public void onClick(View v) {
                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;

        }
    }
    public class ViewHolder {
        TextView title;
        Button deleteBtn;
        Button editBtn;
    }

}
