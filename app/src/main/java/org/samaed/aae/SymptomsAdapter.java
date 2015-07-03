package org.samaed.aae;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SymptomsAdapter extends BaseAdapter {
    private Activity mContext;
    private List<SymptomParcelable> values;
    private boolean diagnostic;

    public SymptomsAdapter(Activity c, List<SymptomParcelable> values, boolean diagnostic) {
        this.mContext = c;
        this.values = values;
        this.diagnostic = diagnostic;
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
            if(diagnostic){
                symptomView = inflater.inflate(R.layout.symptom_layout2, parent, false);
            } else {
                symptomView = inflater.inflate(R.layout.symptom_layout, parent, false);
            }
            ImageView img = (ImageView) symptomView.findViewById(R.id.symptom_img);
            int resId = mContext.getResources().getIdentifier(getCurrentSymptom(position).getImage(), "drawable", mContext.getPackageName());
            img.setImageResource(resId);
        } else {
            symptomView = convertView;
        }

        if(diagnostic){
            TextView value = (TextView) symptomView.findViewById(R.id.symptom_value_diag);
            TextView name = (TextView) symptomView.findViewById(R.id.symptom_name_diag);
            TextView ok = (TextView) symptomView.findViewById(R.id.symptom_diag_value);

            String valueStr = String.valueOf(getCurrentSymptom(position).getValue()).replaceAll("\\.?0*$", "");
            int digits = valueStr.length();
            value.setTextSize(11f);
            if (digits > 3) {
                value.setTextSize(11f - (digits - 3));
            }
            value.setText(valueStr);
            ok.setText(getCurrentSymptom(position).isValueInRange() ? "OK" : "PB");
            ok.setTextColor(getCurrentSymptom(position).isValueInRange() ? 0xff00b050 : 0xffff0000);
            name.setText(getCurrentSymptom(position).getUniqueName());

            GradientDrawable gradientDrawable = (GradientDrawable) symptomView.getBackground();
            gradientDrawable.setColor(getCurrentSymptom(position).isValueInRange() ? 0xffebf1de : 0xfff2dcdb);
            symptomView.invalidate();
        } else {
            TextView name = (TextView) symptomView.findViewById(R.id.symptom_name);
            TextView value = (TextView) symptomView.findViewById(R.id.symptom_value);

            name.setText(getCurrentSymptom(position).getUniqueName());
            String valueStr = String.valueOf(getCurrentSymptom(position).getValue()).replaceAll("\\.?0*$", "");
            int digits = valueStr.length();
            value.setTextSize(55f);
            if (digits > 3) {
                value.setTextSize(55f - (9 * (digits - 3)));
            }
            value.setText(valueStr);

            GradientDrawable gradientDrawable = (GradientDrawable) symptomView.getBackground();
            gradientDrawable.setColor(getCurrentSymptom(position).getColor());
            symptomView.invalidate();
        }
        return symptomView;
    }

    public Symptom getCurrentSymptom(int position){
        return (Symptom) getItem(position);
    }

}
