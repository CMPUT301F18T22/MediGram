package medigram.medigram;

import com.robotium.solo.Solo;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.CheckBox;
import android.widget.EditText;

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

    public void testAddProblem(){
        solo.enterText((EditText) solo.getView(R.id.InputUserID), "solotestID");
        solo.clickOnButton("Sign In");
        solo.enterText((EditText) solo.getView(R.id.searchBox), "left arm");
        solo.sendKey(Solo.ENTER);
        solo.clickOnButton("Add Problem");
        solo.assertCurrentActivity("Wrong Activity", EditProblemActivity.class);

        solo.enterText((EditText) solo.getView(R.id.problemTitle), "test problem 1");
        solo.enterText((EditText) solo.getView(R.id.problemDescription), "test problem description");

        solo.clickOnButton("Confirm");

        solo.assertCurrentActivity("Wrong Activity", ProblemListActivity.class);









    }
}
