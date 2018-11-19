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
public class EditRecordTitle extends AppCompatActivity {

    private EditText edit;
    private TextView view;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record_title);

        //link all the button and text view and edit text
        edit = (EditText) findViewById(R.id.edit_title);
        view = (TextView) findViewById(R.id.text_view) ;
        save = findViewById(R.id.save);

        //get the specific record from the edit record
        Record newrecord = (Record) getIntent().getSerializableExtra("socool");
        String title= newrecord.getRecordTitle();
        view.setText(title);

        //save button pressed go back to the edit record activity
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edit.getText().toString(); //gets you the contents of edit text
                newrecord.setRecordTitle(content);
                Intent intent=new Intent(getApplicationContext(),EditRecordActivity.class);
                startActivity(intent);
            }
        });



    }
}
