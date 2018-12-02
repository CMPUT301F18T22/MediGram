package medigram.medigram;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import java.util.*;
import java.net.*;
import android.widget.*;

/**
 * Takes a list of bitmaps and displays them as a grid
 * @author Nic Raboy
 */
public class GalleryActivity extends Activity {

    private GridView imageGrid;
    private ArrayList<Bitmap> bitmapList;
    private Patient patient;
    private Record record;
    private int recordIndex, problemIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        patient = (Patient) getIntent().getSerializableExtra("patient");
        recordIndex = (int) getIntent().getSerializableExtra("recordIndex");
        problemIndex = (int) getIntent().getSerializableExtra("problemIndex");
        record = patient.getProblems().getProblem(problemIndex).getRecordList().getRecord(recordIndex);
        bitmapList = record.getBitmaps();

        this.imageGrid = (GridView) findViewById(R.id.gridview);
        this.bitmapList = new ArrayList<Bitmap>();

        ImageAdapter imageAdapter = new ImageAdapter(this, this.bitmapList);
        this.imageGrid.setAdapter(imageAdapter);



    }

}
