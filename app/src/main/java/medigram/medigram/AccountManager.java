package medigram.medigram;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.Patterns;

import java.util.ArrayList;

/**
 * Handles all account related methods such as login, account creation, updating account info, and lastly account deletion.
 * References:
 * https://stackoverflow.com/questions/6119722/how-to-check-edittexts-text-is-email-address-or-not
 * https://stackoverflow.com/questions/44773940/check-internet-connection-android
 */
public class AccountManager{
    private static String syncOperation = "none";
    private static Patient patientToSync;
    private static CareProvider providerToSync;
    private static String userIDSync;
    private ArrayList<Patient> patientsResults;
    private ArrayList<CareProvider> careProvidersResults;
    private OfflineBehaviorController offlineController;
    private Context context;

    /**
     * Initializes the AccountManager with the ApplicationContext required for saving sharedPreferences
     * @param context context of the application.
     */
    public AccountManager(Context context){
        this.context = context;
        offlineController = new OfflineBehaviorController(context);
    }

    /**
     * Handles checking if there an active Internet connection.
     * Also sets the need for online syncing, and calls onlineSync() when connection is back.
     * This method is used throughout to decide whether to use locally saving or ElasticSearch online saving.
     * @return True if there is connection, False if there is no connection.
     */
    public boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            onlineSync();
            return true;
        }else{
            return false;
        }
    }

    /**
     * Handles the creation of a new patient's account
     *
     * @param patient the Patient account to be created.
     * @return true if restrictions (userID length, etc.) pass, else returns false
     * @see Patient
     */
    public String addPatient(Patient patient){
        if (findPatient(patient.getUserID()) != null){
            return "UserID already taken. Please try another one.";
        }
        else if (patient.getUserID().length() >= 8 && Patterns.EMAIL_ADDRESS.matcher(patient.getEmailAddress()).matches() && patient.getPhoneNumber().length() >= 10){
            if (checkConnection()) {
                ElasticSearchController.CreatePatient createAccount = new ElasticSearchController.CreatePatient();
                createAccount.execute(patient);
                offlineController.savePatient(patient);
                return null;
            }else{
                return "Internet connection not available. Try again later.";
            }
        }
        else{
            if (patient.getUserID().length() < 8){
                return "UserID too short (minimum 8 characters).";
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(patient.getEmailAddress()).matches()){
                return "Email address not valid. Please enter a valid email.";
            }
            else if (patient.getPhoneNumber().length() < 10){
                return "Phone number not valid. Please enter a valid phone number.";
            }
        }
        return "Oops, something went wrong. Please try again.";
    }

    /**
     * Handles the creation of a new care provider's account
     *
     * @param careProvider the CareProvider account that is to be created.
     * @return String that is the error to be displayed if account is not valid. Else null to indicate lack of error.
     * @see CareProvider
     */
    public String addCareProvider(CareProvider careProvider){
        if (findCareProvider(careProvider.getUserID()) != null){
            return "UserID already taken. Please try another one.";
        }
        else if (careProvider.getUserID().length() >= 8 && Patterns.EMAIL_ADDRESS.matcher(careProvider.getEmailAddress()).matches() && careProvider.getPhoneNumber().length() >= 10){
            if (checkConnection()) {
                ElasticSearchController.CreateCareProvider createAccount = new ElasticSearchController.CreateCareProvider();
                createAccount.execute(careProvider);
                offlineController.saveCareProvider(careProvider);
                return null;
            }else{
                return "Internet connection not available. Try again later.";
            }
        }
        else{
            if (careProvider.getUserID().length() < 8){
                return "UserID too short (minimum 8 characters).";
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(careProvider.getEmailAddress()).matches()){
                return "Email address not valid. Please enter a valid email.";
            }
            else if (careProvider.getPhoneNumber().length() < 10){
                return "Phone number not valid. Please enter a valid phone number.";
            }
        }
        return "Oops, something went wrong. Please try again.";
    }

    /**
     * Finds a patient with a given userID
     *
     * @param userID
     * @return Patient if found, else null
     * @see Patient
     */
    public Patient findPatient(String userID){
        try {
            if (checkConnection()) {
                if (syncOperation.equals("none")) {
                    ElasticSearchController.GetPatient getPatient = new ElasticSearchController.GetPatient();
                    getPatient.execute(userID);
                    patientsResults = getPatient.get();
                    if (patientsResults.size() != 0) {
                        return patientsResults.get(patientsResults.size() - 1);
                    }
                }
                else{
                    onlineSync();
                    ElasticSearchController.UpdatePatient updatePatient= new ElasticSearchController.UpdatePatient();
                    patientsResults = updatePatient.get();
                    if (patientsResults.size() != 0) {
                        return patientsResults.get(patientsResults.size() - 1);
                    }
                }
            }
            else {
                return offlineController.loadPatient(userID);
            }
        }catch (Exception e){
            return offlineController.loadPatient(userID);
        }
        return null;
    }

    /**
     * Finds a care provider with a given userID
     *
     * @param userID
     * @return CareProvider if found, else null
     * @see CareProvider
     */
    public CareProvider findCareProvider(String userID){
        try {
            if (checkConnection()) {
                if (syncOperation.equals("none")) {
                    ElasticSearchController.GetCareProvider getCareProvider = new ElasticSearchController.GetCareProvider();
                    getCareProvider.execute(userID);
                    careProvidersResults = getCareProvider.get();
                    if (careProvidersResults.size() != 0) {
                        return careProvidersResults.get(careProvidersResults.size() - 1);
                    }
                }
                else{
                    onlineSync();
                    ElasticSearchController.UpdateCareProvider updateCareProvider= new ElasticSearchController.UpdateCareProvider();
                    careProvidersResults = updateCareProvider.get();
                    if (careProvidersResults.size() != 0) {
                        return careProvidersResults.get(careProvidersResults.size() - 1);
                    }
                }
            }else {
                return offlineController.loadCareProvider(userID);
            }
        }catch (Exception e){
            return offlineController.loadCareProvider(userID);
        }
        return null;
    }

    /**
     * Updates a given patient's profile info
     * Checks is the updated info are valid first.
     * @param patient
     * @see Patient
     */
    public String patientUpdater(String oldUserID, Patient patient) {
        if (!patient.getUserID().equals(oldUserID) && findPatient(patient.getUserID()) != null) {
            return "UserID already taken. Please try another one.";
        } else if (patient.getUserID().length() >= 8 && Patterns.EMAIL_ADDRESS.matcher(patient.getEmailAddress()).matches() && patient.getPhoneNumber().length() >= 10) {
            if (checkConnection()) {
                ElasticSearchController.UpdatePatient updatePatient = new ElasticSearchController.UpdatePatient();
                updatePatient.execute(patient);
                offlineController.savePatient(patient);
                return null;
            } else {
                patientToSync = patient;
                syncOperation = "update";
                offlineController.savePatient(patient);
                return null;
            }
        }else{
            if (patient.getUserID().length() < 8){
                return "UserID too short (minimum 8 characters).";
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(patient.getEmailAddress()).matches()){
                return "Email address not valid. Please enter a valid email.";
            }
            else if (patient.getPhoneNumber().length() < 10){
                return "Phone number not valid. Please enter a valid phone number.";
            }
        }
        return "Oops, something went wrong. Please try again.";
    }

    /**
     * Updates a given care provider's profile info
     * Checks is the updated info are valid first.
     * @param careProvider
     * @see CareProvider
     */
    public String careProviderUpdater(String oldUserID, CareProvider careProvider){
        if (!careProvider.getUserID().equals(oldUserID) && findPatient(careProvider.getUserID()) != null) {
            return "UserID already taken. Please try another one.";
        } else if (careProvider.getUserID().length() >= 8 && Patterns.EMAIL_ADDRESS.matcher(careProvider.getEmailAddress()).matches() && careProvider.getPhoneNumber().length() >= 10) {
            if (checkConnection()) {
                ElasticSearchController.UpdateCareProvider updateCareProvider = new ElasticSearchController.UpdateCareProvider();
                updateCareProvider.execute(careProvider);
                offlineController.saveCareProvider(careProvider);
                return null;
            } else {
                providerToSync = careProvider;
                syncOperation = "update";
                offlineController.saveCareProvider(careProvider);
                return null;
            }
        }else{
            if (careProvider.getUserID().length() < 8){
                return "UserID too short (minimum 8 characters).";
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(careProvider.getEmailAddress()).matches()){
                return "Email address not valid. Please enter a valid email.";
            }
            else if (careProvider.getPhoneNumber().length() < 10){
                return "Phone number not valid. Please enter a valid phone number.";
            }
        }
        return "Oops, something went wrong. Please try again.";
    }

    /**
     * Deletes the account associated with a given jestID
     *
     * @param userID the userID for the account to be deleted.
     * @see User
     */
    public void accountDeleter(String userID){
        if (checkConnection()) {
            ElasticSearchController.DeleteUser deleteUser = new ElasticSearchController.DeleteUser();
            deleteUser.execute(userID);
            offlineController.deleteSave();
        }else{
            userIDSync = userID;
            syncOperation = "delete";
            offlineController.deleteSave();
        }
    }

    /**
     * Handles synchronization operations for when connection is back.
     * Uses class static attributes to keep track of changes across instances.
     * @see ElasticSearchController
     */
    public void onlineSync(){
        switch (syncOperation){
            case "update":
                if (patientToSync != null){
                    ElasticSearchController.UpdatePatient updatePatient = new ElasticSearchController.UpdatePatient();
                    updatePatient.execute(patientToSync);
                }
                if (providerToSync != null){
                    ElasticSearchController.UpdateCareProvider updateCareProvider = new ElasticSearchController.UpdateCareProvider();
                    updateCareProvider.execute(providerToSync);
                }
                syncOperation = "none";
                break;

            case "delete":

                ElasticSearchController.DeleteUser deleteUser = new ElasticSearchController.DeleteUser();
                deleteUser.execute(userIDSync);

                syncOperation = "none";
                break;
        }
    }
}
