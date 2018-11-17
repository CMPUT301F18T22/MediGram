package medigram.medigram;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

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
    private String recordTitle;
    private ArrayList<Comment> Comments = new ArrayList<>();
    private Date dateStarted;
    private Double geoLocation;
    private ArrayList<Photo> photos;
    private Context mContext;

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

    public Double getGeoLocation(Context mContext){
        LocationManager locationManager;
        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)mContext.getSystemService(context);
        String provider = LocationManager.GPS_PROVIDER;
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION );
        }
        Location location = locationManager.getLastKnownLocation(provider)
        return geoLocation;
    }

    public void setGeoLocation(Double location){
        this.geoLocation = location;
    }
}
