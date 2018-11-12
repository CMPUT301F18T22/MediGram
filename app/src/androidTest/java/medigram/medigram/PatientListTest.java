package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

import static org.junit.Assert.assertArrayEquals;

public class PatientListTest extends ActivityInstrumentationTestCase2 {
    public PatientListTest(){
        super(PatientList.class);
    }

    public void testAddPatient(){
        PatientList patientList = new PatientList();
        Patient patient = new Patient("mesick", "some@email.com", "1112223333");

        patientList.addPatient(patient);

        assertTrue(patientList.patientUserIDExist(patient.getUserID()));
    }

    public void testDeletePatient(){
        PatientList patientList = new PatientList();
        Patient patient = new Patient("mesick", "some@email.com", "1112223333");

        patientList.addPatient(patient);
        patientList.deletePatient(patient);

        assertFalse(patientList.patientUserIDExist(patient.getUserID()));
    }

    public void testGetSize(){
        PatientList patientList = new PatientList();
        Patient patient = new Patient("mesick", "some@email.com", "1112223333");

        patientList.addPatient(patient);

        assertEquals(patientList.getSize(), 1);
    }

    public void testPatientUserIDExist(){
        PatientList patientList = new PatientList();
        Patient patient = new Patient("mesick", "some@email.com", "1112223333");
        patientList.addPatient(patient);

        assertTrue(patientList.patientUserIDExist("mesick"));
    }

    public void testGetPatientByID(){
        PatientList patientList = new PatientList();
        Patient patient = new Patient("mesick", "some@email.com", "1112223333");
        patientList.addPatient(patient);

        assertTrue(patientList.patientUserIDExist("mesick"));
    }

    public void testGetPatientByPosition(){
        PatientList patientList = new PatientList();
        Patient patient = new Patient("mesick", "some@email.com", "1112223333");
        patientList.addPatient(patient);
        int position = 0;

        assertEquals(patientList.getPatientByPosition(position), patient);
    }

    public void testGetUserIDs(){
        PatientList patientList = new PatientList();
        Patient patient = new Patient("mesick", "some@email.com", "1112223333");
        patientList.addPatient(patient);

        assertEquals(patientList.getUserIDs().get(0),"mesick");
    }

    public void testGetAllNumsOfProblems(){
        PatientList patientList = new PatientList();
        Patient patient = new Patient("mesick", "some@email.com", "1112223333");
        patientList.addPatient(patient);

        assertEquals(patientList.getAllNumsOfProblems()[0], patient.getNumOfProblems());
    }

}
