package medigram.medigram;

import android.media.browse.MediaBrowser;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contains a List of comments, with methods that modify or return comments
 * @author Jeremy Xie
 */
public class CommentList implements Serializable{
    private ArrayList<Comment> commentList = new ArrayList<Comment>();

    /**
     * Adds a comment to the comment list
     * @param comment The comment to be added
     */
    public void addComment(Comment comment){
        commentList.add(comment);
    }

    /**
     * Gets the comment list
     * @return The list of comments
     */
    public ArrayList<Comment> getList() {return commentList;}

    /**
     * Removes a comment at the given index
     * @param index The int index of the comment to be removed
     */
    public void removeIndex(int index){commentList.remove(index);
    }

    public void removeComment(Comment comment){commentList.remove(comment);
    }

    /**
     * Gets the comment at the given index
     * @param index The index of the comment to be returned
     * @return The comment at a specified index
     */
    public void updateComment(int index, Comment comment){
        commentList.set(index, comment);
    }

    /**
     * Gets the comment at the given index
     * @param index The index of the comment to be returned
     * @return The comment at a specified index
     */
    public Comment getComment(int index){return commentList.get(index);
    }

    /**
     * Gets the index of the given comment
     * @param comment The given comment
     * @return The int index of the comment that matches the given comment
     */
    public int getIndex(Comment comment){
        return commentList.indexOf(comment);
    }

    public Boolean commentExist(Comment comment){
        return commentList.contains(comment);
    }

    /**
     * Gets the size of the comment list
     * @return Int size of the comment list
     */
    public int getSize(){
        return commentList.size();
    }

}
