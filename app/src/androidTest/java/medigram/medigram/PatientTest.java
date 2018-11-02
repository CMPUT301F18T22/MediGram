package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

public class PatientTest extends ActivityInstrumentationTestCase2 {
    public PatientTest(){
        super(Patient.class);
    }

    public void testGetUserID(){
        String userid = "user12";
        Patient patient = new Patient(userid, "null", "1112223333");

        assertEquals(patient.getUserID(), userid);
    }

    public void testGetEmail(){
        String email = "some@email.com";
        Patient patient = new Patient("null", email, "1112223333");

        assertEquals(patient.getEmailAddress(), email);

    }

    public void testGetPhoneNumber(){
        String phoneNumber = "7801112222";
        Patient patient = new Patient("null", "some@email.com", phoneNumber);

        assertEquals(patient.getPhoneNumber(), phoneNumber);

    }

    public void testGetUserType(){
        Patient patient = new Patient("null", "some@email.com", "1112223333");

        assertEquals(patient.checkUserType(), "patient");
    }
}
