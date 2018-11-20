package medigram.medigram;

import android.media.browse.MediaBrowser;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contains a List of Problems, with methods that modify or return problems
 * @author Jarred Mahinay
 */
public class ProblemList implements Serializable{
    private ArrayList<Problem> problemList = new ArrayList<Problem>();

    /**
     * Adds a problem to the problem list
     * @param problem The problem to be added
     */
    public void addProblem(Problem problem){
        problemList.add(problem);
    }

    /**
     * Gets the problem list
     * @return The list of problems
     */
    public ArrayList<Problem> getList() {return problemList;}

    /**
     * Removes a problem at the given index
     * @param index The int index of the problem to be removed
     */
    public void removeIndex(int index){problemList.remove(index);
    }

    public void removeProblem(Problem problem){problemList.remove(problem);
    }

    /**
     * Gets the problem at the given index
     * @param index The index of the problem to be returned
     */
    public void updateProblem(int index, Problem problem){
        problemList.set(index, problem);
    }

    /**
     * Gets the problem at the given index
     * @param index The index of the problem to be returned
     * @return The problem at a specified index
     */
    public Problem getProblem(int index){return problemList.get(index);
    }

    /**
     * Gets the index of the given problem
     * @param problem The given problem
     * @return The int index of the problem that matches the given problem
     */
    public int getIndex(Problem problem){
        return problemList.indexOf(problem);
    }

    public Boolean problemExist(Problem problem){
        return problemList.contains(problem);
    }

    /**
     * Gets the size of the problem list
     * @return Int size of the problem list
     */
    public int getSize(){
        return problemList.size();
    }

}
