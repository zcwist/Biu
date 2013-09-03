package com.kiwi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kiwi.network.Contact;

public class ContactJDBC {
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private PreparedStatement pstmt = null;
	
	public ContactJDBC(String url, String usr, String pwd) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(url, usr, pwd);
		pstmt = con.prepareStatement("alter table contact AUTO_INCREMENT 1;");
		pstmt.execute();
	}
	
	public void addContact(Contact contact) throws Exception{
		int nextid = getNextId();
		if (nextid < 0) {
			throw new Exception("Can't get next id");
		}
		String name = contact.getName();
		String telephone = contact.getTelephone();
		String location = contact.getLocation();
		String ip = contact.getIp();
		String time = String.valueOf(contact.getTime().getTime());
		
		String expr = "insert into contact (name, telephone, location, ip, time,id) values (?,?,?,?,?,?)";
		pstmt = con.prepareStatement(expr);
		pstmt.setString(1,name);
		pstmt.setString(2,telephone);
		pstmt.setString(3,location);
		pstmt.setString(4,ip);
		pstmt.setString(5,time);
		pstmt.setInt(6, nextid);
		try {
			pstmt.execute();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public int getNextId()	throws Exception {
		int result = -1;
		String sql = "select max(id)+1 from contact";
		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
		while (rs.next()){
			result = rs.getInt(1);
		}
		return result;
	}
	
	public List<Contact> findContacts(Contact contact){
		List<Contact> contactList = new ArrayList<Contact>();
		String sql = "SELECT * FROM WPD.contact" +
				" where (location = " + contact.getLocation() + " and " +
				"time > (" + contact.getTime().getTime() + " - 50000) and " +
				"time < (" + contact.getTime().getTime() + " + 50000) and " +
				"name <> \"" + contact.getName() +"\")" +
				" group by telephone;";
		System.out.println(sql);
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()){
				Contact temp = new Contact();
				temp.setId(rs.getInt(1));
				temp.setName(rs.getString(2));
				temp.setTelephone(rs.getString(3));
				temp.setIp(rs.getString(4));
				contactList.add(temp);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return contactList;
	}
	
	public void close(){
		if (con != null){
			try {
				con.close();
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				con = null;
			}
		}
	}
	
	

}
