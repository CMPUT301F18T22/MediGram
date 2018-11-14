package medigram.medigram;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Problem extends RecordList implements Serializable{
    private String problemTitle;
    private String description;
    private Date dateStarted;
    private String bodyLocation;
    private RecordList recordList;
    private ArrayList<Photo> bodyLocationPhotos;
    private SimpleDateFormat sdf;

    public Problem(String problemTitle, String description, Date dateStarted, String bodylocation){
        this.problemTitle = problemTitle;
        this.description = description;
        this.dateStarted = dateStarted;
        this.bodyLocation = bodylocation;
    }
    /*
    public Problem(String problemTitle, String description, Date dateStarted, String bodylocation, ArrayList<Photo> photos){
        this.problemTitle = problemTitle;
        this.description = description;
        this.dateStarted = dateStarted;
        this.bodyLocation = bodylocation;
        this.bodyLocationPhotos = photos;
    }
    */

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

    public String getDateString() {
        sdf= new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return sdf.format(dateStarted);
    }

    public Date getDate() {return this.dateStarted;}

    public void setDateStarted(String dateString) {
        sdf= new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        ParsePosition pos = new ParsePosition(0);
        this.dateStarted =  sdf.parse(dateString, pos);
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

    public String toString(){
        return(" Date Started: "+ this.getDateString()
        + "\n Body Location: " + this.getBodyLocation());
    }


}
