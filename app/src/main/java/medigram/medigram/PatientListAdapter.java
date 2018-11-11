/**
 * This is the adapter enabling the patient list to be shown as a ListView
 * Sources:
 * Nguyen Duc Hoang, https://www.youtube.com/watch?v=Q_fDWhqKX7g
 */
package medigram.medigram;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PatientListAdapter extends BaseAdapter {
    Activity context;
    private PatientList patients;
    private static LayoutInflater inflater = null;

    public PatientListAdapter(Activity context, PatientList patients) {
        this.context = context;
        this.patients = patients;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return patients.getSize();
    }

    @Override
    public Patient getItem(int i) {
        return patients.getPatientByPosition(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        itemView = (itemView == null) ? inflater.inflate(R.layout.patient_list_item, null): itemView;
        TextView patientUserID = (TextView) itemView.findViewById(R.id.patient_userid);
        TextView numOfProblems = (TextView) itemView.findViewById(R.id.num_problems);
        Patient selectedPatient = patients.getPatientByPosition(i);
        patientUserID.setText(selectedPatient.getUserID());
        ProblemList problems = selectedPatient.getProblems();
        if (problems != null) {
            numOfProblems.setText(Integer.toString(problems.getSize()));
        } else {
            numOfProblems.setText("0");
        }

        return itemView;
    }

}

