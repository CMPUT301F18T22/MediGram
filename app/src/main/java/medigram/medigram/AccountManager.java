package medigram.medigram;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Patterns;

import java.util.ArrayList;

/**
 * Handles all account related methods such as login, account creation, updating account info, and lastly account deletion.
 * References: https://stackoverflow.com/questions/6119722/how-to-check-edittexts-text-is-email-address-or-not
 */
public class AccountManager{
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
     * This method is used throughout to decide whether to use locally saving or ElasticSearch online saving.
     * @return True if there is connection, False if there is no connection.
     */
    public boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null){
            return false;
        }else{
            return true;
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
                offlineController.savePatient(patient);
                return null;
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
                offlineController.saveCareProvider(careProvider);
                return null;
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
        ElasticSearchController.GetPatient getPatient = new ElasticSearchController.GetPatient();
        try {
            if (checkConnection()){
                getPatient.execute(userID);
                patientsResults = getPatient.get();
                if (patientsResults.size() != 0) {
                    return patientsResults.get(0);
                }
            }else{
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
        ElasticSearchController.GetCareProvider getCareProvider = new ElasticSearchController.GetCareProvider();
        try {
            if (checkConnection()){
                getCareProvider.execute(userID);
                careProvidersResults = getCareProvider.get();
                if (careProvidersResults.size() != 0) {
                    return careProvidersResults.get(0);
                }
            }else{
                return offlineController.loadCareProvider(userID);
            }
        }catch (Exception e){
            return offlineController.loadCareProvider(userID);
        }
        return null;
    }

    /**
     * Updates a given patient's profile info
     *
     * @param patient
     * @see Patient
     */
    public void patientUpdater(Patient patient){
        if (checkConnection()) {
            ElasticSearchController.UpdatePatient updatePatient = new ElasticSearchController.UpdatePatient();
            updatePatient.execute(patient);
        }else{
            offlineController.savePatient(patient);
        }
    }

    /**
     * Updates a given care provider's profile info
     *
     * @param careProvider
     * @see CareProvider
     */
    public void careProviderUpdater(CareProvider careProvider){
        if (checkConnection()){
            ElasticSearchController.UpdateCareProvider updateCareProvider = new ElasticSearchController.UpdateCareProvider();
            updateCareProvider.execute(careProvider);
        }else{
            offlineController.saveCareProvider(careProvider);
        }
    }

    /**
     * Deletes the account associated with a given jestID
     *
     * @param jestID
     * @see User
     */
    public void accountDeleter(String jestID){
        if (checkConnection()) {
            ElasticSearchController.DeleteUser deleteUser = new ElasticSearchController.DeleteUser();
            deleteUser.execute(jestID);
        }else{
            offlineController.deleteSave();
        }
    }
}
