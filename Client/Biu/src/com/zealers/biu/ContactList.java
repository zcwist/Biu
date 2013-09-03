package com.zealers.biu;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactList extends ListActivity {
	Context mContext = null;

    /**获取库Phon表字段**/
    private static final String[] PHONES_PROJECTION = new String[] {
	    Phone.DISPLAY_NAME, Phone.NUMBER, Phone.CONTACT_ID };
   
    /**联系人显示名称**/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    
    /**电话号码**/
    private static final int PHONES_NUMBER_INDEX = 1;
    
    /**联系人的ID**/
    private static final int PHONES_CONTACT_ID_INDEX = 2;
    

    /**联系人名称**/
    private ArrayList<String> mContactsName = new ArrayList<String>();
    
    /**联系人头像**/
    private ArrayList<String> mContactsNumber = new ArrayList<String>();

    ListView mListView = null;
    MyListAdapter myAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mContext = this;
		mListView = this.getListView();
		/**得到手机通讯录联系人信息**/
		getPhoneContacts();

		myAdapter = new MyListAdapter(this);

		setListAdapter(myAdapter);


		mListView.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> adapterView, View view,
			    int position, long id) {
			//调用系统方法拨打电话
//			Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri
//				.parse("tel:" + mContactsNumber.get(position)));
//			startActivity(dialIntent);
		    	
		    	
		    }
		});

		super.onCreate(savedInstanceState);
	}

	private void getPhoneContacts() {
		// TODO Auto-generated method stub
		ContentResolver resolver = mContext.getContentResolver();

		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);


		if (phoneCursor != null) {
		    while (phoneCursor.moveToNext()) {

			//得到手机号码
			String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
			//当手机号码为空的或者为空字段 跳过当前循环
			if (TextUtils.isEmpty(phoneNumber))
			    continue;
			
			//得到联系人名称
			String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
			
			//得到联系人ID
			Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

			mContactsName.add(contactName);
			mContactsNumber.add(phoneNumber);
		    }
		    

		    phoneCursor.close();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}
	
	class MyListAdapter extends BaseAdapter {
		public MyListAdapter(Context context) {
		    mContext = context;
		}

		public int getCount() {
		    //设置绘制数量
		    return mContactsName.size();
		}

		@Override
		public boolean areAllItemsEnabled() {
		    return false;
		}

		public Object getItem(int position) {
		    return position;
		}

		public long getItemId(int position) {
		    return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Log.i("No.",String.valueOf(position));
		    TextView title = null;
		    TextView text = null;
		    if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
				R.layout.activity_contact_list, null);
			title = (TextView) convertView.findViewById(R.id.name);
			text = (TextView) convertView.findViewById(R.id.phoneNum);
		    }
		    //绘制联系人名称
		    try{
		    title.setText(mContactsName.get(position) + ":");
		    //绘制联系人号码
		    text.setText(mContactsNumber.get(position));
		    }
		    catch(Exception e){
//		    	e.printStackTrace();
		    	
		    }
		    return convertView;
		}

	    }

}
