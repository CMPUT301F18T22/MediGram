package medigram.medigram;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckBox;
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
    public void test0_CreateProfile(){
        solo.clickOnButton("Sign Up");
        solo.enterText((EditText) solo.getView(R.id.signUpUserID), "careproviderTest3");
        solo.enterText((EditText) solo.getView(R.id.signUpEmail), "test@email.com");
        solo.enterText((EditText) solo.getView(R.id.signUpPhoneNumber), "0001112222");
        CheckBox patientCheckBox = (CheckBox) solo.getView(R.id.CareProviderCheckBox);
        solo.clickOnView(patientCheckBox);
        solo.clickOnButton("Sign Up");
    }

    public void test2_AddPatient() {
        solo.clickOnButton("View Patients");
        solo.assertCurrentActivity("Wrong Activity", PatientListActivity.class);

        solo.clickOnButton("Add Patient");
        solo.assertCurrentActivity("Wrong Activity", AddPatientActivity.class);
        solo.enterText((EditText) solo.getView(R.id.input_userid), "L5PIV1");
        solo.clickOnButton("Confirm Adding");

        solo.goBackToActivity("PatientListActivity");
        solo.assertCurrentActivity("Wrong Activity", PatientListActivity.class);

    }

    public void test2_ClearData() {
        solo.clickOnButton("Edit Profile");
        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);

        solo.clickOnButton("Delete");
    }

}
