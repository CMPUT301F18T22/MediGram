/**
 * This is the patient's info shown in patient list, i.e., patient icon & user id & number of problems
 * @see medigram.medigram.Patient
 * @author Xiaohui Liu
 */
package medigram.medigram;

public class PatientSearchInfo {
    int icon = R.drawable.patient_icon;
    String userID;
    int numOfProblems;

    public PatientSearchInfo(String userID, int numOfProblems) {
        this.userID = userID;
        this.numOfProblems = numOfProblems;
    }

    /**
     * get user's id
     * @return userID
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * set the userID to a patient's searchInfo
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * get the number of problems
     * @return numOfProblems
     */
    public int getNumOfProblems() {
        return this.numOfProblems;
    }

    /**
     * set the number of problems
     * @param numOfProblems
     */
    public void setNumOfProblems(int numOfProblems) {
        this.numOfProblems = numOfProblems;
    }
}

