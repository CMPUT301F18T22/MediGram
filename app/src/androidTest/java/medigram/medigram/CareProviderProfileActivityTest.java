package medigram.medigram;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckBox;
import android.widget.EditText;

import com.robotium.solo.Solo;

public class CareProviderProfileActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public CareProviderProfileActivityTest() {
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

    public void test1_EditProfile() {
        //solo.enterText((EditText) solo.getView(R.id.InputCode), "GG9FCT");
        //solo.clickOnButton("Sign In");

        solo.clickOnButton("Edit Profile");
        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.updateUserID));
        solo.enterText((EditText) solo.getView(R.id.updateUserID), "careprovider2");
        solo.clearEditText((EditText) solo.getView(R.id.updateEmail));
        solo.enterText((EditText) solo.getView(R.id.updateEmail), "uitest@gmail.com");
        solo.clearEditText((EditText) solo.getView(R.id.updatePhone));
        solo.enterText((EditText) solo.getView(R.id.updatePhone), "1111111111");

        solo.clickOnButton("Update");
        solo.assertCurrentActivity("Wrong Activity", CareProviderProfileActivity.class);
    }

    public void test2_DeleteProfile() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        solo.clickOnButton("Edit Profile");
        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);

        solo.clickOnButton("Delete");
    }
}
