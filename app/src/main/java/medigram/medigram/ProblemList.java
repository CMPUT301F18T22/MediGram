package medigram.medigram;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProblemList{
    private List<Problem> problemList = new ArrayList<Problem>();
    private Problem problem;

    public void addProblem(Problem problem){
        problemList.add(problem);
    }

    public void removeProblem(Problem problem){
        problemList.remove(problem);
    }

    public int getIndex(Problem problem){
        return problemList.indexOf(problem);
    }

    public int getProblemAtIndex(Problem problem){
        return problemList.indexOf(problem);
    }

    public Boolean problemExist(Problem problem){
        return problemList.contains(problem);
    }

}
