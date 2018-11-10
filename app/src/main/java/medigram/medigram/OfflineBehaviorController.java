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

    /**
     * Handles locally saving a Care Provider's account
     *
     * @param context required for locally saving SharePreferences
     * @param account the Care Provider account to be saved
     */
    public void saveCareProvider(Context context, CareProvider account){
        try {
            this.sharedPref = context.getSharedPreferences("User", Context.MODE_PRIVATE);
            editor = this.sharedPref.edit();

            gson = new Gson();
            String json = gson.toJson(account);
            Log.d("Info", json);
            editor.putString("account", json);
            editor.apply();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Handles retrieving locally saved  Care Provider's account.
     *
     * @param context required for loading local SharePreferences
     * @return Found local Care Provider account, if none is found returns null
     */
    public CareProvider loadCareProvider(Context context){
        try{
            this.sharedPref = context.getSharedPreferences("User", Context.MODE_PRIVATE);
            String json = this.sharedPref.getString("account", "");
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
     * @param context required for locally saving SharePreferences
     * @param account the Patient account to be saved
     */
    public void savePatient(Context context, Patient account){
        try {
            this.sharedPref = context.getSharedPreferences("User", Context.MODE_PRIVATE);
            editor = this.sharedPref.edit();

            gson = new Gson();
            String json = gson.toJson(account);
            Log.d("Info", json);
            editor.putString("account", json);
            editor.apply();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Handles retrieving locally saved  Patient's account.
     *
     * @param context required for loading local SharePreferences
     * @return Found local Patient account, if none is found returns null
     */
    public Patient loadPatient(Context context){
        try{
            this.sharedPref = context.getSharedPreferences("User", Context.MODE_PRIVATE);
            String json = this.sharedPref.getString("account", "");
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
     * @param context
     */
    public void deleteSave(Context context){
        this.sharedPref = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        editor = this.sharedPref.edit();

        editor.remove("User");
    }

}
