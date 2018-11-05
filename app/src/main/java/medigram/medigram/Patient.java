package medigram.medigram;


import java.io.Serializable;

import io.searchbox.annotations.JestId;

public class Patient extends User implements Serializable {
    private String userType = "Patient";
    private ProblemList problemList = new ProblemList();

    @JestId
    private String jestID;

    public Patient(String userid, String emailaddress, String phonenumber){
        this.userID = userid;
        this.emailAddress = emailaddress;
        this.phoneNumber = phonenumber;
    }

    public String checkUserType(){
        return this.userType;
    }

    public void setJestID(String jestID){
        this.jestID = jestID;
    }

    public ProblemList getProblems() {
        return problemList;
    }

}
