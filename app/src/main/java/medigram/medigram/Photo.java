package medigram.medigram;

import android.graphics.Bitmap;
import android.provider.ContactsContract;

public class Photo {
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
