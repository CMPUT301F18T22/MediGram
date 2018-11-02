package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

public class CareProviderTest extends ActivityInstrumentationTestCase2 {
    public CareProviderTest(){
        super(CareProvider.class);
    }

    /* The other Inherited methods from User are tested in Patient */

    public void testGetUserType(){
        CareProvider careProvider = new CareProvider("house", "some@email.com", "1112223333");

        assertEquals(careProvider.checkUserType(), "CareProvider");
    }

    public void testAddPatient(){
        CareProvider careProvider = new CareProvider("house", "some@email.com", "1112223333");

        Patient patient = new Patient("mesick", "some@email.com", "1112223333");
        careProvider.addPatient(patient);

        assertTrue(careProvider.patientAssigned(patient));

    }

    public void testDeletePatient() {
        CareProvider careProvider = new CareProvider("house", "some@email.com", "1112223333");

        Patient patient = new Patient("mesick", "some@email.com", "1112223333");
        careProvider.addPatient(patient);
        careProvider.deletePatient(patient);

        assertFalse(careProvider.patientAssigned(patient));

    }

    public void testPatientAssigned(){
        CareProvider careProvider = new CareProvider("house", "some@email.com", "1112223333");

        Patient patient = new Patient("mesick", "some@email.com", "1112223333");
        careProvider.addPatient(patient);

        assertTrue(careProvider.patientAssigned(patient));

    }

}
