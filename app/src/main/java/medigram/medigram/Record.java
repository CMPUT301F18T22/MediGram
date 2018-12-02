package medigram.medigram;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
    private CommentList CommentList = new CommentList();;
    private ArrayList<Double> geoLocation = new ArrayList<>();
    private Date dateStarted;
    private ArrayList<Photo> photos = new ArrayList();
    private transient SimpleDateFormat sdf;

    /**
     * these are the diffenent ways to create the record for different uses
     * the blank is used in adding the record others are used during the testing process
     */
    public Record(){}

    public Record(String recordtitle, Date date){
        this.recordTitle = recordtitle;
        //Comments.add(comment);
        this.dateStarted = date;
    }

    public Record(String recordtitle,  Date date, ArrayList<Photo> photos){
        this.recordTitle = recordtitle;
        //addComment(comment);
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
    public CommentList getComments() {
        return CommentList;
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
    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }

    /**
     * set the photo to this record in add and edit records parts
     * @param photos
     */
    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public ArrayList<Bitmap> getBitmaps() {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (Photo p: this.photos){
            bitmaps.add(p.getBitmap());
        }
        return bitmaps;
    }

    /**
     * get the geolocation of the specific rocords
     * @return geoLocation
     */
    public LatLng getGeoLocation(){
        if (geoLocation.size() != 0){
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
        return this.recordTitle + "~ " + this.getDateString() + " \n  "
                + "\n\n\n\n " + this.recordTitle.replaceAll("\\s+","");
    }
}
