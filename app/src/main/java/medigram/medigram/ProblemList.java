package medigram.medigram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProblemList implements Serializable {
    private List<Problem> problemList = new ArrayList<Problem>();

    public void addProblem(Problem problem){
        problemList.add(problem);
    }

    public void removeProblem(Problem problem){
        problemList.remove(problem);
    }

    public int getIndex(Problem problem){
        return problemList.indexOf(problem);
    }

    public Boolean problemExist(Problem problem){
        return problemList.contains(problem);
    }

    public int getSize(){
        return problemList.size();
    }

}
