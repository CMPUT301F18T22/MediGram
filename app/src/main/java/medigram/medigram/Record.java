package medigram.medigram;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Record implements Serializable{
    private String recordTitle;
    private ArrayList<Comment> Comments = new ArrayList<>();
    private Date dateStarted;
    private String geoLocation;
    private ArrayList<Photo> photos;
    private transient SimpleDateFormat sdf;

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

    public String getDateString() {
        sdf= new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return sdf.format(dateStarted);
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

    public String getGeoLocation(){
        return geoLocation;
    }

    public void setGeoLocation(String location){
        this.geoLocation = location;
    }

    public String toString(){
        return(this.recordTitle + "~ " + this.getDateString() + " \n  "+ this.geoLocation
                + " \n\n " + this.recordTitle.replaceAll("\\s+","")+ " "  + this.geoLocation.replaceAll("\\s+",""));
    }
}
