package medigram.medigram;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckBox;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Reference: https://github.com/ta301-ks/lonelyTwitter/blob/lab7UI/app/src/androidTest/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivityTest.java
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public LoginActivityTest(){
        super(medigram.medigram.LoginActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void test1_SignUp(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", CreateAccountActivity.class);

        solo.enterText((EditText) solo.getView(R.id.signUpUserID), "solotestID2");
        solo.enterText((EditText) solo.getView(R.id.signUpEmail), "solotest@email.com");
        solo.enterText((EditText) solo.getView(R.id.signUpPhoneNumber), "0001112222");
        CheckBox patientCheckBox = (CheckBox) solo.getView(R.id.PatientCheckBox);
        solo.clickOnView(patientCheckBox);
        solo.clickOnButton("Sign Up");

        solo.assertCurrentActivity("Wrong Activity", PatientProfileActivity.class);

    }

    public void test2_ClearData() {
        solo.clickOnButton("Edit Profile");
        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);

        solo.clickOnButton("Delete");
    }
}
