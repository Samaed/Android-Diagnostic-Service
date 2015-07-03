package org.samaed.aae;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class DiseaseParcelable extends Disease implements Parcelable {

    public DiseaseParcelable() {
        super();
    }

    public DiseaseParcelable(Disease d) {
        super(d);
    }

    public DiseaseParcelable(String name) {
        super(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle b = new Bundle();
        b.putString("uniquename", getUniqueName());
        dest.writeBundle(b);
    }

    public static final Parcelable.Creator<DiseaseParcelable> CREATOR
            = new Parcelable.Creator<DiseaseParcelable>() {
        public DiseaseParcelable createFromParcel(Parcel in) {
            return new DiseaseParcelable(in);
        }

        public DiseaseParcelable[] newArray(int size) {
            return new DiseaseParcelable[size];
        }
    };

    private DiseaseParcelable(Parcel in) {
        Bundle b = in.readBundle();
        setUniqueName(b.getString("uniquename"));
    }
}
