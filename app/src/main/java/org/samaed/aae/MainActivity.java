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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testSymptoms();

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
        List<SymptomParcelable> symptoms = new ArrayList<>();
        symptoms.add(new SymptomParcelable("Waist to hip ratio", 1f, 2f, 1.5f, 0x9ff79646, "measuring_tape"));
        symptoms.add(new SymptomParcelable("Blood pressure", 60, 89, 70, 0x9f4f81bd, "pressure_reading"));
        symptoms.add(new SymptomParcelable("Body temperature", 97, 98, 102, 0xffc0504d, "thermometer"));
        symptoms.add(new SymptomParcelable("Oxygenation of blood", 95, 100, 95, 0x7f4bacc6, "oxygen"));
        symptoms.add(new SymptomParcelable("Pulse rate", 66, 99, 75, 0x5fc00000, "heart"));
        symptoms.add(new SymptomParcelable("Blood glucose", 5, 8, 6.3f, 0x6f8064a2, "laboratory"));
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
}
