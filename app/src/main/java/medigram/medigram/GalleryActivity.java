package medigram.medigram;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import java.util.*;
import java.net.*;
import android.widget.*;

public class GalleryActivity extends Activity {

    private GridView imageGrid;
    private ArrayList<Bitmap> bitmapList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        this.imageGrid = (GridView) findViewById(R.id.gridview);
        this.bitmapList = new ArrayList<Bitmap>();

        this.imageGrid.setAdapter(new ImageAdapter(this, this.bitmapList));

    }

}
