package medigram.medigram;

import java.util.ArrayList;

public class Patient extends User{
    private ProblemList problemList = new ProblemList();

    public Patient(String userID, String emailAddress, String phoneNumber){
        this.userID = userID;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.userType = "Patient";
    }

    public ProblemList getProblems() {
        return problemList;
    }

}
