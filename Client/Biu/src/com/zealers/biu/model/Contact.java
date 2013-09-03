package com.zealers.biu.model;

public class Contact {
	private String name;
	private String telephone;
	public Contact(String name, String telephone){
		setName(name);
		setTelephone(telephone);
	}
	public Contact(){
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
