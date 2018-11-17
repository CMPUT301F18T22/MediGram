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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Record is used to record the patients' problem
 * Records can have a title, a description, a date the problem started, a location, and a photo
 * @author Jiaqi Liu, Zeyu liu(peer programming)
 */
public class Record implements Serializable{

    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private LocationManager locationManager;
    private String provider;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String recordTitle;
    private ArrayList<Comment> Comments = new ArrayList<>();
    private ArrayList<Double> geoLocation = new ArrayList<>();
    private String dateStarted;
    private File photos;

    public Record(){}

    public Record(String recordtitle, Comment comment, String date){
        this.recordTitle = recordtitle;
        Comments.add(comment);
        this.dateStarted = date;
    }

    public Record(String recordtitle, Comment comment, String date, File photos){
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

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public File getPhotos() {
        return photos;
    }

    public void setPhotos(File photos) {
        this.photos = photos;
    }

    public void setGeoLocation(ArrayList<Double> Location){
        this.geoLocation = Location;
    }
}
