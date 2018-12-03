package medigram.medigram;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.*;

import java.io.File;
import java.util.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

import android.os.Environment;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

public class GalleryActivity extends Activity {
    private ImageSwitcher imageSwitcher;
    private Button leftBtn, slideshowBtn, stopBtn;
    private Button rightBtn;
    private GestureDetector gestureDetector;
    private ArrayList<Bitmap> bitmapList;
    private Patient patient;
    private Record record;
    private int recordIndex, problemIndex, currentImageIndex;
    private int width = 480, height = 800;
    private View.OnClickListener slideListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

//        patient = (Patient) getIntent().getSerializableExtra("patient");
        recordIndex = (int) getIntent().getSerializableExtra("recordIndex");
        problemIndex = (int) getIntent().getSerializableExtra("problemIndex");
        record = (Record) getIntent().getSerializableExtra("record");
        bitmapList = record.getBitmaps();
        currentImageIndex = 0;

        leftBtn = findViewById(R.id.leftBtn);
        rightBtn = findViewById(R.id.rightBtn);
        imageSwitcher = findViewById(R.id.imageSwitcher);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT));
                return myView;
            }
        });

        if (bitmapList.size() > 0) {
            File file = new File(Environment.getExternalStorageDirectory().getPath()
                    , record.getRecordTitle() + Integer.toString(currentImageIndex) + ".jpg");
            if (file.exists()) {
                imageSwitcher.setImageURI(Uri.fromFile(file));
            } else {
                Bitmap bitmap = Bitmap.createScaledBitmap(bitmapList.get(0), width, height, false);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getApplicationContext().getResources(), bitmap);
                imageSwitcher.setImageDrawable(bitmapDrawable);
            }
        }

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentImageIndex != 0  && bitmapList.size() != 0){
                    currentImageIndex--;
                    File file = new File(Environment.getExternalStorageDirectory().getPath()
                            ,record.getRecordTitle()+ Integer.toString(currentImageIndex) +".jpg");

                    if(file.exists()){
                        imageSwitcher.setImageURI(Uri.fromFile(file));
                    }
                    else{
                        Bitmap bitmap = Bitmap.createScaledBitmap(bitmapList.get(currentImageIndex), width, height, false);
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getApplicationContext().getResources(), bitmap);
                        imageSwitcher.setImageDrawable(bitmapDrawable);
                    }
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentImageIndex != bitmapList.size()-1 && bitmapList.size() != 0){
                    currentImageIndex++;
                    File file = new File(Environment.getExternalStorageDirectory().getPath()
                            ,record.getRecordTitle()+ Integer.toString(currentImageIndex) +".jpg");
                    if(file.exists()){
                        imageSwitcher.setImageURI(Uri.fromFile(file));
                    }
                    else{
                        Bitmap bitmap = Bitmap.createScaledBitmap(bitmapList.get(currentImageIndex), width, height, false);
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getApplicationContext().getResources(), bitmap);
                        imageSwitcher.setImageDrawable(bitmapDrawable);
                    }
                }
            }
        });

        slideshowBtn = (Button) findViewById(R.id.slideshowBtn);


        slideListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideshowBtn.setOnClickListener(null);
                startLoop(0);

            }
        };
        slideshowBtn.setOnClickListener(slideListener);



    }

    // delay without putting thread to sleep, unlike Thread.sleep
    private void startLoop(final int i) {
        slideshowBtn.setBackgroundResource(R.drawable.button_style5);
        if(currentImageIndex != bitmapList.size()-1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    actionToBeDone();
                    startLoop(i+1);
                }
            }, 3000); // set delay in ms here
        }
        if(currentImageIndex == bitmapList.size()-1){
            slideshowBtn.setOnClickListener(slideListener);
            slideshowBtn.setBackgroundResource(R.drawable.button_style1);
        }
    }

    private void actionToBeDone() {
        //enter actions you want to be done
        rightBtn.performClick();
    }


}


