package medigram.medigram;

import java.io.Serializable;
import java.util.ArrayList;

public class PatientList implements Serializable {
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
        return patients.get(position);
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

    public ArrayList<String> getUserIDs() {
        ArrayList<String> userIDs = new ArrayList<>();
        for (Patient patient : patients){
            userIDs.add(patient.getUserID());
        }
        return userIDs;
    }
}
