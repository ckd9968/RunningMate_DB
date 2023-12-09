package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class RegisterEquipmentController {
	private Connection con = null;
	
	public Object[] registerEquipment(String userID,String EqType, String EqBrand, String EqName, int EqPrice) {
		
		DB_Connector.connectToDB("장비 등록하기");
		con = DB_Connector.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into V_장비ID없음 values(?,?,?,?,?)");
			pstmt.setString(1, userID);
			pstmt.setString(2, EqType);
			pstmt.setString(3, EqBrand);
			pstmt.setString(4, EqName);
			pstmt.setInt(5, EqPrice);
			pstmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "장비 등록이 완료되었습니다");
			
			pstmt.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
