package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Test for PatientSearchInfo class
 * @see PatientSearchInfo
 * @author Xiaohui Liu
 */
public class PatientSearchInfoTest extends ActivityInstrumentationTestCase2 {
    public PatientSearchInfoTest(){
        super(PatientSearchInfo.class);
    }

    public void testGetUserID() {
        String userid = "user12";
        PatientSearchInfo searchInfo = new PatientSearchInfo(userid, 1);

        assertEquals(searchInfo.getUserID(), userid);
    }

    public void testSetUserID() {
        String userid = "user12";
        PatientSearchInfo searchInfo = new PatientSearchInfo(userid, 1);
        String new_userid = "user13";
        searchInfo.setUserID(new_userid);

        assertEquals(searchInfo.getUserID(), new_userid);
    }

    public void testGetNumOfProblems() {
        int numOfProblems = 1;
        PatientSearchInfo searchInfo = new PatientSearchInfo("whatever", 1);

        assertEquals(searchInfo.getNumOfProblems(), numOfProblems);
    }

    public void testSetNumOfProblems() {
        int numOfProblems = 1;
        PatientSearchInfo searchInfo = new PatientSearchInfo("whatever", 1);
        int new_numOfProblems = 2;
        searchInfo.setNumOfProblems(2);

        assertEquals(searchInfo.getNumOfProblems(), new_numOfProblems);
    }
}
