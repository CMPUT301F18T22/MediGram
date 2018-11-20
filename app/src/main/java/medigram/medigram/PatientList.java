package medigram.medigram;

import java.io.Serializable;
import java.util.ArrayList;

public class PatientList implements Serializable {
    private ArrayList<Patient> patients = new ArrayList<>();

    /**
     * @return int, size of the list
     */
    public int getSize(){
        return patients.size();
    }

    /**
     * add a patient to the list
     * @param patient
     */
    public void addPatient(Patient patient){
        patients.add(patient);
    }

    /**
     * delete a selected patient from the list
     * @param patient
     */
    public void deletePatient(Patient patient){
        patients.remove(patient);
    }

    /**
     * @param userID
     * @return true if patient's user id exists in patient list, false otherwise
     */
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

    /**
     * used when care provider tries to add a patient
     * @param userID
     * @return patient corresponding to the given user id
     */
    public Patient getPatientByID(String userID){
        for (Patient patient : patients){
            if (patient.getUserID().equals(userID)){
                return patient;
            }
        }
        return null;
    }

    /**
     * @return user ids for all patients in the list
     */
    public ArrayList<String> getUserIDs() {
        ArrayList<String> userIDs = new ArrayList<>();
        for (Patient patient : patients){
            userIDs.add(patient.getUserID());
        }
        return userIDs;
    }

    /**
     * @return a int list containing numbers of problems for all patients in the list
     */
    public int[] getAllNumsOfProblems() {
        int [] numsOfProblems = new int[patients.size()];
        for (int i=0; i<patients.size(); i++){
            numsOfProblems[i] = (patients.get(i).getNumOfProblems());
        }
        return numsOfProblems;
    }
}
