package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;
import android.test.mock.MockContentProvider;

import java.util.ArrayList;
import java.util.Date;

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

    // Commented out because Update is hard to test, since the it updates to slow and findPatient is faster and does not find the new update.

//    public void testUpdatePatient(){
//        String userID = "testcase";
//        String updatedUserID = "updatedtest";
//
//        ElasticSearchController.GetPatient getPatient = new ElasticSearchController.GetPatient();
//        ElasticSearchController.UpdatePatient updatePatient = new ElasticSearchController.UpdatePatient();
//
//        ElasticSearchController.CreatePatient addPatient = new ElasticSearchController.CreatePatient();
//        Patient patient = new Patient(userID, "testcase@email.com", "1337");
//        addPatient.execute(patient);
//
//
////        ElasticSearchController.DeleteUser deleteUser = new ElasticSearchController.DeleteUser();
////        deleteUser.execute(userID);
////        ElasticSearchController.DeleteUser deleteUser1 = new ElasticSearchController.DeleteUser();
////        deleteUser1.execute(updatedUserID);
//
//
//        patient.setUserID(updatedUserID);
//        patient.getProblems().addProblem(new Problem("newishTitle", "description", new Date(), "33"));
//        updatePatient.execute(patient);
//
//        try {
//            ArrayList<Patient> returnedPatients = getPatient.execute(patient.getUserID()).get();
//
//            System.out.println("Returned Size");
//            System.out.println(returnedPatients.size());
//            System.out.println("Returned JestID");
//            for (Patient pat : returnedPatients){
//                System.out.println(pat.getJestID());
//            }
//            System.out.println("Patient");
//            System.out.println(patient.getJestID());
//
//            assertEquals(patient.getJestID(), returnedPatients.get(returnedPatients.size()-1).getJestID());
//        }catch (Exception e){
//            //TODO offline testing
//            e.printStackTrace();
//        }
//
//    }

    public void testDeleteUser(){
        String userID = "testcase";
        ElasticSearchController.CreatePatient addPatient = new ElasticSearchController.CreatePatient();
        Patient patient = new Patient(userID, "testcase@email.com", "1337");
        addPatient.execute(patient);

        ElasticSearchController.DeleteUser deleteUser = new ElasticSearchController.DeleteUser();
        deleteUser.execute(patient.getUserID());

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
