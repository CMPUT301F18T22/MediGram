package medigram.medigram;

import android.util.Patterns;

import java.util.ArrayList;

/**
 * Handles all account related methods such as login, account creation, updating account info, and lastly account deletion.
 * References: https://stackoverflow.com/questions/6119722/how-to-check-edittexts-text-is-email-address-or-not
 */
public class AccountManager {
    private ArrayList<Patient> patientsResults;
    private ArrayList<CareProvider> careProvidersResults;

    /**
     * Handles the creation of a new patient's account
     *
     * @param patient the Patient account to be created.
     * @return true if restrictions (userID length, etc.) pass, else returns false
     */
    public String addPatient(Patient patient){
        ElasticSearchController.CreatePatient createAccount = new ElasticSearchController.CreatePatient();
        if (patient.getUserID().length() >= 8 && Patterns.EMAIL_ADDRESS.matcher(patient.getEmailAddress()).matches() && patient.getPhoneNumber().length() >= 10){
            createAccount.execute(patient);
            return null;
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
     *
     * @param careProvider the CareProvider account that is to be created.
     * @return String that is the error to be displayed if account is not valid. Else null to indicate lack of error.
     */
    public String addCareProvider(CareProvider careProvider){
        ElasticSearchController.CreateCareProvider createAccount = new ElasticSearchController.CreateCareProvider();
        if (careProvider.getUserID().length() >= 8 && Patterns.EMAIL_ADDRESS.matcher(careProvider.getEmailAddress()).matches() && careProvider.getPhoneNumber().length() >= 10){
            createAccount.execute(careProvider);
            return null;
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
     */
    public Patient findPatient(String userID){
        ElasticSearchController.GetPatient getPatient = new ElasticSearchController.GetPatient();
        try {
            getPatient.execute(userID);
            patientsResults = getPatient.get();
            if (patientsResults.size() != 0){
                return patientsResults.get(0);
            }
        }catch (Exception e){
            //TODO better error handling
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Finds a care provider with a given userID
     *
     * @param userID
     * @return CareProvider if found, else null
     */
    public CareProvider findCareProvider(String userID){
        ElasticSearchController.GetCareProvider getCareProvider = new ElasticSearchController.GetCareProvider();
        try {
            getCareProvider.execute(userID);
            careProvidersResults = getCareProvider.get();
            if (careProvidersResults.size() != 0) {
                return careProvidersResults.get(0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates a given patient's profile info
     *
     * @param patient
     */
    public void patientUpdater(Patient patient){
        ElasticSearchController.UpdatePatient updatePatient = new ElasticSearchController.UpdatePatient();
        updatePatient.execute(patient);
    }

    /**
     * Updates a given care provider's profile info
     *
     * @param careProvider
     */
    public void careProviderUpdater(CareProvider careProvider){
        ElasticSearchController.UpdateCareProvider updateCareProvider = new ElasticSearchController.UpdateCareProvider();
        updateCareProvider.execute(careProvider);
    }

    /**
     * Deletes the account associated with a given jestID
     *
     * @param jestID
     */
    public void accountDeleter(String jestID){
        ElasticSearchController.DeleteUser deleteUser = new ElasticSearchController.DeleteUser();
        deleteUser.execute(jestID);
    }
}
