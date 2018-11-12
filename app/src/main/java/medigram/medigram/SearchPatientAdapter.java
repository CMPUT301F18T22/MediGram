/**
 * This is the adapter that gives a custom list view
 * Sources
 * https://www.youtube.com/watch?v=NMTUsrBHCrA
 */

package medigram.medigram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchPatientAdapter extends BaseAdapter implements Filterable {
    Context c;
    ArrayList<PatientSearchInfo> searchInfos;
    ArrayList<PatientSearchInfo> temp;
    CustomFilter cs;

    public SearchPatientAdapter(Context c, ArrayList<PatientSearchInfo> searchInfos) {
        this.c = c;
        this.searchInfos = searchInfos;
        this.temp = searchInfos;
    }

    @Override
    public Object getItem(int i) {
        return searchInfos.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.patient_list_item, null);

        TextView userID = row.findViewById(R.id.patient_userid);
        TextView numOfProblems = row.findViewById(R.id.num_problems);
        ImageView patient_icon = row.findViewById(R.id.patient_icon);

        userID.setText(searchInfos.get(i).getUserID());
        numOfProblems.setText("# of problems: " + String.valueOf(searchInfos.get(i).getNumOfProblems()));
        patient_icon.setImageResource(R.drawable.patient_icon);

        return row;
    }

    @Override
    public int getCount() {
        return searchInfos.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Filter getFilter() {
        if (cs ==null) {
            cs = new CustomFilter();
        }

        return cs;
    }

// this is the filter that searches patient
class CustomFilter extends Filter {

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();


            ArrayList<PatientSearchInfo> filters = new ArrayList<>();

            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getUserID().toUpperCase().contains(constraint)) {
                    PatientSearchInfo singleRow = new PatientSearchInfo(temp.get(i).getUserID()
                            , temp.get(i).getNumOfProblems());
                    filters.add(singleRow);
                }
            }
            results.count = filters.size();
            results.values = filters;
        } else {
            results.count = temp.size();
            results.values = temp;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        searchInfos = (ArrayList<PatientSearchInfo>) filterResults.values;
        notifyDataSetChanged();
    }
}

}
