package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

public class RecordListTest extends ActivityInstrumentationTestCase2 {
    public RecordListTest(){
        super(RecordList.class);
    }
/**
    public void testAddRecord(){
        RecordList recordList = new RecordList();
        Record record = new Record("title", new Comment("blah", "doe"), new Date());

        recordList.addRecord(record);

        assertTrue(recordList.recordExist(record));

    }

    public void testRemoveRecord(){
        RecordList recordList = new RecordList();
        Record record = new Record("title", new Comment("blah", "doe"), new Date());

        recordList.addRecord(record);
        recordList.removeRecord(record);

        assertFalse(recordList.recordExist(record));
    }

    public void testRecordExist(){
        RecordList recordList = new RecordList();
        Record record = new Record("title", new Comment("blah", "doe"), new Date());

        recordList.addRecord(record);

        assertTrue(recordList.recordExist(record));
    }

    public void testGetIndex(){
        RecordList recordList = new RecordList();
        Record record = new Record("title", new Comment("blah", "doe"), new Date());

        recordList.addRecord(record);

        assertEquals(recordList.getIndex(record), 0);
    }


    public void testGetSize(){
        RecordList recordList = new RecordList();
        Record record = new Record("title", new Comment("blah", "doe"), new Date());

        recordList.addRecord(record);

        assertEquals(recordList.getSize(), 1);

    }
*/

}
