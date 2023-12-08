package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableLoader {
	private static Connection con = null;
	
	public static ArrayList<String[]> loadTables(String table_name) {
		ArrayList<String[]> tupleSet = new ArrayList<String[]>();
		DB_Connector.connectToDB(table_name + "테이블 로드");
		con = DB_Connector.getConnection();
		try {
			System.out.println(table_name);
			PreparedStatement pstmt = con.prepareStatement("Select * from "+table_name);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData schema = rs.getMetaData();
			String[] single_tuple;
			while(rs.next()) {
				single_tuple = new String[schema.getColumnCount()];
				for(int i = 1; i <= schema.getColumnCount(); i++) {
					single_tuple[i-1] = (rs.getObject(i).toString());
				}
				tupleSet.add(single_tuple);
			}
			
			pstmt.close();
			rs.close();
			return tupleSet;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB_Connector.closeConnection();
		}
	}
}
