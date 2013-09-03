package com.zealers.biu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zealers.biu.model.Contact;
import com.zealers.biu.util.SaveToContacts;

public class People_received extends Activity {
	TextView name;
	TextView state;
	Button accept;
	Contact peopleReceived;
	Handler handler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_people_received);
		
		handler = new Handler();
		
		peopleReceived = new Contact();
		Log.i("INFO","Are you here?");
		
		name = (TextView) findViewById(R.id.nameReceived);
		state = (TextView) findViewById(R.id.swapState);
		accept = (Button)findViewById(R.id.selectAllButton);

		accept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("INFO","i got the click");
				SaveToContacts saveToContacts = new SaveToContacts(getBaseContext(), peopleReceived);
				saveToContacts.save();
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						state.setText("存储成功");
						
					}
				});
				
				
			}
		});
		
		
		Log.i("Original name", name.getText().toString());
		
		 Intent intent = getIntent();
		 String nameValue = intent.getStringExtra("name");
		 String numValue = intent.getStringExtra("num");
		 peopleReceived.setName(nameValue);
		 peopleReceived.setTelephone(numValue);
		 
		 try{
			 name.setText(nameValue);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 Log.i("INFO",nameValue);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.people_received, menu);
		return true;
	}

}
