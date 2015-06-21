package org.samaed.aae;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ServiceConnection mServiceConnection;
    private Messenger mMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("ServiceConnection","onServiceConnected()");
                mMessenger = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mMessenger = null;
            }
        };

        Log.d("MainActivity", "Service Connection created");
        Intent intent = new Intent(this, DiagnosticService.class);
        bindService(intent, mServiceConnection, Context.BIND_ADJUST_WITH_ACTIVITY);
        startService(intent);
    }

    public void onButtonClick(View view) {
        Log.d("MainActivity", "onButtonClick()");

        Message m = Message.obtain();
        m.what = DiagnosticService.MSG_ADD_SYMPTOMS;

        Message m2 = Message.obtain();
        m2.what = DiagnosticService.MSG_REMOVE_SYMPTOMS;

        Message m3 = Message.obtain();
        m3.what = DiagnosticService.MSG_CLEAR_SYMPTOMS;

        Message m4 = Message.obtain();
        m4.what = DiagnosticService.MSG_COMPUTE;

        Bundle b = new Bundle();
        ArrayList<SymptomParcelable> list = new ArrayList<>();
        list.add(new SymptomParcelable("tension haute", 12f));
        list.add(new SymptomParcelable("tension basse", 7f));
        b.putParcelableArrayList(DiagnosticService.MSG_ADD_SYMPTOMS_KEY, list);
        m.setData(b);

        Bundle b2 = new Bundle();
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("tension basse");
        b2.putStringArrayList(DiagnosticService.MSG_REMOVE_SYMPTOMS_KEY, list2);
        m2.setData(b2);

        try {
            mMessenger.send(m);
            Log.i("MainActivity", "MSG_ADD_SYMPTOMS sent");
            Log.i("MainActivity", String.format("Symptoms sent : %s", list.toString()));
            mMessenger.send(m2);
            Log.i("MainActivity", "MSG_REMOVE_SYMPTOMS sent");
            Log.i("MainActivity", String.format("Symptoms sent : %s", list2.toString()));
            mMessenger.send(m4);
            Log.i("MainActivity", "MSG_COMPUTE sent");
            mMessenger.send(m3);
            Log.i("MainActivity", "MSG_CLEAR_SYMPTOMS sent");
        } catch (RemoteException ex) {
            Log.e("MainActivity",ex.getLocalizedMessage());
        }
    }
}
