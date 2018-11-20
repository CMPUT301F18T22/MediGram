package medigram.medigram;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
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

    public void test1_addRecord(){
        solo.enterText((EditText) solo.getView(R.id.InputUserID), "solotestID");
        solo.clickOnButton("Sign In");
        solo.enterText((EditText) solo.getView(R.id.searchBox), "left arm");
        solo.sendKey(Solo.ENTER);
        solo.clickOnButton("Add Problem");
        solo.assertCurrentActivity("Wrong Activity", EditProblemActivity.class);

        solo.enterText((EditText) solo.getView(R.id.problemTitle), "test problem 1");
        solo.enterText((EditText) solo.getView(R.id.problemDescription), "test problem description");
        solo.enterText((EditText) solo.getView(R.id.problemBodyLocation), "left arm");

        solo.clickOnButton("Confirm");

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", RecordListActivity.class);

        solo.clickOnButton("Add record");
        solo.assertCurrentActivity("Wrong Activity", AddRecordActivity.class);
        solo.enterText((EditText) solo.getView(R.id.recordTitle), "Test Record 1");
        solo.enterText((EditText) solo.getView(R.id.commentText), "Test Record Comment 1");

        Button save = (Button) solo.getView(R.id.save);
        //save.performClick();
        solo.clickOnView(save);
        solo.assertCurrentActivity("Wrong Activity", RecordListActivity.class);
    }

    public void test2_deleteRecord(){
        solo.enterText((EditText) solo.getView(R.id.InputUserID), "solotestID");
        solo.clickOnButton("Sign In");
        solo.clickOnButton("View Problems");
        solo.clickInList(0);

        solo.sleep(500);
        ListView listView = (ListView) solo.getView(R.id.RecordListView);
        View view = listView.getChildAt(0);
        solo.clickOnView((Button)view.findViewById(R.id.recordDeleteBtn));
        solo.assertCurrentActivity("Wrong Activity", RecordListActivity.class);

        solo.goBack();
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", RecordListActivity.class);

        view = listView.getChildAt(0);
        solo.clickOnView((Button)view.findViewById(R.id.deleteBtn));
        solo.sleep(1000);

    }

}
