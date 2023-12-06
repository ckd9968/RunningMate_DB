package controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class RecordingController {
	private Connection con = null;
	
	public Object[] recordSingle(String[] id, String[] arguments) {
		
		return null;
	}
	
	public Object[] recordGroup(String[] ids, String[] arguments) {
		DB_Connector.connectToDB("그룹 기록하기");
		
		boolean hitFlag = true;
		Vector<String> invalidIDs = new Vector<String>();
		Date record_time = new Date(System.currentTimeMillis());
		
		con = DB_Connector.getConnection();
		try {
			con.setAutoCommit(false); // 그룹 기록하기 트랜잭션
			
			PreparedStatement pstmt = con.prepareStatement("insert into 기록 values(?,?,?,?,?)");
			pstmt.setInt(3, Integer.parseInt(arguments[0]));
			pstmt.setDate(4, record_time);
			pstmt.setString(5, arguments[1]);
			
			for(int i = 0; i < ids.length; i++) {
				String recordSerialNumber = getRecordNumberOfID(ids[i]);
				
				if(recordSerialNumber.length() > 0) {
					pstmt.setString(1, recordSerialNumber);
					pstmt.setString(2, ids[i]);
					pstmt.addBatch();
					
				} else {
					hitFlag = false;
					invalidIDs.add(ids[i]);
				}
			}
			
			if(!hitFlag) {
				con.rollback();
				con.setAutoCommit(true);
				return new Object[] {2, false, invalidIDs};
			}
			int[] result = pstmt.executeBatch();
			con.commit();
			return new Object[] {2, true};
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		} finally {
			DB_Connector.closeConnection();
		}
		
		return null;
	}
	
	public Object[] recordParty(String[] partyID, String[] arguments) {
		
		
		return null;
	}
	
	private String getRecordNumberOfID(String id) {
		ResultSet rs;
		try {
			// 기록번호를 반환해야함.
			Statement stmt = con.createStatement();
			String latestRecordNumber = "";
			rs = stmt.executeQuery("select count(*) from 회원 where 회원ID = " + id);
			rs.next();
			if(rs.getInt(1)>0) {
				rs.close();
				rs = stmt.executeQuery("select 기록번호 from(select * from 기록 order by 기록번호 DESC) where 기록회원 = "+id+" and rownum = 1");
				
				if(rs.next()) { latestRecordNumber = rs.getString(1); }
				else { latestRecordNumber = "REC00001"; }
				
				rs.close();
				return latestRecordNumber;
			}
			
			return latestRecordNumber;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
