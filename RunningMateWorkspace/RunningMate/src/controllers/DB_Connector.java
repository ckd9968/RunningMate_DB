package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connector {
	static Connection con;
	static final String url = "jdbc:oracle:thin:@localhost:32769:orcl";
	static final String id = "rm";
	static final String pass = "rm123";
	
	static {
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void connectToDB(String message){
		try {
			con = DriverManager.getConnection(url, id, pass);
			System.out.println("connected completely : " + message);
		} catch(SQLException e) {
			System.out.println(e.getMessage() + " " + message);
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return DB_Connector.con;
	}
	
	public static void closeConnection() {
		try {
			con.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
