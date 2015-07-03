package org.samaed.aae;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class CurrentDiagnostic {
    private static CurrentDiagnostic _instance = null;
    private List<SymptomParcelable> symptoms;
    private List<DiseaseParcelable> diseases;

    private CurrentDiagnostic(){
        this.symptoms = new ArrayList<>();
        this.diseases = new ArrayList<>();
    }

    public static CurrentDiagnostic getInstance(){
        if(_instance == null){
            _instance = new CurrentDiagnostic();
        }
        return _instance;
    }

    public List<SymptomParcelable> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<SymptomParcelable> symptoms) {
        this.symptoms = symptoms;
    }

    public List<DiseaseParcelable> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<DiseaseParcelable> diseases) {
        this.diseases = diseases;
    }
}
