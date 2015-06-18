package org.samaed.aae;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.JsonWriter;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.widget.Toast;

import java.io.Console;
import java.io.FileDescriptor;
import java.util.ArrayList;
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
                    List<Symptom> symptoms = b.getParcelableArrayList(MSG_ADD_SYMPTOMS_KEY);
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
}
