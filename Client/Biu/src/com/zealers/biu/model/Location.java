package com.zealers.biu.model;

public class Location {
	private String mobileCountryCode;
	private String mobileNetworkCode;
	private int cellId;
	private int locationAreaCode;
	public String getMobileCountryCode() {
		return mobileCountryCode;
	}
	public void setMobileCountryCode(String mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}
	public String getMobileNetworkCode() {
		return mobileNetworkCode;
	}
	public void setMobileNetworkCode(String mobileNetworkCode) {
		this.mobileNetworkCode = mobileNetworkCode;
	}
	public int getCellId() {
		return cellId;
	}
	public void setCellId(int cellId) {
		this.cellId = cellId;
	}
	public int getLocationAreaCode() {
		return locationAreaCode;
	}
	public void setLocationAreaCode(int locationAreaCode) {
		this.locationAreaCode = locationAreaCode;
	}
	

}
