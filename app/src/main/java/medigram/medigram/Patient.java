package medigram.medigram;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

public class Patient extends User implements Serializable {
    @SerializedName("Patient")
    private String userType = "Patient";
    private ProblemList problemList = new ProblemList();

    public Patient(String userid, String emailaddress, String phonenumber){
        this.userID = userid;
        this.emailAddress = emailaddress;
        this.phoneNumber = phonenumber;
    }

    public String checkUserType(){
        return this.userType;
    }

    public ProblemList getProblems() {
        return problemList;
    }

}
