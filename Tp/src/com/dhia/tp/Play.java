package com.dhia.tp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

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
