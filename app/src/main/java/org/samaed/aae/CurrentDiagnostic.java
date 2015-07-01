package org.samaed.aae;

import java.util.ArrayList;
import java.util.List;

public class CurrentDiagnostic {
    private static CurrentDiagnostic _instance = null;
    private List<Symptom> symptoms;
    private List<Disease> diseases;

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

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }
}
