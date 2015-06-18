package org.samaed.aae;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Symptom implements Parcelable {
    private static final String DEFAULT_NAME = "Symptom";
    private static final float DEFAULT_VALUE = 0f;
    private String name;
    private float value;

    public Symptom() {
        this(DEFAULT_NAME, DEFAULT_VALUE);
    }

    public Symptom(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public void setUniqueName(String name) {
        this.name = name;
    }

    public String getUniqueName() {
        return this.name;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("Symptom : %s@%f",getUniqueName(),getValue());
    }

    //region Parcelable Implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle b = new Bundle();
        b.putString("name",getUniqueName());
        b.putFloat("value",getValue());
        dest.writeBundle(b);
    }

    public static final Parcelable.Creator<Symptom> CREATOR
            = new Parcelable.Creator<Symptom>() {
        public Symptom createFromParcel(Parcel in) {
            return new Symptom(in);
        }

        public Symptom[] newArray(int size) {
            return new Symptom[size];
        }
    };

    private Symptom(Parcel in) {
        Bundle b = in.readBundle();
        setUniqueName(b.getString("name"));
        setValue(b.getFloat("value"));
    }

    //endregion
}
