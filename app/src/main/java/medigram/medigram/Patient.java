package medigram.medigram;

import java.io.Serializable;

public class Patient extends User implements Serializable {
    private String emailAddress;
    private String phoneNumber;
    private String userID;
    private String userType = "Patient";
    private ProblemList problemList = new ProblemList();

    public Patient(String userID, String emailAddress, String phoneNumber){
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

    public String checkUserType(){
        return this.userType;
    }

    public ProblemList getProblems() {
        return problemList;
    }

}
