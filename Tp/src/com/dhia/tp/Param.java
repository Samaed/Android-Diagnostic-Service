package com.dhia.tp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Param extends Activity {
	public static String a="blood glucose";
	public static String b="11" ;
	 Spinner spinner2 ;
	 EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_param);
		et =(EditText) findViewById(R.id.editText1);
	  spinner2 = (Spinner) findViewById(R.id.spinner1);
		List<String> list = new ArrayList<String>();
		list.add("waist hip ratio");
		list.add("body temperature ");
		list.add("oxygenation of blood");
		list.add("blood pressure");
		list.add("blood glucose");
		list.add("pulse rate");
	
		
		
		


		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
		
		
		Button btnSubmit = (Button) findViewById(R.id.button1);
		 
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				a= String.valueOf(spinner2.getSelectedItem()) ;
				b=et.getText().toString();
				
				
			}
			
		});
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		et.setText(getMyNumber());
		
		
		
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.param, menu);
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
	
	
	public int getMyNumber()
	{

	int min = 30;


	int max = 42;




	Random r = new Random();


	int i = r.nextInt(max - min + 1) + min;

	

return i;

	}

}

