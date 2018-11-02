package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

public class CommentTest extends ActivityInstrumentationTestCase2 {
    public CommentTest(){
        super(Comment.class);
    }

    public void testEditText(){
        Comment comment = new Comment("my face looks weird", "Patient");
        comment.editText("me face lookin yarr mighty ugly");

        assertEquals(comment.getText(), "me face lookin yarr mighty ugly");

    }

    public void testGetText(){
        Comment comment = new Comment("my face looks weird", "Patient");

        assertEquals(comment.getText(), "my face looks weird");
    }

    public void testGetSender() {
        Comment comment = new Comment("it's always like that", "CareProvider");

        assertEquals(comment.getSender(), "CareProvider");
    }
}
