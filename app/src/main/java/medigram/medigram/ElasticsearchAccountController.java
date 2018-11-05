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

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

public class ElasticsearchAccountController {
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
        if(client==null){
            DroidClientConfig config= new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080")
                    .build();

            JestClientFactory factory=new JestClientFactory();
            factory.setDroidClientConfig(config);
            client=(JestDroidClient) factory.getObject();
        }

    }

    public static class GetPatientAccount extends AsyncTask<String, Void, ArrayList<Patient>>{
        @Override
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
            System.out.println(query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t22test")
                    .addType("Patients")
                    .build();
            JestResult result;
            try {
                result = client.execute(search);
                System.out.println(result.getJsonString());
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

    public static class GetCareProviderAccount extends AsyncTask<String, Void, ArrayList<CareProvider>>{
        @Override
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
            System.out.println(query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t22test")
                    .addType("CareProviders")
                    .build();
            JestResult result;
            try {
                result = client.execute(search);
                System.out.println(result.getJsonString());
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

    public static class CreatePatientAccount extends AsyncTask<Patient, Void, Void>{
        @Override
        protected Void doInBackground(Patient...patients){
            //TODO check if account exists first
            setClient();
            Patient patient= patients[0];

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
                //TODO Ouput a no internet connection error
                e.printStackTrace();
            }

            return null;
        }

    }

    public static class CreateCareProviderAccount extends AsyncTask<CareProvider, Void, Void>{
        @Override
        protected Void doInBackground(CareProvider...params){
            //TODO check if account exists first
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
                //TODO Ouput a no internet connection error
                e.printStackTrace();
            }

            return null;
        }

    }

    //TODO add Update, and Delete functionality


}
