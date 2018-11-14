package medigram.medigram;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.test.mock.MockContext;

/**
 * Created by anas on 11/9/18.
 */

public class OfflineBehaviorControllerTest extends ActivityInstrumentationTestCase2 {
    public OfflineBehaviorControllerTest(){
        super(OfflineBehaviorController.class);
    }

    public void testSavePatient(){
        Context context = InstrumentationRegistry.getTargetContext();
        Patient patient = new Patient("offlinetest", "test@email.com", "0001112222");
        OfflineBehaviorController offlineBehaviorController = new OfflineBehaviorController(context);

        offlineBehaviorController.savePatient(patient);

        assertNotNull(offlineBehaviorController.loadPatient(patient.getUserID()));
    }

    public void testLoadPatient(){
        Context context = InstrumentationRegistry.getTargetContext();
        Patient patient = new Patient("offlinetest", "test@email.com", "0001112222");
        OfflineBehaviorController offlineBehaviorController = new OfflineBehaviorController(context);

        offlineBehaviorController.savePatient(patient);
        Patient loaded = offlineBehaviorController.loadPatient(patient.getUserID());

        assertEquals("Patient", loaded.checkUserType());
    }

    public void testSaveCareProvider(){
        Context context = InstrumentationRegistry.getTargetContext();
        CareProvider careProvider = new CareProvider("offlinetest", "test@email.com", "0001112222");
        OfflineBehaviorController offlineBehaviorController = new OfflineBehaviorController(context);

        offlineBehaviorController.saveCareProvider(careProvider);

        assertNotNull(offlineBehaviorController.loadCareProvider(careProvider.getUserID()));
    }

    public void testLoadCareProvider(){
        Context context = InstrumentationRegistry.getTargetContext();
        CareProvider careProvider = new CareProvider("offlinetest", "test@email.com", "0001112222");
        OfflineBehaviorController offlineBehaviorController = new OfflineBehaviorController(context);

        offlineBehaviorController.saveCareProvider(careProvider);
        CareProvider loaded = offlineBehaviorController.loadCareProvider(careProvider.getUserID());

        assertEquals("CareProvider", loaded.checkUserType());
    }

    public void testDeleteSave(){
        Context context = InstrumentationRegistry.getTargetContext();
        Patient patient = new Patient("offlinetest", "test@email.com", "0001112222");
        OfflineBehaviorController offlineBehaviorController = new OfflineBehaviorController(context);

        offlineBehaviorController.savePatient(patient);
        offlineBehaviorController.deleteSave();

        assertNull(offlineBehaviorController.loadPatient(patient.getUserID()));
    }
}
