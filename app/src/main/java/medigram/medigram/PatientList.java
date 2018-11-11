package medigram.medigram;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PatientList {
    private ArrayList<Patient> patients = new ArrayList<>();

    public int getSize(){
        return patients.size();
    }

    public void addPatient(Patient patient){
        patients.add(patient);
    }

    public void deletePatient(Patient patient){
        patients.remove(patient);
    }

    public Boolean patientUserIDExist(String userID){
        for (Patient patient : patients){
            if (patient.getUserID().equals(userID)){
                return true;
            }
        }
        return false;
    }

    public Patient getPatientByPosition(int position) {
        return (Patient) Array.get(patients, position);
    }

    // used when care provider tries to add a patient
    public Patient getPatientByID(String userID){
        for (Patient patient : patients){
            if (patient.getUserID().equals(userID)){
                return patient;
            }
        }
        return null;
    }
}
