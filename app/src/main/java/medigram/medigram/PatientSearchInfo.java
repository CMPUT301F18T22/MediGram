package medigram.medigram;

public class PatientSearchInfo {
    int icon = R.drawable.patient_icon;
    String userID;
    int numOfProblems;

    public PatientSearchInfo(String userID, int numOfProblems) {
        this.userID = userID;
        this.numOfProblems = numOfProblems;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getNumOfProblems() {
        return this.numOfProblems;
    }

    public void setNumOfProblems(int numOfProblems) {
        this.numOfProblems = numOfProblems;
    }
}

