package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connector {
	static Connection con;
	static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static final String id = "rmadmin";
	static final String pass = "rm123";
	
	static {
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void connectToDB() throws SQLException{
		con = DriverManager.getConnection(url, id, pass);
	}
	
	public static Connection getConnection() {
		return DB_Connector.con;
	}
	
	public static void closeConnection() throws SQLException{
		con.close();
	}
}
