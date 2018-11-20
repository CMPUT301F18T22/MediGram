package medigram.medigram;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

public class Patient extends User implements Serializable {
    @SerializedName("Patient")
    private String userType = "Patient";
    private ProblemList problemList = new ProblemList();

    @JestId
    private String jestID;

    public Patient(String userid, String emailaddress, String phonenumber){
        this.userID = userid;
        this.emailAddress = emailaddress;
        this.phoneNumber = phonenumber;
    }

    /**
     * checks the user's role
     * @return String: one of "patient" and "care provider"
     */
    public String checkUserType(){
        return this.userType;
    }

    /**
     * get the problem list of the patient
     * @return list of problems
     */
    public ProblemList getProblems() {
        return problemList;
    }

    /**
     * get the size of a patient's problem list
     * @return size of the problem list
     */
    public int getNumOfProblems() {
        return problemList.getSize();
    }

    /**
     * get jestID of patient
     * @return jestID
     */
    public String getJestID() {
        return jestID;
    }

    /**
     * set a given jestID to patient
     * @param jestID
     */
    public void setJestID(String jestID) {
        this.jestID = jestID;
    }
}
