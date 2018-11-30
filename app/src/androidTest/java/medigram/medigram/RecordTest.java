package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

public class RecordTest extends ActivityInstrumentationTestCase2 {
    public RecordTest(){
        super(Record.class);
    }

    public void testSetRecordTitle(){
        Record record = new Record("title", new Date());
        record.setRecordTitle("giorno");

        assertEquals(record.getRecordTitle(), "giorno");
    }

    public void testGetRecordTitle(){
        Record record = new Record("title", new Date());

        assertEquals(record.getRecordTitle(), "title");
    }

    public void testGetDateStarted(){
        Date date = new Date();
        Record record = new Record("title", date);

        assertEquals(record.getDateStarted().toString(), date.toString());
    }

    public void testSetDateStarted(){}

    public void testSetPhotos(){}
    public void testGetPhotos(){}

    public void testSetGeoLocation(){}
    public void testGetGeoLocation(){}

}
