package medigram.medigram;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Reference: https://github.com/ta301-ks/lonelyTwitter/blob/lab7UI/app/src/androidTest/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivityTest.java
 */
public class PatientListActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public PatientListActivityTest(){
        super(medigram.medigram.LoginActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testAddPatient() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.InputCode), "careprovider1");
        solo.clickOnButton("Sign In");
        solo.assertCurrentActivity("Wrong Activity", CareProviderProfileActivity.class);

        solo.clickOnButton("View Patients");
        solo.assertCurrentActivity("Wrong Activity", PatientListActivity.class);

        solo.clickOnButton("Add Patient");
        solo.assertCurrentActivity("Wrong Activity", AddPatientActivity.class);
        solo.enterText((EditText) solo.getView(R.id.input_userid), "solotestID");
        solo.clickOnButton("Confirm adding");

        solo.goBackToActivity("PatientListActivity");

        solo.assertCurrentActivity("Wrong Activity", PatientListActivity.class);

    }

}
