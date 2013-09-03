package com.zealers.biu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class Send_friend extends Activity {
	TextView name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_people_received);
		name = (TextView) findViewById(R.id.nameReceived);
		Log.i("Original name", name.getText().toString());
		
		 Intent intent = getIntent();
		 String message = intent.getStringExtra("name");
		 try{
			 name.setText(message);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 Log.i("INFO",message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.people_received, menu);
		return true;
	}

}
