package medigram.medigram;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

public class ProblemListActivityTest extends ActivityInstrumentationTestCase2{
    private Solo solo;

    public ProblemListActivityTest() {
        super(medigram.medigram.LoginActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void test1_AddProblem(){
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

        solo.assertCurrentActivity("Wrong Activity", ProblemListActivity.class);

        solo.goBack();
        solo.clickOnButton("View Problems");
        solo.clickOnButton("Add Problem");

        solo.enterText((EditText) solo.getView(R.id.problemTitle), "test problem 2");
        solo.enterText((EditText) solo.getView(R.id.problemDescription), "test problem 2 description");
        solo.enterText((EditText) solo.getView(R.id.problemBodyLocation), "right arm");
        solo.clickOnButton("Confirm");

        solo.assertCurrentActivity("Wrong Activity", ProblemListActivity.class);
    }

    public void test2_EditProblem(){
        solo.enterText((EditText) solo.getView(R.id.InputUserID), "solotestID");
        solo.clickOnButton("Sign In");
        solo.clickOnButton("View Problems");

        solo.sleep(500);
        ListView listView = (ListView) solo.getView(R.id.ProblemListView);
        View view = listView.getChildAt(0);
        solo.clickOnView((Button)view.findViewById(R.id.editBtn));

        solo.assertCurrentActivity("Wrong Activity", EditProblemActivity.class);

        solo.enterText((EditText) solo.getView(R.id.problemTitle), " edited");
        solo.enterText((EditText) solo.getView(R.id.problemDescription), " edited");

        EditText bodyLocation = (EditText) solo.getView(R.id.problemBodyLocation);
        solo.clearEditText(bodyLocation);
        solo.enterText(bodyLocation, "left leg");

        TextView date = (TextView) solo.getView(R.id.problemDate);
        date.setText("10/10/1999");

        solo.clickOnButton("Confirm");

        solo.assertCurrentActivity("Wrong Activity", ProblemListActivity.class);

    }

    public void test3_DeleteProblem(){
        solo.enterText((EditText) solo.getView(R.id.InputUserID), "solotestID");
        solo.clickOnButton("Sign In");
        solo.clickOnButton("View Problems");

        solo.sleep(500);

        ListView listView = (ListView) solo.getView(R.id.ProblemListView);
        View view = listView.getChildAt(0);
        solo.clickOnView((Button)view.findViewById(R.id.deleteBtn));

        solo.assertCurrentActivity("Wrong Activity", ProblemListActivity.class);
    }
}
