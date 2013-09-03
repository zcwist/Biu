package com.zealers.biu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.view.Window;
import android.widget.TextView;

import com.zealers.biu.model.Contact;
import com.zealers.biu.model.Location;
import com.zealers.biu.util.PostAndGet;

public class Forward_page extends Activity implements SensorEventListener{
	
	private Contact friend;
	private TextView name;
	private TextView log;
	
	private SensorManager mgr;
    private Sensor proximity;
    private static final String TAG = "ProximityActivity";
    private int count = 0;
    
    private PostAndGet postAndGet = null;

    private Handler handler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forward_page);
		
		handler = new Handler();
		
		
		name = (TextView)findViewById(R.id.nameToSend);
		log = (TextView)findViewById(R.id.log);
		
		friend = new Contact();
		
		Intent intent = getIntent();
//		Log.i("INFO",intent.getStringExtra("friendName"));
		friend.setName(intent.getStringExtra("friendName"));
		friend.setTelephone(intent.getStringExtra("friendNum"));
		Log.i("INFO",friend.getName() + ":" + friend.getTelephone());
		name.setText(friend.getName());
		
		postAndGet = new PostAndGet(getLocation(), friend);
		
		// Android 所有的传感器的统一接口
        this.mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        // 取得距离传感器
        this.proximity = this.mgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//     
        this.mgr.registerListener(this, this.proximity,
        		SensorManager.SENSOR_DELAY_NORMAL);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forward_page, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
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
	
	 @Override
	    protected void onResume() {
	        super.onResume();
	        this.mgr.registerListener(this, this.proximity,
	                SensorManager.SENSOR_DELAY_FASTEST);
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
	        
	        handler.post(displayLog);
		}
		
	};
	
	//显示调试信息进程
	Runnable displayLog = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
//			log.setText(postAndGet.getLogText());
			//Intent intent = new Intent(getParent(), Send_friend.class);
			
			if (postAndGet.getListFromServer().size()>0){
				
			
			Log.i("I get",postAndGet.getListFromServer().get(0).getName());
//			intent.putExtra("name", postAndGet.getListFromServer().get(0).getName());
//			
//			} else {
//				intent.putExtra("name", "");
//			}
//			startActivity(intent);
		}
		}
	};
	
	@Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "unregisterListener...");
        // 一定要在这解注册
        this.mgr.unregisterListener(this, this.proximity);
    }

}
