package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;
import android.test.mock.MockContentProvider;

import java.util.ArrayList;

public class ElasticSearchControllerTest extends ActivityInstrumentationTestCase2 {
    public ElasticSearchControllerTest(){
        super(ElasticSearchController.class);
    }

    public void testAddPatient(){
        String userID = "testcase";
        ElasticSearchController.CreatePatient addPatient = new ElasticSearchController.CreatePatient();
        Patient patient = new Patient(userID, "testcase@email.com", "1337");
        addPatient.execute(patient);

        ElasticSearchController.GetPatient getPatient = new ElasticSearchController.GetPatient();
        try {
            ArrayList<Patient> returnedPatients = getPatient.execute(userID).get();
            assertEquals(patient.getUserID(), returnedPatients.get(0).getUserID());
        }catch (Exception e){
            //TODO offline testing
            e.printStackTrace();
        }
    }

    public void testGetPatient(){
        String userID = "testcase";
        ElasticSearchController.CreatePatient addPatient = new ElasticSearchController.CreatePatient();
        Patient patient = new Patient(userID, "testcase@email.com", "1337");
        addPatient.execute(patient);

        ElasticSearchController.GetPatient getPatient = new ElasticSearchController.GetPatient();

        assertNotNull(getPatient.execute(userID));
    }

    public void testUpdatePatient(){
        String userID = "testcase";
        String updatedUserID = "updated";
        ElasticSearchController.CreatePatient addPatient = new ElasticSearchController.CreatePatient();
        Patient patient = new Patient(userID, "testcase@email.com", "1337");
        addPatient.execute(patient);

        patient.setUserID(updatedUserID);
        ElasticSearchController.UpdatePatient updatePatient = new ElasticSearchController.UpdatePatient();
        updatePatient.execute(patient);

        ElasticSearchController.GetPatient getPatient = new ElasticSearchController.GetPatient();
        try {
            ArrayList<Patient> returnedPatients = getPatient.execute(updatedUserID).get();
            assertEquals(updatedUserID, returnedPatients.get(0).getUserID());
        }catch (Exception e){
            //TODO offline testing
            e.printStackTrace();
        }

    }

    public void testDeleteUser(){
        String userID = "testcase";
        ElasticSearchController.CreatePatient addPatient = new ElasticSearchController.CreatePatient();
        Patient patient = new Patient(userID, "testcase@email.com", "1337");
        addPatient.execute(patient);

        ElasticSearchController.DeleteUser deleteUser = new ElasticSearchController.DeleteUser();
        deleteUser.execute(patient.getJestID());

        ElasticSearchController.GetPatient getPatient = new ElasticSearchController.GetPatient();
        try {
            ArrayList<Patient> returnedPatients = getPatient.execute(userID).get();
            assertNull(returnedPatients.get(0));
        }catch (Exception e){
            //TODO offline testing
            e.printStackTrace();
        }
    }
}
