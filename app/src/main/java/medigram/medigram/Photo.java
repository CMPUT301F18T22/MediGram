package medigram.medigram;

import android.graphics.Bitmap;
import android.provider.ContactsContract;

import java.io.Serializable;

public class Photo implements Serializable {
    private String photoID;


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
