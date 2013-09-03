package com.kiwi.db;

public class Config {
	private static String DBUrl = "jdbc:mysql://localhost:3306/WPD";
	private static String DBUsr = "root";
	private static String DBPwd = "112358";
	public static String getDBUrl() {
		return DBUrl;
	}
	public static String getDBUsr() {
		return DBUsr;
	}
	public static String getDBPwd() {
		return DBPwd;
	}
}
