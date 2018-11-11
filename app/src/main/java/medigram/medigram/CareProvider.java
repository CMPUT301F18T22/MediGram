package medigram.medigram;

import java.io.Serializable;

public class CareProvider extends User implements Serializable {
    private PatientList patientList = new PatientList();
    private String emailAddress;
    private String phoneNumber;
    private String userID;
    private String userType = "CareProvider";

    public CareProvider(String userID, String emailAddress, String phoneNumber){
        this.userID = userID;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    // return the patient list the care provider is assigned to
    public PatientList getAssignedPatients() {return patientList;}

    public void assignPatient(Patient patient){
         patientList.addPatient(patient);
    }

    public void unassignPatient(Patient patient){
        patientList.deletePatient(patient);
    }

    public Boolean patientAssigned(Patient patient){
        return patientList.patientUserIDExist(patient.getUserID());
    }
    public Boolean patientAssigned(String userID) {
        return patientList.patientUserIDExist(userID);
    }

    public String checkUserType(){
        return this.userType;
    }

}
