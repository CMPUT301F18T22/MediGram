package medigram.medigram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * this is the part that the user can add their comment on wpecific record only for the patient
 * @author Jiaqi Liu
 */
public class RecordAddComment extends AppCompatActivity {
    private EditText texxt1;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_add_comment);
        //getting thenewrecord from the add record activity
        Record newrecord = (Record) getIntent().getSerializableExtra("socool");
        String id = (String)getIntent().getSerializableExtra("notcool");
        texxt1 = findViewById(R.id.texxt1);
        String content = texxt1.getText().toString();
        Comment comment = new Comment(content, id);
        newrecord.addComment(comment);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddRecordActivity.class);
                startActivity(intent);
            }
        });
    }
}
