package medigram.medigram;

import java.util.ArrayList;
import java.util.List;

public class CareProvider extends User {
    private List<Patient> patientList = new ArrayList<Patient>();

    public void addPatient(Patient patient){
        patientList.add(patient);
    }
    public void deletePatient(Patient patient){
        patientList.remove(patient);
    }
    public Boolean patientExists(Patient patient){
        patientList.contains(patient);
    }

}
