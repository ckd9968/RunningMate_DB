package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class PartyContoller {
	private Connection con = null;
	private String SQL_party_member_join = "select * from 회원 left outer join 파티 on 회원.참여파티 = 파티.파티ID where 회원ID = ?";
	private String SQL_leave_party = "delete from 파티참여 where 파티ID = ? and 회원ID = ?";
	private String SQL_participation = "insert into 파티참여 values(?,?)";
	
	public boolean leaveParty(String userID) {
		DB_Connector.connectToDB("파티탈퇴");
		con = DB_Connector.getConnection();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(SQL_party_member_join);
			pstmt.setString(1, userID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String part_party = rs.getString("참여파티");
				System.out.println(part_party);
				if(part_party == null) {
					JOptionPane.showMessageDialog(null, "가입된 파티가 없습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				
				if(rs.getString("회원ID").equals(rs.getString("파티장"))) {
					JOptionPane.showMessageDialog(null, "파티장은 탈퇴할 수 없습니다..", "입력오류", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				
				PreparedStatement pstmt2 = con.prepareStatement(SQL_leave_party);
				pstmt2.setString(1, part_party);
				pstmt2.setString(2, userID);
				pstmt2.executeUpdate();
				pstmt.close();
				pstmt2.close();
				rs.close();
				return true;
			}
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DB_Connector.closeConnection();
		}
	}
	
	public boolean participateInParty(String partyID, String userID) {
		DB_Connector.connectToDB("파티가입");
		this.con = DB_Connector.getConnection();
		try {
			ResultSet rs = con.createStatement().executeQuery("select * from 파티참여 where 회원ID='"+userID+"'");
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "이미 가입한 파티가 있습니다.", "파티 중복", JOptionPane.ERROR_MESSAGE);
				rs.close();
				return false;
			}
			rs.close();
			PreparedStatement pstmt = con.prepareStatement(this.SQL_participation);
			pstmt.setString(1, partyID);
			pstmt.setString(2, userID);
			pstmt.executeUpdate();
			
			pstmt.close();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DB_Connector.closeConnection();
		}
		
	}
}
