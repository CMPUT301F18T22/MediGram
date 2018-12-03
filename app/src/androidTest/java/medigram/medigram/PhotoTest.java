package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

public class PhotoTest extends ActivityInstrumentationTestCase2 {
    private Photo photo, newPhoto;
    public PhotoTest(){
        super(Photo.class);
    }

    public void test1_GetSize(){
        photo = new Photo();
        newPhoto = new Photo();
        assertEquals(newPhoto.getBitmap().getByteCount(), photo.getBitmap().getByteCount());
    }
    public void test2_GetString(){
        photo = new Photo();
        newPhoto = new Photo();
        assertEquals(newPhoto.getBitmapString(),photo.getBitmapString());
    }
}
