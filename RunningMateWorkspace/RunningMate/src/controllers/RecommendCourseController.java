package controllers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;


public class RecommendCourseController {
	private Connection con = null;
	
	public ResultSet  searchCourse(String selectedAddress, String selectedAtmosphere, int visitOver, int visitUnder) {
		DB_Connector.connectToDB("코스추천 조회하기");
		con = DB_Connector.getConnection();
		ResultSet rs = null;
		
        try {
            CallableStatement cstmt = con.prepareCall("{call SP_추천코스검색(?,?,?,?,?)}");
            cstmt.setString(1, selectedAddress);
            cstmt.setString(2, selectedAtmosphere);
            cstmt.setInt(3, visitOver);
            cstmt.setInt(4, visitUnder);
            cstmt.registerOutParameter(5, OracleTypes.CURSOR);
            cstmt.execute();
            
            rs = (ResultSet) cstmt.getObject(5);
            
	} catch (SQLException e) {
		e.printStackTrace();
	}
        return rs;
	
}

}
