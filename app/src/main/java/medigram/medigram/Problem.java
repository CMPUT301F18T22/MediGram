package medigram.medigram;

import java.util.ArrayList;
import java.util.Date;

public class Problem {
    private String problemTitle;
    private String description;
    private Date dateStarted;
    private String bodyLocation;
    private RecordList records;
    private Photo photos;

    public Problem(String problemTitle, String description, Date dateStarted,
                   String bodyLocation){
        this.problemTitle = problemTitle;
        this.description = description;
        this.dateStarted = dateStarted;
    }

    public void editDescription(String description){
        this.description = description;
    }
    public void editTitle(String problemTitle){
        this.problemTitle = problemTitle;
    }


}
