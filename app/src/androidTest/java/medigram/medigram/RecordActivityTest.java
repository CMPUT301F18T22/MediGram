package medigram.medigram;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

public class RecordActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public RecordActivityTest() {
        super(medigram.medigram.LoginActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void test0_setUp(){

        solo.clickOnButton("Sign Up");

        solo.enterText((EditText) solo.getView(R.id.signUpUserID), "solotestID2");
        solo.enterText((EditText) solo.getView(R.id.signUpEmail), "solotest@email.com");
        solo.enterText((EditText) solo.getView(R.id.signUpPhoneNumber), "0001112222");
        CheckBox patientCheckBox = (CheckBox) solo.getView(R.id.PatientCheckBox);
        solo.clickOnView(patientCheckBox);
        solo.clickOnButton("Sign Up");

    }

    public void test1_setup(){
        solo.clickOnButton("View Problems");
        solo.clickOnButton("Add Problem");
        solo.assertCurrentActivity("Wrong Activity", EditProblemActivity.class);

        solo.enterText((EditText) solo.getView(R.id.problemTitle), "test problem 1");
        solo.enterText((EditText) solo.getView(R.id.problemDescription), "test problem description");
        solo.enterText((EditText) solo.getView(R.id.problemBodyLocation), "left arm");

        solo.clickOnButton("Confirm");

        solo.assertCurrentActivity("Wrong Activity", ProblemListActivity.class);
        solo.goBack();

    }

    public void test2_SetTitle() {
        solo.clickOnButton("View Problems");
        solo.clickInList(0);
        solo.sleep(1000);

        solo.clickOnButton("Add record");
        solo.assertCurrentActivity("Wrong Activity", AddRecordActivity.class);
        solo.enterText((EditText) solo.getView(R.id.recordTitle), "Test set title");
        solo.enterText((EditText) solo.getView(R.id.commentText), "Test Record Comment 1");
        Button save = (Button) solo.getView(R.id.save);
        solo.clickOnView(save);
        solo.assertCurrentActivity("Wrong Activity", RecordListActivity.class);

        solo.clickInList(0);
        solo.sleep(1000);
        solo.clearEditText((EditText) solo.getView(R.id.recordTitle));
        solo.enterText((EditText) solo.getView(R.id.recordTitle), "uitest");
        solo.clickOnButton("Set Title");
    }

    public void test3_AddComment(){
        solo.clickOnButton("View Problems");
        solo.clickInList(0);
        solo.sleep(1000);

        solo.clickOnButton("Add record");
        solo.assertCurrentActivity("Wrong Activity", AddRecordActivity.class);
        solo.enterText((EditText) solo.getView(R.id.recordTitle), "Test set add comment");
        solo.enterText((EditText) solo.getView(R.id.commentText), "Test Record Comment 1");
        Button save = (Button) solo.getView(R.id.save);
        solo.clickOnView(save);
        solo.assertCurrentActivity("Wrong Activity", RecordListActivity.class);

        solo.clickInList(0);
        solo.sleep(1000);
        solo.clickOnButton("Add Comment");
        solo.enterText((EditText) solo.getView(R.id.enterComment), "ui testing comment");
        solo.clickOnButton("confirm");
        solo.assertCurrentActivity("Wrong Activity", RecordActivity.class);

    }

    public void test4_AddOrViewGeolocation() {
        solo.clickOnButton("View Problems");
        solo.clickInList(0);
        solo.sleep(1000);

        solo.clickOnButton("Add record");
        solo.assertCurrentActivity("Wrong Activity", AddRecordActivity.class);
        solo.enterText((EditText) solo.getView(R.id.recordTitle), "Test geo location");
        solo.enterText((EditText) solo.getView(R.id.commentText), "Test Record Comment 1");
        Button save = (Button) solo.getView(R.id.save);
        solo.clickOnView(save);
        solo.assertCurrentActivity("Wrong Activity", RecordListActivity.class);

        solo.clickInList(0);
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.addGeo));
        solo.assertCurrentActivity("Wrong Activity", MapsActivity.class);
    }

    public void test5_ClearData(){
        solo.clickOnButton("Edit Profile");
        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);

        solo.clickOnButton("Delete");
    }

    /*
    public void testViewGallery() {
        solo.enterText((EditText) solo.getView(R.id.InputUserID), "solotestID");
        solo.clickOnButton("Sign In");
        solo.clickOnButton("View Problems");
        solo.clickInList(0);

        solo.sleep(1000);

        solo.clickInList(0);
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.viewPicture));
        solo.assertCurrentActivity("Wrong Activity", GalleryActivity.class);
    }
    */

}
