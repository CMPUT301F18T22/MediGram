package medigram.medigram;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

public class AccountManagerTest extends ActivityInstrumentationTestCase2 {
    public AccountManagerTest(){
        super(AccountManager.class);
    }

    /**
     * Tests Account Manager's addPatient method
     * @see Patient
     * @see AccountManager
     */
    public void testAddPatient(){
        Context mockContext = InstrumentationRegistry.getTargetContext();
        AccountManager accountManager = new AccountManager(mockContext);
        accountManager.addPatient(new Patient("PatientAddTest", "AccountManagerTest@email.com", "1112223333"));

        assertNotNull(accountManager.findPatient("PatientAddTest"));
    }

    /**
     * Tests Account Manager's addCareProvider method
     * @see Patient
     * @see AccountManager
     */
    public void testAddCareProvider(){
        Context mockContext = InstrumentationRegistry.getTargetContext();
        AccountManager accountManager = new AccountManager(mockContext);
        accountManager.addCareProvider(new CareProvider("CareProviderAddTest", "AccountManagerTest@email.com", "1112223333"));

        assertNotNull(accountManager.findCareProvider("CareProviderAddTest"));
    }

    /**
     * Tests Account Manager's findPatient method
     * @see Patient
     * @see AccountManager
     */
    public void testPatientFinder(){
        Context mockContext = InstrumentationRegistry.getTargetContext();
        AccountManager accountManager = new AccountManager(mockContext);
        Patient patient = new Patient("PatientFindTest", "AccountManagerTest@email.com", "1112223333");
        accountManager.addPatient(patient);

        assertEquals(patient.getUserID(), accountManager.findPatient("PatientFindTest").getUserID());
    }

    /**
     * Tests Account Manager's findCareProvider method
     * @see CareProvider
     * @see AccountManager
     */
    public void testCareProviderFinder(){
        Context mockContext = InstrumentationRegistry.getTargetContext();
        AccountManager accountManager = new AccountManager(mockContext);
        CareProvider careProvider = new CareProvider("CareProviderFindTest", "AccountManagerTest@email.com", "1112223333");
        accountManager.addCareProvider(careProvider);

        assertEquals(careProvider.getUserID(), accountManager.findCareProvider("CareProviderFindTest").getUserID());
    }

    /**
     * Tests Account Manager's patientUpdater method.
     * @see Patient
     * @see AccountManager
     */
    public void testPatientUpdater(){
        Context mockContext = InstrumentationRegistry.getTargetContext();
        AccountManager accountManager = new AccountManager(mockContext);
        Patient patient = new Patient("newPatient", "AccountManagerTest@email.com", "1112223333");

        accountManager.addPatient(patient);
        patient.setUserID("patientUpdated");
        accountManager.patientUpdater(patient);

        assertNotNull(accountManager.findPatient("patientUpdated"));
    }

    /**
     * Tests Account Manager's careProviderUpdater method.
     * @see CareProvider
     * @see AccountManager
     */
    public void testCareProviderUpdater(){
        Context mockContext = InstrumentationRegistry.getTargetContext();
        AccountManager accountManager = new AccountManager(mockContext);
        CareProvider careProvider = new CareProvider("newCareProvider", "AccountManagerTest@email.com", "1112223333");

        accountManager.addCareProvider(careProvider);
        careProvider.setUserID("careProviderUpdated");
        accountManager.careProviderUpdater(careProvider);

        assertNotNull(accountManager.findCareProvider("careProviderUpdated"));
    }

    /**
     * Tests Account Manager's accountDeleter method
     * @see AccountManager
     */
    public void testDeleteAccount(){
        Context mockContext = InstrumentationRegistry.getTargetContext();
        AccountManager accountManager = new AccountManager(mockContext);
        CareProvider careProvider = new CareProvider("DeleteTest", "AccountManagerTest@email.com", "1112223333");

        accountManager.addCareProvider(careProvider);
        accountManager.accountDeleter(careProvider.getJestID());

        assertNull(accountManager.findCareProvider("DeleteTest"));
    }
}
