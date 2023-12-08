package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import entities.Party;

public class PartyRegisterController {
	private Connection con;
	public boolean checkDouble(String userName) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from 파티참여 where 회원ID='"+userName+"'");
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public boolean registerParty(Party newparty) {
		DB_Connector.connectToDB("파티 등록");
		this.con = DB_Connector.getConnection();
		
		try {
			
			if(checkDouble(newparty.getLeader())) {
				return false;
			}
			
			newparty.setPartyID(getLatestPartyID());
//			System.out.println(newparty.getPartyID());
//			System.out.println(newparty.getLeader());
//			System.out.println(newparty.getMeetingPlace());
//			System.out.println(newparty.getPartyName());
//			System.out.println(newparty.getMeetingDate());
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			PreparedStatement pstmt = con.prepareStatement("insert into 파티 values(?,?,?,?,?)");
			pstmt.setString(1, newparty.getPartyID());
			pstmt.setString(2, newparty.getLeader());
			pstmt.setString(3, newparty.getPartyName());
			pstmt.setString(4, newparty.getMeetingPlace());
			pstmt.setTimestamp(5, newparty.getMeetingDate());
		
			// 트리거를 위한 선행 작업
			stmt.executeUpdate("alter table 회원 disable constraint 참여파티_외래키");
			stmt.executeUpdate("alter table 파티참여 disable constraint 참여파티_파티ID");
			pstmt.executeUpdate();
			stmt.executeUpdate("alter table 회원 enable novalidate constraint 참여파티_외래키");
			stmt.executeUpdate("alter table 파티참여 enable novalidate constraint 참여파티_파티ID");
			
			stmt.close();
			pstmt.close();
			con.commit();
			con.setAutoCommit(true);
			return true;
			
		} catch(SQLException e) {
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DB_Connector.closeConnection();
		}
		
		return false;
	}
	
	public boolean removeParty(String userID) {
		DB_Connector.connectToDB("파티탈퇴");
		con = DB_Connector.getConnection();
		
		try {
			PreparedStatement pstmt = con.prepareStatement("select 파티ID from 파티 where 파티장 = ?");
			pstmt.setString(1, userID);
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next()) {
				JOptionPane.showMessageDialog(null, "등록한 파티가 없습니다.", "기능 오류", JOptionPane.ERROR_MESSAGE);
			} else {
				String paryID = rs.getString(1);
				Statement stmt = con.createStatement();
				int r = stmt.executeUpdate("delete from 파티 where 파티ID='"+paryID+"'");
				if(r > 0) {
					return true;
				}
			}
			return false;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DB_Connector.closeConnection();
		}
	}
	
	private String getLatestPartyID() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select 파티ID from(select * from 파티 order by 파티ID) where rownum = 1");
			if(rs.next()) {
				return getNextPartyID(rs.getString(1));
			} else {
				return "PT00001";
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private String getNextPartyID(String party_id) {
		char[] cur = party_id.toCharArray();
		int i = 2;
		while(cur[i] == '0') {
			i++;
		}
		
		char[] next_num = new char[i];
		next_num[0] = 'P'; next_num[1] = 'T';
		for(int k = 2; k < i; k++) {
			next_num[k] = '0';
		}
		
		return new String(next_num) + (Integer.parseInt(party_id.substring(i)) + 1);
	}
}
