

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kiwi.db.Config;
import com.kiwi.db.ContactJDBC;
import com.kiwi.network.Contact;


@SuppressWarnings("serial")
public class Servlet extends HttpServlet {
	private ContactJDBC contactJDBC = null;
//	private Contact contact = null;

	/**
	 * Constructor of the object.
	 */
	public Servlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
		contactJDBC.close();
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
//	public void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/plain");
//		response.setCharacterEncoding("UTF-8");
//		System.out.println("Got a get request");
//		PrintWriter out = response.getWriter();
//		JSONObject result = new JSONObject();
//		if (request.getParameter("get").equals("list")){
//			List<Contact> contactList = contactJDBC.findContacts(contact);
//			System.out.println("got you! "+contact.getName() + " " + contact.getTelephone() + "@" + contact.getTime().getTime() + "ms");
//			if (contactList.size() == 0){
//				JSONArray contacts = new JSONArray();
//				try {
//					result.put("contacts",contacts);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			} else {
//				JSONArray contacts = new JSONArray();
//				for (Contact contact : contactList){
//					try {
//						JSONObject c = new JSONObject();
//						c.put("id", contact.getId());
//						c.put("name", contact.getName());
//						c.put("telephone", contact.getTelephone());
//						contacts.put(c);
//						
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				try {
//					result.put("contacts",contacts);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println(result.toString());
//				out.println(result.toString());	
//			}
//			System.out.println(result.toString());
//			out.flush();
//			out.close();		
//		}
//	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Contact contact = new Contact();
		request.setCharacterEncoding("UTF-8");
		contact.setTime(new Date());
		System.out.println(contact.getTime().getTime());
		contact.setIp(request.getRemoteAddr());
		contact.setName(request.getParameter("name"));
		contact.setTelephone(request.getParameter("telephone"));
		contact.setLocation(request.getParameter("location"));
		System.out.println(contact.getIp());
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("telephone"));
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		insert2db(contact);
		
		try {
	     	   Thread.sleep(2 * 1000);
	     	 } 
	     	 catch(InterruptedException e) {}
		
		PrintWriter out = response.getWriter();
		JSONObject result = new JSONObject();
		List<Contact> contactList = contactJDBC.findContacts(contact);
		System.out.println("got you! "+contact.getName() + " " + contact.getTelephone() + "@" + contact.getTime().getTime() + "ms");
		if (contactList.size() == 0){
			JSONArray contacts = new JSONArray();
			try {
				result.put("contacts",contacts);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			JSONArray contacts = new JSONArray();
			for (Contact contectInfo: contactList){
				try {
					JSONObject c = new JSONObject();
					c.put("id", contectInfo.getId());
					c.put("name", contectInfo.getName());
					c.put("telephone", contectInfo.getTelephone());
					contacts.put(c);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				result.put("contacts",contacts);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(result.toString());
			out.println(result.toString());	
		}
		System.out.println(result.toString());
		out.flush();
		out.close();
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	public void insert2db(Contact contact){
		try {
			contactJDBC = new ContactJDBC(Config.getDBUrl(), Config.getDBUsr(), Config.getDBPwd());
			contactJDBC.addContact(contact);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
