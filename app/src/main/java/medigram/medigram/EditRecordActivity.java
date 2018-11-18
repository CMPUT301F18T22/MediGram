package medigram.medigram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditRecordActivity extends AppCompatActivity {
    private Button edittitle;
    private Button editpicture;
    private Button editcomment;
    private Button editdate;
    private Button editgeolocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        edittitle = findViewById(R.id.edit_title);
        editpicture = findViewById(R.id.edit_picture);
        editcomment = findViewById(R.id.edit_comment);
        editgeolocation = findViewById(R.id.edit_geolocation);
        editdate = findViewById(R.id.edit_dates);


        //getting the specific record from record view
        Record editrecord = (Record )getIntent().getSerializableExtra("Record");

//handle the edit title,suppose to jump to the edit record title activity and user can edit the title and then update the record
        edittitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditRecordTitle .class);
                intent.putExtra("socool", editrecord);
                startActivity(intent);
            }
        });

        //handle the edit picture activity the user  can go to the galary and view all the picture that have been taken and try to add or delete picture
        //not sure useing gallery or new activity  need discussion
        editpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        editcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        editgeolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
}
