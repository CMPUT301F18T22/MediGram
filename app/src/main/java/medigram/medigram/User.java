package medigram.medigram;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

import io.searchbox.annotations.JestId;

public abstract class User implements Serializable {
    String emailAddress;
    String phoneNumber;
    String userID;
    String securityCode;

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
     * Generates a code for the user to use to login on new devices.
     * The code is also used by Care Providers to add them to their patient list.
     * @return Code for the user's account
     */
    public String generateCode() {
        if (this.securityCode == null){
            SecureRandom random = new SecureRandom();
            this.securityCode = new BigInteger(30, random).toString(32).toUpperCase();
        }
        return this.securityCode;
    }

    /**
     * check user type
     */
    public abstract String checkUserType();

}
