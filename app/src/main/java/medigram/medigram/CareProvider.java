package medigram.medigram;

import java.util.ArrayList;
import java.util.List;

public class CareProvider extends User {
    private PatientList patientList = new PatientList();

    public CareProvider(String userID, String emailAddress, String phoneNumber){
        this.userID = userID;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.userType = "CareProvider";
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

}
