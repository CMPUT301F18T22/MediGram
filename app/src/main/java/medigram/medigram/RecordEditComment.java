package medigram.medigram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RecordEditComment extends AppCompatActivity {

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
        newrecord.getComments().addComment(comment);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),EditRecordActivity.class);
                startActivity(intent);
            }
        });
    }
}
