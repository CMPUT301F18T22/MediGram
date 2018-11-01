package medigram.medigram;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient extends User{
    private List<Problem> problemList = new ArrayList<Problem>();
    private Problem problem;

    public void addProblem(String title, String description, Date dateStarted,
    String bodyLocation, Photo photos){
        problem = new Problem(title, description, dateStarted, bodyLocation, photos);
        problemList.add(problem);


    }
    public void removeProblem(String problemTitle){
    }

    public Boolean problemExist(String problemTitle){
        return true;
    }
}
