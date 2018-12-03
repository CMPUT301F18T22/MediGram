Some tests require creating a new account to run the rest of the test cases on.
If the test that creates the account fails (because account already exists), the rest of the
tests will also fail. To fix this, change the account ID name to something
unique in the line that says:
solo.enterText((EditText) solo.getView(R.id.signUpUserID), "solotestID2");

The user must also accept permissions that pop up if app is being run for the first time. 