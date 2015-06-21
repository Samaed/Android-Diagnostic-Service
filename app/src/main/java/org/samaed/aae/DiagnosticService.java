package org.samaed.aae;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagnosticService extends Service {
    public final static int MSG_ADD_SYMPTOMS = 1;
    public final static int MSG_REMOVE_SYMPTOMS = 2;
    public final static int MSG_CLEAR_SYMPTOMS = 3;
    public final static int MSG_COMPUTE = 4;
    public final static int MSG_RETRIEVE_SYMPTOMS = 5;
    public final static String MSG_ADD_SYMPTOMS_KEY = "SYMPTOMS";
    public final static String MSG_REMOVE_SYMPTOMS_KEY = "SYMPTOMS";

    private final static String REST_URL_ROOT = "http://10.0.2.2:8080";
    private final static String REST_URL_MODEL = "/aae?data={data}";
    private final static String REST_FULL_URL = REST_URL_ROOT + REST_URL_MODEL;

    private final Map<String, Symptom> mCatalog = new HashMap<>();
    private final Messenger mMessenger = new Messenger(new IncomingHandler(mCatalog));

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("DiagnosticService", "onStartCommand()");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DiagnosticService", "onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("DiagnosticService", "onDestroy()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("DiagnosticService", "onBind()");
        return mMessenger.getBinder();
    }

    private class IncomingHandler extends Handler {

        private Map<String, Symptom> mCatalog;

        IncomingHandler(Map<String, Symptom> catalog) {
            mCatalog = catalog;
        }

        // TODO use bundle and Parcelable to pass data
        @Override
        public void handleMessage(Message msg) {
            Log.i("DiagnosticService","handleMessage()");
            Log.i("DiagnosticService", String.format("Service catalog then : %s", mCatalog.toString()));

            Bundle b;

            switch (msg.what) {
                case MSG_ADD_SYMPTOMS:
                    Log.i("DiagnosticService", "MSG_ADD_SYMPTOMS");

                    b = msg.getData();
                    b.setClassLoader(Symptom.class.getClassLoader());
                    List<SymptomParcelable> symptoms = b.getParcelableArrayList(MSG_ADD_SYMPTOMS_KEY);
                    Log.i("DiagnosticService", String.format("Symptoms received : %s",symptoms.toString()));
                    for (Symptom s : symptoms) {
                        mCatalog.put(s.getUniqueName(), s);
                    }
                    break;
                case MSG_CLEAR_SYMPTOMS:
                    Log.i("DiagnosticService", "MSG_CLEAR_SYMPTOMS");

                    mCatalog.clear();
                    break;
                case MSG_REMOVE_SYMPTOMS:
                    Log.i("DiagnosticService", "MSG_REMOVE_SYMPTOMS");

                    b = msg.getData();
                    List<String> symptomsNames = b.getStringArrayList(MSG_REMOVE_SYMPTOMS_KEY);
                    Log.i("DiagnosticService", String.format("Symptoms received : %s",symptomsNames.toString()));
                    for (String s : symptomsNames) {
                        mCatalog.remove(s);
                    }
                    break;
                case MSG_COMPUTE:
                    Log.i("DiagnosticService", "MSG_COMPUTE");
                    // SEND API REQUEST
                    // Create a new RestTemplate instance
                    Log.d("DiagnosticCatalog",mCatalog.toString());
                    Symptom[] symptomsArray = new Symptom[mCatalog.size()];
                    new ComputeTask().execute(mCatalog.values().toArray(symptomsArray));
                    break;
                case MSG_RETRIEVE_SYMPTOMS:
                    Log.i("DiagnosticService", "MSG_RETRIEVE_SYMPTOMS");
                    // SEND TO CLIENT SYMPTOMS
                    break;
                default:
                    super.handleMessage(msg);
            }
            Log.i("DiagnosticService", String.format("Service catalog now : %s", mCatalog.toString()));
        }
    }

    private class ComputeTask extends AsyncTask<Symptom, Void, List<Symptom>> {

        @Override
        protected List<Symptom> doInBackground(Symptom... params) {
            RestTemplate restTemplate = new RestTemplate();
            Log.d("DiagnosticParam",String.valueOf(params[0]));

            Gson gson = new Gson();
            String symptomsJSON = gson.toJson(params);
            Log.d("DiagnosticJSON",symptomsJSON);
            // Add the String message converter
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            // Make the HTTP GET request, marshaling the response to a String
            Symptom[] results = restTemplate.getForObject(REST_FULL_URL, Symptom[].class, symptomsJSON);
            return Arrays.asList(results);
        }

        @Override
        protected void onPostExecute(List<Symptom> result) {
            // TODO send the data to the UI
            Log.d("DiagnosticService",result.toString());
        }
    }
}
