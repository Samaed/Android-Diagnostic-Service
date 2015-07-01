package org.samaed.aae;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);

        testDiseases();

        final GridView gridviewSymptoms = (GridView) findViewById(R.id.list_symptoms2_grid_id);
        gridviewSymptoms.setAdapter(new SymptomsAdapter(this, CurrentDiagnostic.getInstance().getSymptoms(), true));

        final GridView gridviewDiseases = (GridView) findViewById(R.id.list_diseases_grid_id);
        gridviewDiseases.setAdapter(new DiseasesAdapter(this, CurrentDiagnostic.getInstance().getSymptoms(), CurrentDiagnostic.getInstance().getDiseases()));

        TextView textViewEmptyList = (TextView) findViewById(R.id.empty_list2);
        textViewEmptyList.setVisibility(CurrentDiagnostic.getInstance().getSymptoms().isEmpty() ? View.VISIBLE : View.INVISIBLE);
        TextView textViewEmptyList2 = (TextView) findViewById(R.id.empty_list3);
        textViewEmptyList2.setVisibility(CurrentDiagnostic.getInstance().getDiseases().isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }

    private void testDiseases(){
        List<Disease> diseases = new ArrayList<>();
        diseases.add(new Disease("Inflammation"));
        diseases.add(new Disease("Infection"));
        CurrentDiagnostic.getInstance().setDiseases(diseases);
    }

    public void finishActivity(View view){
        finish();
    }
}
