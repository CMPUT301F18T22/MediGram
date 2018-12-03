package medigram.medigram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Converts a given bitmap to a string, and then back to a bitmap.
 *
 * @author Jarred Mahinay
 */
public class Photo implements Serializable {
    private String bitmapString;

    /**
     * Takes a bitmap, converts it to string, and stores it
     * @param photo The bitmap to be converted to a string
     */
    // TODO: Finish this constructor
    public Photo(Bitmap photo) {
        this.bitmapString = encodeTobase64(photo);
    }

    /**
     * If no bitmap is given, generate a blank bitmap
     */
    public Photo() {
        this.bitmapString = encodeTobase64(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
    }

    /**
     * Takes the stored bitmap string and converts it to a bitmap
     * @return bitmap of the stored bitmap string
     */
    public Bitmap getBitmap(){
        byte[] decodedByte = Base64.decode(bitmapString, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    /**
     * The bitmap to bitmap string converter
     * @param image takes a bitmap and converts it to a string
     * @return string of the bitmap
     */

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        //Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    /**
     *
     * @return bitmap string
     */
    public String getBitmapString(){
        return this.bitmapString;
    }

}
