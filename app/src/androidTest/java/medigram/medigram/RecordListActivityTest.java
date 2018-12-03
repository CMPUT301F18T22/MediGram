package medigram.medigram;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

public class RecordListActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public RecordListActivityTest() {
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

    public void test2_addRecord(){
        solo.clickOnButton("View Problems");
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", RecordListActivity.class);

        solo.clickOnButton("Add record");
        solo.assertCurrentActivity("Wrong Activity", AddRecordActivity.class);
        solo.enterText((EditText) solo.getView(R.id.recordTitle), "Test Record 1");
        solo.enterText((EditText) solo.getView(R.id.commentText), "Test Record Comment 1");

        Button save = (Button) solo.getView(R.id.save);
        solo.clickOnView(save);
        solo.assertCurrentActivity("Wrong Activity", RecordListActivity.class);
    }

    public void test3_deleteRecord(){
        solo.clickOnButton("View Problems");
        solo.clickInList(0);

        solo.sleep(1000);
        solo.clickOnButton("Add record");
        solo.assertCurrentActivity("Wrong Activity", AddRecordActivity.class);
        solo.enterText((EditText) solo.getView(R.id.recordTitle), "ui test title");
        solo.enterText((EditText) solo.getView(R.id.commentText), "Test Record Comment 1");

        Button save = (Button) solo.getView(R.id.save);
        solo.clickOnView(save);

        ListView listView = (ListView) solo.getView(R.id.recordListView);
        View view = listView.getChildAt(0);
        solo.clickOnView((Button)view.findViewById(R.id.recordDeleteBtn));
        solo.assertCurrentActivity("Wrong Activity", RecordListActivity.class);
    }
    public void test4_ClearData(){
        solo.clickOnButton("Edit Profile");
        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);

        solo.clickOnButton("Delete");
    }



}
