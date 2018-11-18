package medigram.medigram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RecordAddComment extends AppCompatActivity {
    private EditText texxt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_add_comment);

        Record newrecord = (Record) getIntent().getSerializableExtra("socool");
        String id = (String)getIntent().getSerializableExtra("notcool");
        texxt1 = findViewById(R.id.texxt1);
        String content = texxt1.getText().toString();
        Comment comment = new Comment(content, id);
        newrecord.addComment(comment);
    }
}
