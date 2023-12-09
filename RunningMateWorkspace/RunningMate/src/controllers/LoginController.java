package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class LoginController {
	Connection con  = null;
	
	public boolean verifyUserID(String ID) {
		DB_Connector.connectToDB("로그인");
		con = DB_Connector.getConnection();
		try {
			ResultSet rs = con.createStatement().executeQuery("select * from 회원 where 회원ID = '"+ID+"'");
			if(!rs.next()) {
				JOptionPane.showMessageDialog(null, "무효한 아이디 입니다. \nusage : MEMXXXXX", "입력 오류", JOptionPane.ERROR_MESSAGE);
				rs.close();
				return false;
			}
			rs.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DB_Connector.closeConnection();
		}
	}
}
