package com.zealers.biu.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.zealers.biu.model.Contact;
import com.zealers.biu.model.Location;

public class PostAndGet {
	private String IP = new String("http://166.111.54.243:8080/");
    private String servlet1 = new String("WPD/servlet/Servlet");
    private String getList = new String("WPD/servlet/Servlet?get=list"); 
    private String logText = "";
    private ArrayList<Contact> listFromServer= new ArrayList<Contact>();
    public ArrayList<Contact> getListFromServer() {
		return listFromServer;
	}

	private Location loc;
    private Contact card;
    
    public PostAndGet(Location loc, Contact card) {
		// TODO Auto-generated constructor stub
    	this.loc = loc;
    	this.card = card;
	}
    
    public Contact getCard(){
    	return card;
    }
    
	private String request(String method, String url) {  
		StringBuffer result = new StringBuffer();
        try {  
               if (method.equals("GET")) {  
                   result = doGet(url); 
               }
               else if (method.equals("POST")){
            	   result = doPost(url,card);
               }
            }catch (Exception e) {  
               e.printStackTrace();
        }  
        return result.toString();
	}
	
	@SuppressWarnings("static-access")
	public void doIt(){
		logText = request("POST", IP + servlet1);
        
        //延时2秒
        try {
     	   Thread.currentThread().sleep(5 * 1000);
     	 } 
     	 catch(InterruptedException e) {}
        //logText = (request("GET", IP + getList));
		
	}
	
	private StringBuffer doGet(String url) throws ClientProtocolException, IOException, JSONException{
		HttpResponse httpResponse = null;  
        StringBuffer result = new StringBuffer();
		// 1.通过url创建HttpGet对象  
        HttpGet httpGet = new HttpGet(url);  
        // 2.通过DefaultClient的excute方法执行返回一个HttpResponse对象  
        HttpClient httpClient = new DefaultHttpClient();  
        httpResponse = httpClient.execute(httpGet);  
        // 3.取得相关信息  
        // 取得HttpEntiy  
        HttpEntity httpEntity = httpResponse.getEntity(); 
        String response = EntityUtils.toString(httpEntity, "utf-8");
        Log.i("INFO",response);
        try {   
            JSONArray jsonObjs = new JSONObject(response).getJSONArray("contacts"); 
            for(int i = 0; i < jsonObjs.length() ; i++){   
                JSONObject jsonObj = jsonObjs.getJSONObject(i); 
                Contact contact = new Contact();
                contact.setName(jsonObj.getString("name"));
                contact.setTelephone(jsonObj.getString("telephone"));
                listFromServer.add(contact);
            }   
        } catch (JSONException e) {   
        	result.append("no one is nearby");
        	return result;
        }
        for (Contact contact:listFromServer){
        	result.append(contact.getName() + ":" + contact.getTelephone() + "\r\n");
        }
        
        return result;	
	}
	
	
	
	private StringBuffer doPost(String url, Contact contact) throws ClientProtocolException, IOException{
		HttpPost httpRequest =new HttpPost(url);
		
		StringBuffer result = new StringBuffer();
		
		List <NameValuePair> params=new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("name",contact.getName()));
	    params.add(new BasicNameValuePair("telephone",contact.getTelephone()));
	    params.add(new BasicNameValuePair("location", String.valueOf(loc.getLocationAreaCode())));
	    Log.i("INFO:", contact.getName() + ":" + contact.getTelephone());
	    StringEntity reqEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
	    httpRequest.setEntity(reqEntity);
	    HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
	    String response = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
	    if(httpResponse.getStatusLine().getStatusCode()==200){
	        //取出回应字串
	        //
	    	Log.i("INFO",response);
	        try {   
	            JSONArray jsonObjs = new JSONObject(response).getJSONArray("contacts"); 
	            for(int i = 0; i < jsonObjs.length() ; i++){   
	                JSONObject jsonObj = jsonObjs.getJSONObject(i); 
	                Contact c = new Contact();
	                c.setName(jsonObj.getString("name"));
	                c.setTelephone(jsonObj.getString("telephone"));
	                Log.i("ResponseToJSON",c.getName() + ":" + c.getTelephone());
	                listFromServer.add(c);
	            }   
	        } catch (JSONException e) {   
	        	result.append("no one is nearby");
	        	return result;
	        }
	        for (Contact c:listFromServer){
	        	Log.i("ResponseToJSON",c.getName() + ":" + c.getTelephone());
	        	result.append(c.getName() + ":" + c.getTelephone() + "\r\n");
	        }
	    	
	       }else{
	        //
	       }
        return result;
		
	}

	public String getLogText() {
		return logText;
	}
}
