package com.zealers.biu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.zealers.biu.model.Contact;
import com.zealers.biu.model.Location;
import com.zealers.biu.util.PostAndGet;

public class MyContactList extends Activity {
    private ListView mContactList;
    private Cursor cursor = null;
    private TextView log = null;
    private ImageButton mImageButton = null;
    private Handler handler = null;
    private PostAndGet postAndGet = null;
    private String logInfo;

    /**
     * Called when the activity is first created. Responsible for initializing the UI.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact_list);
        handler = new Handler();

        // Obtain handles to UI objects
        //log = (TextView) findViewById(R.id.logInContactList);
        mContactList = (ListView) findViewById(R.id.contactList);
        
        mContactList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String name = "";
				name = cursor.getString(1);
				String phone = "";
				if (cursor.getInt(2) > 0){ //有电话号码
					String[] selection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
					Cursor phones = getContentResolver().query(  
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,  
                            selection,  
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID  
                                    + " = " + cursor.getInt(0), null, null);
					if (phones.moveToFirst()){
						phone = phones.getString(0);
					}
					phones.close();
				}
				Contact friend = new Contact();
				friend.setName(name);
				friend.setTelephone(phone);
				//log.setText("Selected a contact");
//				postAndGet = new PostAndGet(getLocation(), friend);
//				Thread temp = new Thread(netOperation);
//		        temp.start();
				Intent intent = new Intent(getParent(),Forward_page.class);
				intent.putExtra("friendName", friend.getName());
				intent.putExtra("friendNum", friend.getTelephone());
				Log.i("INFO",friend.getName() + ":" + friend.getTelephone());
				startActivity(intent);
			}
		});
        
//        mImageButton.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.i("INFO","I got you!");
//				
//			}
//        	
//		});

        // Populate the contact list
        populateContactList();
    }

    /**
     * Populate the contact list based on account currently selected in the account spinner.
     */
    @SuppressWarnings("deprecation")
	private void populateContactList() {
        // Build adapter with contact entries
        cursor = getContacts();
        String[] fields = new String[] {
                ContactsContract.Data.DISPLAY_NAME
        };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.contact_entry, cursor,
                fields, new int[] {R.id.contactEntryText});
        mContactList.setAdapter(adapter);
    }

    /**
     * Obtains the contact list for the currently selected account.
     *
     * @return A cursor for for accessing the contact list.
     */
	private Cursor getContacts()
    {
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
        };

    	Cursor cur = getContentResolver().query(  
                ContactsContract.Contacts.CONTENT_URI,  
                projection,  
                null,  
                null,  
                ContactsContract.Contacts.DISPLAY_NAME  
                        + " COLLATE LOCALIZED ASC");  

        return cur;
    }
	private Location getLocation(){
	     //获得地理位置
		Location loc = new Location();
 		TelephonyManager mTelMan = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
 		String operator = mTelMan.getNetworkOperator();
 		loc.setMobileCountryCode(operator.substring(0, 3)) ;//mobile_country_code
 		loc.setMobileNetworkCode(operator.substring(3));//mobile_network_code
 		GsmCellLocation location = (GsmCellLocation) mTelMan.getCellLocation();
 		loc.setCellId(location.getCid());//cell_id
 		loc.setLocationAreaCode(location.getLac());//location_area_code
 		return loc;
		
	}
	
	Runnable netOperation = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
	        postAndGet.doIt();
	        logInfo = postAndGet.getLogText();
	        handler.post(displayLog);	
		}
		
	};
	Runnable displayLog = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//log.setText(logInfo);
		}
		
	};
	protected void onStop() {
        super.onStop();
        cursor.close();
	}
	
}