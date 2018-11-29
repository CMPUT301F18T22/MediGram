package medigram.medigram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Photo implements Serializable {
    private String bitmapString;

    // TODO: Finish this constructor
    public Photo(Bitmap photo) {
        // Take your existing call to BitmapFactory and put it here
        this.bitmapString = encodeTobase64(photo);
    }

    public Photo() {
        // Take your existing call to BitmapFactory and put it here
        this.bitmapString = encodeTobase64(Bitmap.createBitmap(160, 160, Bitmap.Config.ARGB_8888));
    }

    /*
    // Converts the Bitmap into a byte array for serialization
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        this.bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        byte bitmapBytes[] = byteStream.toByteArray();
        out.write(bitmapBytes, 0, bitmapBytes.length);

    }

    // Deserializes a byte array representing the Bitmap and decodes it
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1)
            byteStream.write(b);
        byte bitmapBytes[] = byteStream.toByteArray();
        this.bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
    */

    public Bitmap getBitmap(){
        byte[] decodedByte = Base64.decode(bitmapString, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

}
