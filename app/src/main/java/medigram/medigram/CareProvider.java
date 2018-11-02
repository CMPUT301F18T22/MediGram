package medigram.medigram;

import java.util.ArrayList;
import java.util.List;

public class CareProvider extends User {
    private List<Patient> patientList = new ArrayList<Patient>();
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

    public void addPatient(Patient patient){
         patientList.add(patient);
    }
    public void deletePatient(Patient patient){
        patientList.remove(patient);
    }
    public Boolean patientAssigned(Patient patient){
        return patientList.contains(patient);
    }

    public String checkUserType(){
        return this.userType;
    }

}
