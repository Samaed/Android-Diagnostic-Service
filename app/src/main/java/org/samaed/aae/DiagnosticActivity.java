package org.samaed.aae;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticActivity extends ActionBarActivity {

    private SymptomsAdapter symptomsAdapter;
    private DiseasesAdapter diseasesAdapter;
    private TextView emptySymptoms;
    private TextView loadingSymptoms;

    private ServiceConnection mServiceConnection;
    private Messenger mMessenger;
    private Messenger replyMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);

        createView();
        startReplyMessenger();
        startService();
    }

    private void createView() {
        final GridView gridviewSymptoms = (GridView) findViewById(R.id.list_symptoms2_grid_id);
        symptomsAdapter = new SymptomsAdapter(this, CurrentDiagnostic.getInstance().getSymptoms(), true);
        gridviewSymptoms.setAdapter(symptomsAdapter);

        final GridView gridviewDiseases = (GridView) findViewById(R.id.list_diseases_grid_id);
        diseasesAdapter = new DiseasesAdapter(this, CurrentDiagnostic.getInstance().getDiseases());
        gridviewDiseases.setAdapter(diseasesAdapter);

        TextView textViewEmptyList = (TextView) findViewById(R.id.empty_list2);
        textViewEmptyList.setVisibility(CurrentDiagnostic.getInstance().getSymptoms().isEmpty() ? View.VISIBLE : View.INVISIBLE);
        emptySymptoms = (TextView) findViewById(R.id.empty_list3);
        emptySymptoms.setVisibility(View.INVISIBLE);
        loadingSymptoms = (TextView) findViewById(R.id.loading_list);
    }

    private void startService() {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("ServiceConnection", "onServiceConnected()");
                mMessenger = new Messenger(service);
                sendMessages();
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

    private void startReplyMessenger() {
        replyMessenger = new Messenger(new HandlerResult(diseasesAdapter, loadingSymptoms, emptySymptoms));
    }

    private void sendMessages() {
        Message symptomsClear = Message.obtain();
        symptomsClear.what = DiagnosticService.MSG_CLEAR_SYMPTOMS;

        try {
            mMessenger.send(symptomsClear);
        } catch (RemoteException ex) {
            Toast.makeText(getApplicationContext(),"Cannot clear previous symptoms",Toast.LENGTH_SHORT).show();
        }

        Message symptomsPush = Message.obtain();
        symptomsPush.what = DiagnosticService.MSG_ADD_SYMPTOMS;

        Bundle b = new Bundle();
        ArrayList<SymptomParcelable> list = new ArrayList<>(CurrentDiagnostic.getInstance().getSymptoms());
        b.putParcelableArrayList(DiagnosticService.MSG_ADD_SYMPTOMS_KEY, list);
        symptomsPush.setData(b);

        try {
            mMessenger.send(symptomsPush);
        } catch (RemoteException ex) {
            Toast.makeText(getApplicationContext(),"Cannot add new symptoms",Toast.LENGTH_SHORT).show();
        }

        Message symptomCompute = Message.obtain();
        symptomCompute.what = DiagnosticService.MSG_COMPUTE;
        symptomCompute.replyTo = replyMessenger;

        try {
            mMessenger.send(symptomCompute);
        } catch (RemoteException ex) {
            Toast.makeText(getApplicationContext(),"Cannot send compute message",Toast.LENGTH_SHORT).show();
        }
    }

    public void finishActivity(View view){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(mServiceConnection);
    }

    private class HandlerResult extends Handler {

        private DiseasesAdapter diseasesAdapter;
        private TextView loading, empty;

        HandlerResult(DiseasesAdapter diseasesAdapter, TextView loading, TextView empty) { this.diseasesAdapter = diseasesAdapter; this.loading = loading; this.empty = empty; }

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);

            Log.d("DiagnosticActivity","Gotcha!");

            Bundle b = message.getData();
            b.setClassLoader(Symptom.class.getClassLoader());
            ArrayList<DiseaseParcelable> diseasesParcelable = b.getParcelableArrayList(DiagnosticService.MSG_RESULT_DISEASES_KEY);
            if (diseasesParcelable != null)
            {
                Log.d("DiagnosticActivity", "The answer is not exactly null");

                loading.setVisibility(View.INVISIBLE);
                empty.setVisibility(diseasesParcelable.size() == 0 ? View.VISIBLE : View.INVISIBLE);

                CurrentDiagnostic.getInstance().setDiseases(diseasesParcelable);
                diseasesAdapter.updateDiseases(diseasesParcelable);

            }
        }
    }
}
