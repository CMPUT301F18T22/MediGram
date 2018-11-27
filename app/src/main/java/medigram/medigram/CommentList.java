package medigram.medigram;

import android.media.browse.MediaBrowser;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contains a List of Problems, with methods that modify or return problems
 * @author Jeremy Xie
 */
public class CommentList implements Serializable{
    private ArrayList<Comment> commentList = new ArrayList<Comment>();

    /**
     * Adds a problem to the problem list
     * @param comment The problem to be added
     */
    public void addComment(Comment comment){
        commentList.add(comment);
    }

    /**
     * Gets the problem list
     * @return The list of problems
     */
    public ArrayList<Comment> getList() {return commentList;}

    /**
     * Removes a problem at the given index
     * @param index The int index of the problem to be removed
     */
    public void removeIndex(int index){commentList.remove(index);
    }

    public void removeComment(Comment comment){commentList.remove(comment);
    }

    /**
     * Gets the problem at the given index
     * @param index The index of the problem to be returned
     * @return The problem at a specified index
     */
    public void updateComment(int index, Comment comment){
        commentList.set(index, comment);
    }

    /**
     * Gets the problem at the given index
     * @param index The index of the problem to be returned
     * @return The problem at a specified index
     */
    public Comment getComment(int index){return commentList.get(index);
    }

    /**
     * Gets the index of the given problem
     * @param comment The given problem
     * @return The int index of the problem that matches the given problem
     */
    public int getIndex(Comment comment){
        return commentList.indexOf(comment);
    }

    public Boolean problemExist(Problem problem){
        return commentList.contains(problem);
    }

    /**
     * Gets the size of the problem list
     * @return Int size of the problem list
     */
    public int getSize(){
        return commentList.size();
    }

}
