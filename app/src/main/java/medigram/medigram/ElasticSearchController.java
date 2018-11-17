package medigram.medigram;

import android.os.AsyncTask;
import android.util.Log;
import android.util.MalformedJsonException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.action.Action;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.Update;

public class ElasticSearchController {
    /**
     * Created by: Anas Mohamed.
     * </p>
     * A controller class that controls that is responsible for the online behavior
     * Used to access and update the database used for online synchronization.
     * </p>
     * Resources used:</b>
     * Author: Jakaria Rabbi </b>
     * Link: https://github.com/Jakaria08/lonelyTwitter/blob/elasticsearch/app/src/main/java/ca/ualberta/cs/lonelytwitter/ElasticsearchTweetController.java
     *
     * @see Patient
     * @see CareProvider
     */

    private static JestDroidClient client = null;

    private static void setClient(){
        if (client==null){
            DroidClientConfig config = new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080")
                    .build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }

    }

    /**
     * Handles the finding of a Patient account.
     */
    public static class GetPatient extends AsyncTask<String, Void, ArrayList<Patient>>{
        @Override
        /**
         * Finds a Patient account with given userID.
         * @param params userID of the patient
         * @see Patient
         */
        protected ArrayList<Patient> doInBackground(String...params){
            setClient();
            ArrayList<Patient> accounts = new ArrayList<Patient>();
            String userID = params[0];
            String query = "{"+
                    "\"query\":{" +
                    "\"match\":{" +
                    "\"userID\":\""+ userID + "\"" +
                    "}" +
                    "}" +
                    "}";
            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t22test")
                    .addType("Patients")
                    .build();
            JestResult result;
            try {
                result = client.execute(search);
                if (result.isSucceeded()){
                    List<Patient> matched = result.getSourceAsObjectList(Patient.class);
                    accounts.addAll(matched);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return accounts;
        }
    }

    /**
     * Handles the finding of a Patient account.
     */
    public static class GetCareProvider extends AsyncTask<String, Void, ArrayList<CareProvider>>{
        @Override
        /**
         * Finds a Patient account with given userID.
         * @param params userID of the patient
         * @see Patient
         */
        protected ArrayList<CareProvider> doInBackground(String...params){
            setClient();
            ArrayList<CareProvider> accounts = new ArrayList<CareProvider>();
            String userID = params[0];
            String query = "{"+
                    "\"query\":{" +
                    "\"match\":{" +
                    "\"userID\":\""+ userID + "\"" +
                    "}" +
                    "}" +
                    "}";
            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t22test")
                    .addType("CareProviders")
                    .build();
            JestResult result;
            try {
                result = client.execute(search);
                if (result.isSucceeded()){
                    List<CareProvider> matched = result.getSourceAsObjectList(CareProvider.class);
                    accounts.addAll(matched);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return accounts;
        }
    }

    /**
     * Handles the creation a new account for a Patient.
     */
    public static class CreatePatient extends AsyncTask<Patient, Void, Void>{
        /**
         * Creates a new account for a Patient.
         * @param params
         * @see Patient
         */
        @Override
        protected Void doInBackground(Patient...params){
            setClient();
            Patient patient= params[0];

            Index index = new Index.Builder(patient)
                    .index("cmput301f18t22test")
                    .type("Patients")
                    .build();

            try{
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()){
                    patient.setJestID(result.getId());
                }
            }catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }

    }

    /**
     * Handles the creation a new account for a Care Provider.
     */
    public static class CreateCareProvider extends AsyncTask<CareProvider, Void, Void>{
        /**
         * Creates a new account for a Care Provider.
         * @param params
         * @see CareProvider
         */
        @Override
        protected Void doInBackground(CareProvider...params){
            setClient();
            CareProvider careProvider = params[0];

            Index index = new Index.Builder(careProvider)
                    .index("cmput301f18t22test")
                    .type("CareProviders")
                    .build();

            try{
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()){
                    careProvider.setJestID(result.getId());
                }
            }catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }

    }


    /**
     * Handles updating of Care Provider's account.
     */
    public static class UpdateCareProvider extends AsyncTask<CareProvider, Void, Void> {
        /**
         * Updates a given Care Provider's account to match any new changes
         * @param params
         * @see Patient
         */
        @Override
        protected Void doInBackground(CareProvider... params) {
            setClient();
            CareProvider careProvider = params[0];
            try {
                client.execute(new Index.Builder(careProvider)
                        .index("cmput301f18t22test")
                        .type("CareProviders")
                        .id(careProvider.getJestID())
                        .build());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Handles updating of Patient's account.
     */
    public static class UpdatePatient extends AsyncTask<Patient, Void, Patient> {
        /**
         * Updates a given Patients's account to match any new changes
         * @param params
         * @see Patient
         */
        @Override
        protected Patient doInBackground(Patient... params) {
            setClient();
            Patient patient = params[0];
            try {
                DocumentResult result = client.execute(new Index.Builder(patient)
                        .index("cmput301f18t22test")
                        .type("Patients")
                        .id(patient.getJestID())
                        .build());
//                if (result.isSucceeded()) {
//                    patient.setJestID(result.getId());
//                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Handles the deletion of a User's account.
     */
    public static class DeleteUser extends AsyncTask<String, Void, Void> {
        /**
         * Deletes a given User's account from system.
         * Parameters[0]: jestID to be deleted
         * @param params
         */
        @Override
        protected Void doInBackground(String... params) {
            setClient();
            String userID = params[0];
            try {
                String query = "{"+
                        "\"query\":{" +
                        "\"match\":{" +
                        "\"userID\":\""+ userID + "\"" +
                        "}" +
                        "}" +
                        "}";
                client.execute(new DeleteByQuery.Builder(query)
                        .addIndex("cmput301f18t22test")
                        .build());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
