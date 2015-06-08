package com.dhia.tp;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Play extends Activity {
	 Cursor musiccursor;
		private ArrayList<String> songs = new ArrayList<String>();
		private ArrayList<String> add = new ArrayList<String>();
	     String au[];
	     @SuppressLint("HandlerLeak")
	 	class IncomingHandler extends Handler {
	 		@Override
	 		public void handleMessage(Message msg) {
	 			switch (msg.what) {
	 			case Service1.MSG_ALL:
	 			
	 				break;
	 			
	 			default:
	 				super.handleMessage(msg);
	 			}
	 		}
	 	}
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.play);
	       	  

	    }

}
