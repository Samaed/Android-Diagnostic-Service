package org.samaed.aae;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.widget.Toast;

import java.io.Console;
import java.io.FileDescriptor;
import java.util.HashMap;

public class DiagnosticService extends Service {
    public final static int MSG_ADD_SYMPTOM = 1;
    public final static int MSG_REMOVE_SYMPTOM = 2;
    public final static int MSG_CLEAR_SYMPTOMS = 3;
    public final static int MSG_COMPUTE = 4;

    private final HashMap<String, String> mCatalog = new HashMap<>();
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

        private HashMap<String, String> mCatalog;

        IncomingHandler(HashMap<String, String> catalog) {
            mCatalog = catalog;
        }

        // TODO use bundle and Parcelable to pass data
        @Override
        public void handleMessage(Message msg) {
            Log.i("DiagnosticService","handleMessage()");
            Log.i("DiagnosticService",mCatalog.toString());
            switch (msg.what) {
                case MSG_ADD_SYMPTOM:
                    Log.i("DiagnosticService","MSG_ADD_SYMPTOM");
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
