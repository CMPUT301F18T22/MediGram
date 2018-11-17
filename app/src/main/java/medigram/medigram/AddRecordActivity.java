package medigram.medigram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddRecordActivity extends AppCompatActivity {
    /**
     * This activity displays the problems specified by the user as a list. The list of problems is
     * retrieved from the User given by the parent activity, and then filtered by keywords. Adding or
     * editing a problem is done by a child activity.
     * After adding or editing is done, the User data is updated over the network if available.
     *
     * @author Jiaqi Liu, Zeyu Liu
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
    }

}
