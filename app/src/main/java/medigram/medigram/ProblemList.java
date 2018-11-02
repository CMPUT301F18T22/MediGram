package medigram.medigram;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProblemList{
    private List<Problem> problemList = new ArrayList<Problem>();
    private Problem problem;

    public void addProblem(String title, String description, Date dateStarted,
                           String bodyLocation){
        problem = new Problem(title, description, dateStarted, bodyLocation);
        problemList.add(problem);


    }
    public void removeProblem(String problemTitle){
    }

    public Boolean problemExist(String problemTitle){
        return true;
    }
}
