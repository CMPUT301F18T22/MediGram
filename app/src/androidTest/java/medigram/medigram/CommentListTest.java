package medigram.medigram;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;

public class CommentListTest extends ActivityInstrumentationTestCase2 {
    public CommentListTest(){
        super(CommentList.class);
    }

    public void testAddComment(){
        CommentList commentList = new CommentList();
        Comment comment = new Comment("Im dying", "Patient");

        commentList.addComment(comment);

        assertTrue(commentList.commentExist(comment));
    }

    public void testRemoveComment(){
        CommentList commentList = new CommentList();
        Comment comment = new Comment("Im dying", "Patient");

        commentList.addComment(comment);
        commentList.removeComment(comment);

        assertFalse(commentList.commentExist(comment));
    }

    public void testCommentExist(){
        CommentList commentList = new CommentList();
        Comment comment = new Comment("Im dying", "Patient");

        commentList.addComment(comment);

        assertTrue(commentList.commentExist(comment));
    }

    public void testGetIndex(){
        CommentList commentList = new CommentList();
        Comment comment = new Comment("Im dying", "Patient");

        commentList.addComment(comment);
        assertEquals(commentList.getIndex(comment), 0);
    }

    public void testGetSize(){
        CommentList commentList = new CommentList();
        Comment comment = new Comment("Im dying", "Patient");

        commentList.addComment(comment);
        assertEquals(commentList.getSize(),1);
    }

}
