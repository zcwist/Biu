package com.zealers.biu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MyInfo extends Activity {
	EditText nameText;
	EditText phoneText;
	EditText signatureText;
	EditText emailText;
//	Button commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
		// Show the Up button in the action bar.
		nameText = (EditText) findViewById(R.id.nameValue);
		phoneText = (EditText) findViewById(R.id.phoneValue);
		signatureText = (EditText) findViewById(R.id.signatureValue);
		emailText = (EditText) findViewById(R.id.emailValue);
//		commit = (Button) findViewById(R.id.button1);
//		
//		commit.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(getParent(), MainActivity.class);
//			    startActivity(intent);
//				
//			}
//		});
		
		SharedPreferences userInfo = getSharedPreferences("biuUserInfo", 0);
		
		nameText.setText(userInfo.getString("name", ""));
		phoneText.setText(userInfo.getString("phone", ""));
		signatureText.setText(userInfo.getString("signature", ""));
		emailText.setText(userInfo.getString("email", ""));
		
		
		nameText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				SharedPreferences userInfo = getSharedPreferences("biuUserInfo", 0);
				
				userInfo.edit().putString("name", nameText.getText().toString()).commit();
				Intent intent = new Intent();
                sendBroadcast(intent);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		phoneText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				SharedPreferences userInfo = getSharedPreferences("biuUserInfo", 0);
				userInfo.edit().putString("phone",phoneText.getText().toString()).commit();
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		signatureText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				SharedPreferences userInfo = getSharedPreferences("biuUserInfo", 0);
				userInfo.edit().putString("signature",signatureText.getText().toString()).commit();
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		emailText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				SharedPreferences userInfo = getSharedPreferences("biuUserInfo", 0);
				userInfo.edit().putString("email",emailText.getText().toString()).commit();
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public void sendMessage(View view) {
	    Intent intent = new Intent(this, MainPage.class);
	    startActivity(intent);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}