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
    private Gson gson;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public OfflineBehaviorController(Context context){
        this.sharedPref = context.getSharedPreferences("User", Context.MODE_PRIVATE);
    }

    /**
     * Handles locally saving a Care Provider's account
     * Since this also handles updates, it removes the old account info if it exists.
     * @param account the Care Provider account to be saved
     */
    public void saveCareProvider(CareProvider account){
        try {
            this.editor = this.sharedPref.edit();
            editor.remove(account.getUserID());

            gson = new Gson();
            String json = gson.toJson(account);
            editor.putString("Account", json);
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
    public CareProvider loadCareProvider(){
        try{
            String json = this.sharedPref.getString("Account", "");
            gson = new Gson();

            if (!json.equals("") && gson.fromJson(json, CareProvider.class).checkUserType().equals("CareProvider")) {
                CareProvider loadedUser = gson.fromJson(json, CareProvider.class);
                return loadedUser;
            }else{
                return null;
            }

        }catch (Exception e ){
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * Handles locally saving a Patient's account
     * Since this also handles updates, it removes the old account info if it exists.
     * @param account the Patient account to be saved
     */
    public void savePatient(Patient account){
        try {
            this.editor = this.sharedPref.edit();
            editor.remove(account.getUserID());

            gson = new Gson();
            String json = gson.toJson(account);
            editor.putString("Account", json);
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
    public Patient loadPatient(){
        try{
            String json = this.sharedPref.getString("Account", "");
            gson = new Gson();

            if (!json.equals("") && gson.fromJson(json, Patient.class).checkUserType().equals("Patient")) {
                Patient loadedUser = gson.fromJson(json, Patient.class);
                return loadedUser;
            }else{
                return null;
            }

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
        this.editor = this.sharedPref.edit();

        editor.clear();
        editor.apply();
    }

}
