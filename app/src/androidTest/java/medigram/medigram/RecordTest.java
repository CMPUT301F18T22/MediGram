package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

public class RecordTest extends ActivityInstrumentationTestCase2 {
    public RecordTest(){
        super(Record.class);
    }

    public void testSetRecordTitle(){
        Record record = new Record("title", "commenty", new Date(), "11");
        record.setRecordTitle("giorno");

        assertEquals(record.getRecordTitle(), "giorno");
    }

    public void testGetRecordTitle(){
        Record record = new Record("title", "commenty", new Date(), "11");

        assertEquals(record.getRecordTitle(), "title");
    }

    public void testSetComment(){
        Record record = new Record("title", "commenty", new Date(), "11");
        record.setComment("quincy");

        assertEquals(record.getComment(), "quincy");
    }

    public void testGetComment(){
        Record record = new Record("title", "commenty", new Date(), "11");

        assertEquals(record.getComment(), "commenty");
    }

    public void testGetDateStarted(){
        Date date = new Date();
        Record record = new Record("title", "commenty", date, "11");

        assertEquals(record.getDateStarted().toString(), date.toString());
    }

    public void testSetBodyLocation(){
        Record record = new Record("title", "commenty", new Date(), "11");
        record.setBodyLocation("37");

        assertEquals(record.getBodyLocation(), "37");
    }

    public void testGetBodyLocation(){
        Record record = new Record("title", "commenty", new Date(), "11");

        assertEquals(record.getBodyLocation(), "11");
    }
}
