package medigram.medigram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

public class MediGramActivity extends Activity {

    // TODO delete this part
    private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi_gram);

        // TODO delete this part
        test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediGramActivity.this, PatientListActivity.class);
                startActivity(intent);
            }
        });
    }
}
