package medigram.medigram;

import java.util.ArrayList;
import java.util.Date;

public class Record{
    private String recordTitle;
    private String Comment;
    private Date dateStarted;
    private String bodyLocation;
    private ArrayList<Photo> photos;

    public Record(String recordtitle, String comment, Date date, String bodylocation){
        this.recordTitle = recordtitle;
        this.Comment = comment;
        this.dateStarted = date;
        this.bodyLocation = bodylocation;
    }

    public Record(String recordtitle, String comment, Date date, String bodylocation, ArrayList<Photo> photos){
        this.recordTitle = recordtitle;
        this.Comment = comment;
        this.dateStarted = date;
        this.bodyLocation = bodylocation;
        this.photos = photos;
    }

    public String getRecordTitle() {
        return recordTitle;
    }

    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
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

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
}
