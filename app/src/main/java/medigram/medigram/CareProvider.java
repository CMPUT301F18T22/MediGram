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

    public void setJestID(String jestID){
        this.jestID = jestID;
    }

    public String checkUserType(){
        return this.userType;
    }

}
