package medigram.medigram;

import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.InstrumentationTestRunner;
import android.test.mock.MockContext;
import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by anas on 11/8/18.
 */

/**
 * Controls the local data flow for when there is no Internet connection for ElasticSearch.
 */
public class OfflineBehaviorController {
    private static final String SAVE_FILE = "save.json";
    private Gson gson;
    public SharedPreferences sharedPref;
    public SharedPreferences.Editor editor;
    private Context context;

    public OfflineBehaviorController(Context context){
        this.context = context;
        this.sharedPref = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        this.editor = this.sharedPref.edit();
    }

    /**
     * Handles locally saving a Care Provider's account
     *
     * @param account the Care Provider account to be saved
     */
    public void saveCareProvider(CareProvider account){
        try {
            editor.clear();

            gson = new Gson();
            String json = gson.toJson(account);
            editor.putString(account.getUserID(), json);
            editor.apply();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Handles retrieving locally saved  Care Provider's account.
     *
     * @return Found local Care Provider account, if none is found returns null
     */
    public CareProvider loadCareProvider(String UserID){
        try{
            String json = this.sharedPref.getString(UserID, "");
            gson = new Gson();
            CareProvider loadedUser = gson.fromJson(json, CareProvider.class);

            return loadedUser;

        }catch (Exception e ){
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * Handles locally saving a Patient's account
     *
     * @param account the Patient account to be saved
     */
    public void savePatient(Patient account){
        try {
            editor.clear();

            gson = new Gson();
            String json = gson.toJson(account);
            editor.putString(account.getUserID(), json);
            editor.apply();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Handles retrieving locally saved  Patient's account.
     *
     * @return Found local Patient account, if none is found returns null
     */
    public Patient loadPatient(String UserID){
        try{
            String json = this.sharedPref.getString(UserID, "");
            gson = new Gson();
            Patient loadedUser = gson.fromJson(json, Patient.class);

            return loadedUser;

        }catch (Exception e ){
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * Deletes the User's account
     *
     */
    public void deleteSave(){
        editor.clear();
        editor.apply();
    }

}
