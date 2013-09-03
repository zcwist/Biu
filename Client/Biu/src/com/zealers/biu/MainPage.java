package com.zealers.biu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.zealers.biu.model.Contact;
import com.zealers.biu.model.Location;
import com.zealers.biu.util.PostAndGet;

public class MainPage extends Activity implements SensorEventListener{
	TextView mainName;
	TextView mainSignature;
	TextView mainPhone;
	TextView mainEmail;
	TextView log;
	
	
	private SensorManager mgr;
    private Sensor proximity;
    private static final String TAG = "ProximityActivity";
    private int count = 0;
    
    
//    private ArrayList<Contact> listFromServer= new ArrayList<Contact>();
    private PostAndGet postAndGet = null;

    private Handler handler = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_page);
		
		handler = new Handler();
		
		mainName = (TextView)findViewById(R.id.mainName);
		mainSignature = (TextView)findViewById(R.id.mainSignature);
		mainPhone = (TextView)findViewById(R.id.mainPhone);
		mainEmail = (TextView)findViewById(R.id.mainEmail);
		log = (TextView)findViewById(R.id.log);
		
		SharedPreferences userInfo = getSharedPreferences("biuUserInfo", 0);
		
		mainName.setText(userInfo.getString("name", ""));
		mainPhone.setText(userInfo.getString("phone", ""));
		mainSignature.setText(userInfo.getString("signature", ""));
		mainEmail.setText(userInfo.getString("email", ""));
		
//		setContact();
		postAndGet = new PostAndGet(getLocation(), setContact());
		// Android 所有的传感器的统一接口
        this.mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        // 取得距离传感器
        this.proximity = this.mgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//     
        this.mgr.registerListener(this, this.proximity,
        		SensorManager.SENSOR_DELAY_NORMAL);

        
        
 
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
	
	protected void onStart(){
		super.onStart();  
        Log.i("INFO", "SubActivity====================>onStart");  

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}
	
   @Override
    protected void onResume() {
        super.onResume();
/*        mainName = (TextView)findViewById(R.id.mainName);
		mainSignature = (TextView)findViewById(R.id.mainSignature);
		mainPhone = (TextView)findViewById(R.id.mainPhone);
		mainEmail = (TextView)findViewById(R.id.mainEmail);
		log = (TextView)findViewById(R.id.log);
		
		SharedPreferences userInfo = getSharedPreferences("biuUserInfo", 0);
		
		mainName.setText(userInfo.getString("name", ""));
		mainPhone.setText(userInfo.getString("phone", ""));
		mainSignature.setText(userInfo.getString("signature", ""));
		mainEmail.setText(userInfo.getString("email", ""));
        
        Log.d(TAG, "registerListener...");
        // 一定要在这注册
*/        this.mgr.registerListener(this, this.proximity,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "unregisterListener...");
        // 一定要在这解注册
        this.mgr.unregisterListener(this, this.proximity);
    }
	@Override
    public void onSensorChanged(SensorEvent event) {

		if (count > 0){
	    	this.mgr.unregisterListener(this, this.proximity);
			Log.i("INFO","Sensor working");
	    	log.setText("Sensor working");
	        Thread temp = new Thread(netOperation);
	        temp.start();
	        try {
	        	Log.i("INFO","Sending");
				temp.join(1000);
				count = 0;
				this.mgr.registerListener(this, this.proximity,
		                SensorManager.SENSOR_DELAY_FASTEST);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			count = 1;
		}
    }
    
	//post 信息 get 信息进程
    Runnable netOperation = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			postAndGet.doIt();
	        
	        handler.post(displayLog);
		}
		
	};
	
	//显示调试信息进程
	Runnable displayLog = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
Intent intent = new Intent(getParent(), People_received.class);
			
			if (postAndGet.getListFromServer().size()>0){
				
			
			Log.i("I get",postAndGet.getListFromServer().get(0).getName());
			intent.putExtra("name", postAndGet.getListFromServer().get(0).getName());
			intent.putExtra("num", postAndGet.getListFromServer().get(0).getTelephone());
			
			} else {
				intent.putExtra("name", "");
			}
			startActivity(intent);
			
		}
		
	};


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    
	private Contact setContact(){
		Contact contact = new Contact();
		SharedPreferences userInfo = getSharedPreferences("biuUserInfo", 0);
		contact.setName(userInfo.getString("name", ""));
		contact.setTelephone(userInfo.getString("phone", ""));
		return contact;
	}
	
}