package medigram.medigram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RecordAddComment extends AppCompatActivity {
    private TextView texxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_add_comment);

        texxt = (TextView) findViewById(R.id.texxxt);
        String comments=Record.getComment();
        helloTextView.setText(R.string.user_greeting);
    }
}
