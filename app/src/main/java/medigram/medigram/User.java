package medigram.medigram;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

public abstract class User implements Serializable {
    String emailAddress;
    String phoneNumber;
    String userID;

    /**
     * get email address of a user
     * @return emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * set email address for a user
     * @param emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * get phone number of a user
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * set phone number for a user
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * get user's id
     * @return userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * set userID for a user
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * check user type
     */
    public abstract String checkUserType();

}
