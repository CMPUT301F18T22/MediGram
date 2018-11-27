package medigram.medigram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.annotations.JestId;

public class CareProvider extends User implements Serializable {
    private PatientList patientList = new PatientList();
    private String userType = "CareProvider";

    @JestId
    private String jestID;

    public CareProvider(String userID, String emailAddress, String phoneNumber){
        this.userID = userID;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the patient list the care provider is assigned to
     */

    public PatientList getAssignedPatients() {return patientList;}

    /**
     * assign a patient to care provider
     * @param patient
     */
    public void assignPatient(Patient patient){
         patientList.addPatient(patient);
    }

    /**
     * unassign a patient from a care provider
     * @param patient
     */
    public void unassignPatient(Patient patient){
        patientList.deletePatient(patient);
    }

    /**
     * check if a patient is assigned to care provider by user id
     * @param userID
     * @return true if patient is assigned to the care provider, false otherwise
     */
    public Boolean patientAssigned(String userID) {
        return patientList.patientUserIDExist(userID);
    }

    /**
     * check if a patient is assigned to care provider by patient object
     * @param patient
     * @return true if patient is assigned to the care provider, false otherwise
     */
    public Boolean patientAssigned(Patient patient){
        return patientList.patientUserIDExist(patient.getUserID());
    }

    /**
     * check the user's role
     * @return type of user
     */
    public String checkUserType(){
        return this.userType;
    }

    /**
     * get jestID of care provider
     * @return jestID
     */
    public String getJestID() {
        return jestID;
    }

    /**
     * set jestID of care provider
     * @param jestID
     */
    public void setJestID(String jestID) {
        this.jestID = jestID;
    }

}
