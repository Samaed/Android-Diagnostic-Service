package com.dhia.tp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
 
public class Tabac extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
 
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        intent = new Intent().setClass(this, Param.class);
        spec = tabHost.newTabSpec("Parametres").setIndicator("Parametres")
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, Ph.class);
        spec = tabHost.newTabSpec("Diseases").setIndicator("Diseases")
                      .setContent(intent);
        tabHost.addTab(spec);
 
       
 
      
    }
}