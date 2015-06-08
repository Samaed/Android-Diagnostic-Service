package com.dhia.tp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Ph extends Activity {
	ListView lv;
	private List<String> ad = new ArrayList<String>();	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ph);
		//if(Param.a!=null){
			
		
		
		
		
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ad.clear();
		int val=Integer.parseInt(Param.b);
		tab(Param.a,val);
		//ad.add(Param.a+" "+Param.b);
		 lv = (ListView) findViewById(R.id.listview12);
	 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1,ad);
  
  
          // Assign adapter to ListView
          lv.setAdapter(adapter); 
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ph, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	void tab(String f,int v){
		Table.create();
		List<Integer> values = null;
		
		for (Map.Entry<String, List<Integer>> entry : Table.map.entrySet()) {

			String key = entry.getKey();

			

			System.out.println("Key = " + key);

			System.out.println("Values = " + values + "n");
			if(key.equals(f)){
				values = entry.getValue();
				
			}

			}
		if((values.get(0)>v)||(values.get(1)<v)){
			
			Table.createm();
			
			for (Map.Entry<String, List<String>> entry : Table.mapm.entrySet()) {

				String keym = entry.getKey();
				List<String> valuesm = entry.getValue();
				

				System.out.println("Key = " + keym);

				System.out.println("Values = " + values + "n");
				//ad.add(f+" "+keym);
				for (int i = 0; i < valuesm.size(); ++i) {
					if(valuesm.get(i).equals(f)){
						
						ad.add(f+" "+keym);
						
					}
					
				}
				
				
				
					
				

				}
			
			
			
			
		
			
		}
		
	}
}
