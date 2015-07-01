package org.samaed.aae;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ServiceConnection mServiceConnection;
    private Messenger mMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testSymptoms();

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


        final GridView gridview = (GridView) findViewById(R.id.list_symptoms_grid_id);
        gridview.setAdapter(new SymptomsAdapter(this, CurrentDiagnostic.getInstance().getSymptoms(), false));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                alertDialog(((SymptomsAdapter) gridview.getAdapter()).getCurrentSymptom(position), gridview);
            }
        });

        TextView textViewEmptyList = (TextView) findViewById(R.id.empty_list);
        textViewEmptyList.setVisibility(CurrentDiagnostic.getInstance().getSymptoms().isEmpty() ? View.VISIBLE : View.INVISIBLE);

    }

    private void testSymptoms(){
        List<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom("Waist to hip ratio", 1f, 2f, 1.5f, 0x9ff79646, "measuring_tape"));
        symptoms.add(new Symptom("Blood pressure", 60, 89, 70, 0x9f4f81bd, "pressure_reading"));
        symptoms.add(new Symptom("Body temperature", 97, 98, 96, 0xffc0504d, "thermometer"));
        symptoms.add(new Symptom("Oxygenation of blood", 95, 100, 95, 0x7f4bacc6, "oxygen"));
        symptoms.add(new Symptom("Pulse rate", 66, 99, 75, 0x5fc00000, "heart"));
        symptoms.add(new Symptom("Blood glucose", 5, 8, 6.3f, 0x6f8064a2, "laboratory"));
        CurrentDiagnostic.getInstance().setSymptoms(symptoms);
    }

    public void alertDialog(final Symptom symptom, final GridView gridView){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Set value");
        alert.setMessage(symptom.getUniqueName());

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setText(String.valueOf(symptom.getValue()));
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                symptom.setValue(Float.valueOf(value));
                gridView.invalidateViews();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    public void onClickTest(View view){
        Intent intent = new Intent(this, DiagnosticActivity.class);
        startActivity(intent);
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
