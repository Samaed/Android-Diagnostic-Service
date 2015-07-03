package org.samaed.aae;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
    public final static int MSG_RESULT = 6;
    public final static String MSG_ADD_SYMPTOMS_KEY = "SYMPTOMS";
    public final static String MSG_REMOVE_SYMPTOMS_KEY = "SYMPTOMS";
    public final static String MSG_RESULT_DISEASES_KEY = "DISEASES";

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

                    ComputeTaskModel model = new ComputeTaskModel();
                    model.client = msg.replyTo;
                    model.symptoms = mCatalog.values().toArray(symptomsArray);

                    new ComputeTask().execute(model);
                    break;
                default:
                    super.handleMessage(msg);
            }
            Log.i("DiagnosticService", String.format("Service catalog now : %s", mCatalog.toString()));
        }
    }

    private class ComputeTaskModel {
        public Symptom[] symptoms;
        public Messenger client;
    }

    private class ComputeTask extends AsyncTask<ComputeTaskModel, Void, List<Disease>> {

        private Messenger client;

        @Override
        protected List<Disease> doInBackground(ComputeTaskModel... params) {
            client = params[0].client;

            RestTemplate restTemplate = new RestTemplate();
            ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setReadTimeout(1000*5);
            ((SimpleClientHttpRequestFactory)restTemplate.getRequestFactory()).setConnectTimeout(1000*5);

            Gson gson = new Gson();
            String symptomsJSON = gson.toJson(params[0].symptoms);
            Log.d("DiagnosticJSON", symptomsJSON);
            // Add the String message converter
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            // Make the HTTP GET request, marshaling the response to a String

            try {
                Disease[] results = restTemplate.getForObject(REST_FULL_URL, Disease[].class, symptomsJSON);
                return Arrays.asList(results);
            } catch (Exception ex) {
                Log.d("DiagnosticService","Error in server communication, backup to test values");
                return getTestValue();
            }
        }

        private List<Disease> getTestValue() {
            List<Disease> diseases = new ArrayList<>(2);
            diseases.add(new DiseaseParcelable("Inflammation"));
            diseases.add(new DiseaseParcelable("Infection"));
            return diseases;
        }

        @Override
        protected void onPostExecute(List<Disease> result) {
            Log.d("DiagnosticReceived",result.toString());

            ArrayList<DiseaseParcelable> diseases = new ArrayList<>(result.size());
            for(Disease disease : result) {
                diseases.add(new DiseaseParcelable(disease));
            }

            Message m = Message.obtain();
            m.what = MSG_RESULT;

            Bundle b = new Bundle();
            b.putParcelableArrayList(MSG_RESULT_DISEASES_KEY, diseases);
            m.setData(b);

            try {
                client.send(m);
            } catch (RemoteException ex) {
                Log.d("DiagnosticService", "Cannot send the result to the activity");
            }
        }
    }
}
