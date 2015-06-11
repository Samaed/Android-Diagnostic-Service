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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


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
        m.what = DiagnosticService.MSG_ADD_SYMPTOM;

        try {
            mMessenger.send(m);
            Log.i("MainActivity", "MSG_ADD_SYMPTOM sent");
        } catch (RemoteException ex) {
            Log.e("MainActivity",ex.getLocalizedMessage());
        }
    }
}
