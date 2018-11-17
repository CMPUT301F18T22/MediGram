package medigram.medigram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Record implements Serializable{
    private String recordTitle;
    private ArrayList<Comment> Comments = new ArrayList<>();
    private Date dateStarted;
    private int geoLocation;
    private ArrayList<Photo> photos;

    public Record(String recordtitle, Comment comment, Date date){
        this.recordTitle = recordtitle;
        Comments.add(comment);
        this.dateStarted = date;
    }

    public Record(String recordtitle, Comment comment, Date date, ArrayList<Photo> photos){
        this.recordTitle = recordtitle;
        addComment(comment);
        this.dateStarted = date;
        this.photos = photos;
    }

    public String getRecordTitle() {
        return recordTitle;
    }

    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    public ArrayList<Comment> getComments() {
        return Comments;
    }

    public void addComment(Comment comment) {
        Comments.add(comment);
    }

    public void deleteComment(Comment comment) {
        Comments.remove(comment);
    }

    public Boolean commentExist(Comment comment) {
        return Comments.contains(comment);
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public int getGeoLocation(){
        return geoLocation;
    }

    public void setGeoLocation(int location){
        this.geoLocation = location;
    }
}
