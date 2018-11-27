package medigram.medigram;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a problem that the user is having.
 * Problems can have a title, a description, a date the problem started, a body location,
 * a list of records, and a list of photos.
 * @author Jarred Mahinay
 */

public class Problem implements Serializable{
    private String problemTitle;
    private String description;
    private Date dateStarted;
    private String bodyLocation;
    private RecordList recordList = new RecordList();
    private ArrayList<Photo> bodyLocationPhotos = new ArrayList<Photo>();
    private transient SimpleDateFormat sdf;

    /**
     *
     * @param problemTitle  The string title of the problem (Not unique)
     * @param description   The string description of the problem
     * @param dateStarted   The date the problem started, in a specified date format
     * @param bodylocation  The string location of the problem
     */
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



    /**
     * Gets the problem title
     * @return returns the problem title string
     */
    public String getProblemTitle() {
        return problemTitle;
    }

    /**
     * Sets the problem title to the given string
     * @param problemTitle The new problem title to be used
     */
    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    /**
     * Gets the description
     * @return returns the description string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the problem description to the given string
     * @param description The new problem description to be used
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the string date in a specified date format
     * @return The date the problem started as a String
     */
    public String getDateString() {
        sdf= new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return sdf.format(dateStarted);
    }

    /**
     * Gets the date the problem started in a date format
     * @return The problem date as a Date
     */
    public Date getDate() {return this.dateStarted;}

    /**
     * Sets the date to the given date string
     * @param dateString The date as a String
     */
    public void setDateStarted(String dateString) {
        sdf= new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        ParsePosition pos = new ParsePosition(0);
        this.dateStarted = sdf.parse(dateString, pos);
    }

    /**
     * Gets the body location string
     * @return The body location string
     */
    public String getBodyLocation() {
        return bodyLocation;
    }

    /**
     * Sets the body location to given string
     * @param bodyLocation The body location string
     */
    public void setBodyLocation(String bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    /**
     * get the record list
     * @return recordList
     */
    public RecordList getRecordList() {
        return recordList;
    }

    /**
     * set the record list
     * @param recordList
     */
    public void setRecordList(RecordList recordList) {
        this.recordList = recordList;
    }

    /**
     * get the list of photos
     * @return bodyLocationPhotos
     */
    public ArrayList<Photo> getBodyLocationPhotos() {
        return bodyLocationPhotos;
    }

    /**
     * set the body location photos
     * @param bodyLocationPhotos
     */
    public void setBodyLocationPhotos(ArrayList<Photo> bodyLocationPhotos) {
        this.bodyLocationPhotos = bodyLocationPhotos;
    }

    public void setBodyLocationPhoto(Photo photo, int index){
        this.bodyLocationPhotos.add(index, photo);
    }

    /**
     * Gets the Date and Body Location as a single string, used in a ListView adapter
     * @return Date and Body Location as a single string
     */
    public String toString(){
        return(this.problemTitle + "~ " + this.getDateString() + " \n  "+ this.getBodyLocation()
        + " \n\n " + this.getBodyLocation().replaceAll("\\s+","")+ " "  + this.problemTitle.replaceAll("\\s+",""));
    }


}
