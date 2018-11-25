package medigram.medigram;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

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
    /**
     * get the title of the record
     * @return title of a record
     */
    public String getRecordTitle() {
        return recordTitle;
    }

    /**
     * set the title of the record
     * @param recordTitle
     */
    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    /**
     * get the comments of a record
     * @return Comments
     */
    public ArrayList<Comment> getComments() {
        return Comments;
    }

    /**
     * add a comment to record
     * @param comment
     */
    public void addComment(Comment comment) {
        Comments.add(comment);
    }

    /**
     * delete a comment to record
     * @param comment
     */
    public void deleteComment(Comment comment) {
        Comments.remove(comment);
    }

    /**
     * check if a comment exists in record
     * @param comment
     * @return true if comment exists, false otherwise
     */
    public Boolean commentExist(Comment comment) {
        return Comments.contains(comment);
    }

    /**
     * get the start date
     * @return dateStarted
     */
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

    /**
     * save a start date
     * @param dateStarted
     */
    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    /**
     * load the photos File
     * @return photos
     */
    public File getPhotos() {
        return photos;
    }

    /**
     * set the photo to this record in add and edit records parts
     * @param photos
     */
    public void setPhotos(File photos) {
        this.photos = photos;
    }

    /**
     * get the geolocation of the specific rocords
     * @return geoLocation
     */
    public LatLng getGeoLocation(){
        if (geoLocation != null){
            return new LatLng(geoLocation.get(0), geoLocation.get(1));
        }
        return null;
    }

    /**
     * save the geolocation of record
     * @param Location
     */
    public void setGeoLocation(LatLng Location){
        this.geoLocation.clear();
        this.geoLocation.add(Location.latitude);
        this.geoLocation.add(Location.longitude);
    }

    /**
     * Generates a String that is suitable for the Adapter.
     * @return String
     */
    public String toString(){
        return this.recordTitle + "~ " + this.getDateString() + " \n  ";
    }
}
