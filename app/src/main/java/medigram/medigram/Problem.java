package medigram.medigram;

import java.util.ArrayList;
import java.util.Date;

public class Problem extends RecordList {
    private String problemTitle;
    private String description;
    private Date dateStarted;
    private String bodyLocation;
    public RecordList recordList;
    private ArrayList<Photo> bodyLocationPhotos;

    public Problem(String problemTitle, String description, Date dateStarted, String bodylocation){
        this.problemTitle = problemTitle;
        this.description = description;
        this.dateStarted = dateStarted;
        this.bodyLocation = bodylocation;
    }
    public Problem(String problemTitle, String description, Date dateStarted, String bodylocation, ArrayList<Photo> photos){
        this.problemTitle = problemTitle;
        this.description = description;
        this.dateStarted = dateStarted;
        this.bodyLocation = bodylocation;
        this.bodyLocationPhotos = photos;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getBodyLocation() {
        return bodyLocation;
    }

    public void setBodyLocation(String bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public RecordList getRecordList() {
        return recordList;
    }

    public void setRecordList(RecordList recordList) {
        this.recordList = recordList;
    }

    public ArrayList<Photo> getBodyLocationPhotos() {
        return bodyLocationPhotos;
    }

    public void setBodyLocationPhotos(ArrayList<Photo> bodyLocationPhotos) {
        this.bodyLocationPhotos = bodyLocationPhotos;
    }


}
