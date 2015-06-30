package org.samaed.aae;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SymptomsAdapter extends BaseAdapter {
    private Activity mContext;
    private List<Symptom> values;

    public SymptomsAdapter(Activity c, List<Symptom> values) {
        this.mContext = c;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public Object getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View symptomView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            LayoutInflater inflater = mContext.getLayoutInflater();
            symptomView = inflater.inflate(R.layout.symptom_layout, parent, false);
            //ImageView img = (ImageView) symptomView.findViewById(R.id.symptom_img);
            //int resId = mContext.getResources().getIdentifier(getCurrentSymptom(position).getImage(), "drawable", mContext.getPackageName());
            //img.setImageResource(resId);
        } else {
            symptomView = convertView;
        }

        TextView name = (TextView) symptomView.findViewById(R.id.symptom_name);
        TextView value = (TextView) symptomView.findViewById(R.id.symptom_value);

        name.setText(getCurrentSymptom(position).getUniqueName());
        String valueStr = String.valueOf(getCurrentSymptom(position).getValue()).replaceAll("\\.?0*$", "");
        int digits = valueStr.length();
        value.setTextSize(65f);
        if(digits > 3){
            value.setTextSize(65f-(10*(digits-3)));
        }
        value.setText(valueStr);

        GradientDrawable gradientDrawable = (GradientDrawable) symptomView.getBackground();
        gradientDrawable.setColor(getCurrentSymptom(position).getColor());
        symptomView.invalidate();
        return symptomView;
    }

    public Symptom getCurrentSymptom(int position){
        return (Symptom) getItem(position);
    }

}
