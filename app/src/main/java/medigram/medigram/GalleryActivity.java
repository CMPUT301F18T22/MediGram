package medigram.medigram;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.graphics.*;
import java.util.*;
import java.net.*;

import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

/**
 * Takes a list of bitmaps and displays them as a grid
 * @author Nic Raboy
 */
public class GalleryActivity extends Activity {
    private ImageSwitcher imageSwitcher;
    private Button leftBtn;
    private Button rightBtn;
    private GestureDetector gestureDetector;
    private ArrayList<Bitmap> bitmapList;
    private Patient patient;
    private Record record;
    private int recordIndex, problemIndex, currentImageIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        patient = (Patient) getIntent().getSerializableExtra("patient");
        recordIndex = (int) getIntent().getSerializableExtra("recordIndex");
        problemIndex = (int) getIntent().getSerializableExtra("problemIndex");
        record = patient.getProblems().getProblem(problemIndex).getRecordList().getRecord(recordIndex);
        bitmapList = record.getBitmaps();
        currentImageIndex = 0;

        leftBtn = findViewById(R.id.leftBtn);
        rightBtn = findViewById(R.id.rightBtn);
        imageSwitcher = findViewById(R.id.imageSwitcher);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_XY);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });
        Bitmap bitmap = Bitmap.createScaledBitmap(bitmapList.get(0), 1280, 1280, false);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getApplicationContext().getResources(), bitmap);
        imageSwitcher.setImageDrawable(bitmapDrawable);

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentImageIndex != 0){
                    Bitmap bitmap = Bitmap.createScaledBitmap(bitmapList.get(currentImageIndex-1), 1280, 720, false);
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(getApplicationContext().getResources(), bitmap);
                    imageSwitcher.setImageDrawable(bitmapDrawable);
                    currentImageIndex--;
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentImageIndex != bitmapList.size()-1){
                    Bitmap bitmap = Bitmap.createScaledBitmap(bitmapList.get(currentImageIndex+1), 1280, 720,false);
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(getApplicationContext().getResources(), bitmap);
                    imageSwitcher.setImageDrawable(bitmapDrawable);
                    currentImageIndex++;
                }
            }
        });

    }


}


