package medigram.medigram;

import android.media.browse.MediaBrowser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProblemList{
    private List<Problem> problemList = new ArrayList<Problem>();

    public void addProblem(Problem problem){
        problemList.add(problem);
    }

    public List<Problem> getList() {return problemList;}

    public void removeProblem(int index){problemList.remove(index);
    }

    public Problem getProblem(Integer index){return problemList.get(index);
    }

    public Integer getIndex(Problem problem){
        return problemList.indexOf(problem);
    }

    public Boolean problemExist(Problem problem){
        return problemList.contains(problem);
    }

    public int getSize(){
        return problemList.size();
    }

}
