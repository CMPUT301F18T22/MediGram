package medigram.medigram;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Record is used to record the patients' problem
 * Records can have a title, a description, a date the problem started, a location, and a photo
 * @author Jiaqi Liu
 */
public class Record implements Serializable{

    private String recordTitle;
    private ArrayList<Comment> Comments = new ArrayList<>();
    private ArrayList<Double> geoLocation = new ArrayList<>();
    private Date dateStarted;
    private File photos;
    private transient SimpleDateFormat sdf;

    /**
     * these are the diffenent ways to create the record for different uses
     * the blank is used in adding the record others are used during the testing process
     */
    public Record(){}

    public Record(String recordtitle, Comment comment, Date date){
        this.recordTitle = recordtitle;
        Comments.add(comment);
        this.dateStarted = date;
    }

    public Record(String recordtitle, Comment comment, Date date, File photos){
        this.recordTitle = recordtitle;
        addComment(comment);
        this.dateStarted = date;
        this.photos = photos;
    }
    //this function is able to get the title of the record using in edit record title activity
    public String getRecordTitle() {
        return recordTitle;
    }

    //this function is able to set the title of the record using in edit record title activity
    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    //comment in the arraylist no idea what to do
    public ArrayList<Comment> getComments() {
        return Comments;
    }

    //add comment for the both add and edit comment in the record
    public void addComment(Comment comment) {
        Comments.add(comment);
    }

    //delete the comment in the edit comment in the record part
    public void deleteComment(Comment comment) {
        Comments.remove(comment);
    }

    //determine whether it has any comment or not
    public Boolean commentExist(Comment comment) {
        return Comments.contains(comment);
    }

    //getting the started dated in edit part
    public Date getDateStarted() {
        return dateStarted;
    }

    /**
     * Generates a String from the Date.
     * @return String
     */
    public String getDateString() {
        sdf= new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return sdf.format(dateStarted);
    }

    //save the current date into the record
    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    //return the file of photo from the saving file
    public File getPhotos() {
        return photos;
    }

    //set the photo to this record in add and edit records parts
    public void setPhotos(File photos) {
        this.photos = photos;
    }

    //return the geolocation of the specific rocords
    public ArrayList getGeoLocation(){
        return geoLocation;
    }

    //save in the geolocation of the record
    public void setGeoLocation(ArrayList<Double> Location){
        this.geoLocation = Location;
    }

    /**
     * Generates a String that is suitable for the Adapter.
     * @return String
     */
    public String toString(){
        return this.recordTitle + "~ " + this.getDateString() + " \n  "
                + this.geoLocation.toString();
    }
}
