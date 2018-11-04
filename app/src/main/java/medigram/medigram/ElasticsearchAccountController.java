package medigram.medigram;

import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;

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
                    .Builder("http://cmput301.softwareprocess.es:8080/cmput301f18t22test")
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
            Search search = new Search.Builder(userID)
                    .addIndex("medigram")
                    .addType("Patients")
                    .build();
            try {
                JestResult result = client.execute(search);
                if (result.isSucceeded()){
                    Patient patientAccount = result.getSourceAsObject(Patient.class);
                    accounts.add(patientAccount);
                }
            }catch(IOException e){
                //TODO Ouput no user found
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
            Search search = new Search.Builder(userID)
                    .addIndex("medigram")
                    .addType("CareProviders")
                    .build();
            try {
                JestResult result = client.execute(search);
                if (result.isSucceeded()){
                    CareProvider careProvider = result.getSourceAsObject(CareProvider.class);
                    accounts.add(careProvider);
                }
            }catch(IOException e){
                //TODO Ouput no user found
            }
            return accounts;
        }
    }

    public static class CreatePatientAccount extends AsyncTask<Patient, Void, Void>{
        @Override
        protected Void doInBackground(Patient...patients){
            setClient();
            Patient patient= patients[0];

            Index index = new Index.Builder(patient)
                    .index("medigram")
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
            setClient();
            CareProvider careProvider = params[0];

            Index index = new Index.Builder(careProvider)
                    .index("medigram")
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

}
