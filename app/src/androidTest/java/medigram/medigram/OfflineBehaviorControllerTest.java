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
        super(OfflineBehaviorControllerTest.class);
    }

    public void testSavePatient(){
        Context context = InstrumentationRegistry.getTargetContext();
        Patient patient = new Patient("test", "test@email.com", "0001112222");
        OfflineBehaviorController offlineBehaviorController = new OfflineBehaviorController();

        offlineBehaviorController.savePatient(context, patient);

        assertNotNull(offlineBehaviorController.loadPatient(context));
    }

    public void testLoadPatient(){
        Context context = InstrumentationRegistry.getTargetContext();
        Patient patient = new Patient("test", "test@email.com", "0001112222");
        OfflineBehaviorController offlineBehaviorController = new OfflineBehaviorController();

        offlineBehaviorController.savePatient(context, patient);
        Patient loaded = offlineBehaviorController.loadPatient(context);

        assertEquals("Patient", loaded.checkUserType());
    }
}
