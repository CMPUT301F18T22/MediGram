package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

public class RecordTest extends ActivityInstrumentationTestCase2 {
    public RecordTest(){
        super(Record.class);
    }
/**
    public void testSetRecordTitle(){
        Record record = new Record("title", new Comment("blah", "doe"), new Date());
        record.setRecordTitle("giorno");

        assertEquals(record.getRecordTitle(), "giorno");
    }

    public void testGetRecordTitle(){
        Record record = new Record("title", new Comment("blah", "doe"), new Date());

        assertEquals(record.getRecordTitle(), "title");
    }

    public void testAddComment(){
        Record record = new Record("title", new Comment("blah", "doe"), new Date());

        Comment comment = new Comment("yada", "eod");
        record.addComment(comment);

        assertTrue(record.commentExist(comment));
    }

    public void testDeleteComment(){
        Record record = new Record("title", new Comment("blah", "doe"), new Date());

        Comment comment = new Comment("yada", "eod");
        record.addComment(comment);
        record.deleteComment(comment);

        assertFalse(record.commentExist(comment));
    }

    public void testCommentExist(){
        Comment comment = new Comment("blah", "doe");
        Record record = new Record("title", comment, new Date());

        assertTrue(record.commentExist(comment));
    }

    public void testGetDateStarted(){
        Date date = new Date();
        Record record = new Record("title", new Comment("blah", "doe"), date);

        assertEquals(record.getDateStarted().toString(), date.toString());
    }

    public void testSetDateStarted(){}

    public void testSetPhotos(){}
    public void testGetPhotos(){}

    public void testSetGeoLocation(){}
    public void testGetGeoLocation(){}
*/
}
