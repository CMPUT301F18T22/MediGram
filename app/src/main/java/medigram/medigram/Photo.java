package medigram.medigram;

import android.graphics.Bitmap;
import android.provider.ContactsContract;

import java.io.Serializable;

public class Photo implements Serializable {
    private String photoID;


    private Integer size;
    private Bitmap picture;
    private String bodyLocation;

    public Integer getSize() {
        return size;
    }

    public String getBodyLocation() {
        return bodyLocation;
    }

    public void setBodyLocation(String bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public void compress(){}

    public void decompress(){}
}
