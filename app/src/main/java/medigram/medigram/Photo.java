package medigram.medigram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Photo implements Serializable {
    public Bitmap bitmap;

    // TODO: Finish this constructor
    public Photo(Bitmap photo) {
        // Take your existing call to BitmapFactory and put it here
        bitmap = photo;
    }

    // Converts the Bitmap into a byte array for serialization
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        if (bitmap != null) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
            byte bitmapBytes[] = byteStream.toByteArray();
            out.write(bitmapBytes, 0, bitmapBytes.length);
        }
    }

    // Deserializes a byte array representing the Bitmap and decodes it
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        if (bitmap != null) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int b;
            while ((b = in.read()) != -1)
                byteStream.write(b);
            byte bitmapBytes[] = byteStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
        }
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void compressor(){
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);

        byte bitmapBytes[] = byteStream.toByteArray();
        this.bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);

    }
}
