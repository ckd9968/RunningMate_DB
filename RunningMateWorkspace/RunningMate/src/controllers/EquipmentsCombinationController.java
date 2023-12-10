package controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

public class EquipmentsCombinationController {
	ResultSet res = null;
	Connection con = null;
	// Call SP_장비예산맞추기
	public ResultSet executeCombination(int budget, String priority1, String priority2, String priority3) {
		DB_Connector.connectToDB("장비예산 맞추기");
		con = DB_Connector.getConnection();
		try {
			CallableStatement cstmt = con.prepareCall("{Call SP_예산에맞추기(?,?,?,?,?)}");
			cstmt.setInt(1, budget);
			cstmt.setString(2, priority1);
			cstmt.setString(3, priority2);
			cstmt.setString(4, priority3);
			cstmt.registerOutParameter(5, OracleTypes.CURSOR);
			cstmt.executeUpdate();
			res = (ResultSet)cstmt.getObject(5);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
}
