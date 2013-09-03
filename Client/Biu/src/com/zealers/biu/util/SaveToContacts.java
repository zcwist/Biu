package com.zealers.biu.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.zealers.biu.model.Contact;

public class SaveToContacts extends Activity{
	private Contact contact;
	private Context context;
	
	public SaveToContacts(Context context, Contact contact) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.contact = contact;
	}
	public void save(){
	       //添加联系人  
		ContentResolver resolver = context.getContentResolver();  
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");  
		ContentValues values = new ContentValues();  
		// 向raw_contacts插入一条除了ID之外, 其他全部为NULL的记录, ID是自动生成的  
		long id = ContentUris.parseId(resolver.insert(uri, values));   
		//添加联系人姓名  
		uri = Uri.parse("content://com.android.contacts/data");  
		values.put("raw_contact_id", id);  
		values.put("data2", contact.getName());  
		values.put("mimetype", "vnd.android.cursor.item/name");  
		resolver.insert(uri, values);  
		                //添加联系人电话  
		values.clear(); // 清空上次的数据  
		values.put("raw_contact_id", id);  
		values.put("data1", contact.getTelephone());  
		values.put("data2", "2");  
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");  
		resolver.insert(uri, values);  
//		//添加联系人邮箱  
//		values.clear();  
//		values.put("raw_contact_id", id);  
//		values.put("data1", "zxx@itcast.cn");  
//		values.put("data2", "1");  
//		values.put("mimetype", "vnd.android.cursor.item/email_v2");  
//		resolver.insert(uri, values);  

		 

	}
}

