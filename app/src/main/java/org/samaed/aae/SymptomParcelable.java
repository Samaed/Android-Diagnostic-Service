package org.samaed.aae;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class SymptomParcelable extends Symptom implements Parcelable {

    public SymptomParcelable() {
        super();
    }

    public SymptomParcelable(Symptom s) {
        super(s);
    }

    public SymptomParcelable(String name, float value) {
        super(name, value);
    }

    public SymptomParcelable(String name, float min, float max, float value, int color, String image) {
        super(name, min, max, value, color, image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle b = new Bundle();
        b.putString("uniquename",getUniqueName());
        b.putFloat("value",getValue());
        dest.writeBundle(b);
    }

    public static final Parcelable.Creator<SymptomParcelable> CREATOR
            = new Parcelable.Creator<SymptomParcelable>() {
        public SymptomParcelable createFromParcel(Parcel in) {
            return new SymptomParcelable(in);
        }

        public SymptomParcelable[] newArray(int size) {
            return new SymptomParcelable[size];
        }
    };

    private SymptomParcelable(Parcel in) {
        Bundle b = in.readBundle();
        setUniqueName(b.getString("uniquename"));
        setValue(b.getFloat("value"));
    }

}
