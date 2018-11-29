package medigram.medigram;

import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

import java.lang.reflect.Method;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;

import static medigram.medigram.PermissionRequest.verifyPermission;

public class Photo implements Serializable {
    private String photoID;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    String mCurrentPhotoPath;

    private Integer size;
    private Bitmap picture;
    private String bodyLocation;

    /**
     * get the size of a photo
     * @return size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * get body location
     * @return bodyLocation
     */
    public String getBodyLocation() {
        return bodyLocation;
    }

    /**
     * set the body location of photo
     * @param bodyLocation
     */
    public void setBodyLocation(String bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    /**
     * not used yet, might be used later
     */
    public void compress(){}

    /**
     * not used yet, might be used later
     */
    public void decompress(){}






}
