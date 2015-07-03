package org.samaed.aae;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DiseasesAdapter extends BaseAdapter {
    private Activity mContext;
    private List<DiseaseParcelable> diseases;

    public DiseasesAdapter(Activity c, List<DiseaseParcelable> diseases) {
        this.mContext = c;
        this.diseases = diseases;
    }

    public void updateDiseases(List<DiseaseParcelable> diseases) {
        this.diseases = diseases;
        notifyDataSetChanged();
    }

    public int getCount() {
        return diseases.size();
    }

    public Object getItem(int position) {
        return diseases.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View diseaseView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            LayoutInflater inflater = mContext.getLayoutInflater();
            diseaseView = inflater.inflate(R.layout.disease_layout, parent, false);
        } else {
            diseaseView = convertView;
        }

        TextView value = (TextView) diseaseView.findViewById(R.id.text1);
        value.setText(getCurrentDisease(position).getUniqueName());

        return diseaseView;
    }

    public Disease getCurrentDisease(int position){
        return (Disease) getItem(position);
    }

}
