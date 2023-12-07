package controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RecordingController {
	private Connection con = null;
	
	public Object[] recordSingle(String id, String[] arguments) {
		DB_Connector.connectToDB("단일 기록하기");
		con = DB_Connector.getConnection();
		Date record_time = new Date(System.currentTimeMillis());
		
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into 기록 values(?,?,?,?,?)");
			pstmt.setString(1, getRecordNumberOfID(id));
			pstmt.setString(2, id);
			pstmt.setDouble(3, Double.parseDouble(arguments[0]));
			pstmt.setDate(4, record_time);
			pstmt.setString(5, arguments[1]);
			pstmt.executeUpdate();
			
			pstmt.close();
			return new Object[] {1,true};
		} catch(SQLException e) {
			System.err.println("단일 기록하기 에러");
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			DB_Connector.closeConnection();
		}
		
		return new Object[] {1, false};
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
			pstmt.setDouble(3, Double.parseDouble(arguments[0]));
			pstmt.setDate(4, record_time);
			pstmt.setString(5, arguments[1]);
			
			for(int i = 0; i < ids.length; i++) {
				String recordSerialNumber = getRecordNumberOfID(ids[i]);
				System.out.println("그룹기록하기 시리얼 넘버 출력 : "+recordSerialNumber);
				if(recordSerialNumber.length() > 0) {
					System.out.println(recordSerialNumber);
					pstmt.setString(1, recordSerialNumber);
					pstmt.setString(2, ids[i]);
					pstmt.addBatch();
					
				} else {
					hitFlag = false;
					invalidIDs.add(ids[i]);
				}
			}
			
			if(!hitFlag) {
				System.out.println("hit flag : 거짓");
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
	
	
	// 파티는 등록 기능 이후에 함
	// userID로 파티ID를 요청 -> 파티 ID를 가진 모든 회원으로 기록 등록. -> 그룹 레코드 호출하기
	public Object[] recordParty(String userID, String[] arguments) {
		DB_Connector.connectToDB("파티 등록");
		this.con = DB_Connector.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select 참여파티 from 회원 where 회원ID='"+userID+"' and 참여파티 is not null");
			String partyID = null;
			if(rs.next()) {
				partyID = rs.getString(1);
				rs.close();
			} else {
				return new Object[] {3, false};
			}
			rs = stmt.executeQuery("select 회원ID from 회원 where 참여파티='"+partyID+"'");
			List<String> vids = new ArrayList<String>();
			while(rs.next()) {
				vids.add(rs.getString(1));
			}
			
			String[] partyIDs = new String[vids.size()+1];
			partyIDs[0] = userID;
			
			for(int i = 0; i < vids.size(); i++) {
				partyIDs[i+1] = vids.get(i);
			}
			
			this.recordGroup(partyIDs, arguments);
		} catch(SQLException e) {
			
		}
		
		return null;
	}
	
	private String getRecordNumberOfID(String id) {
		ResultSet rs;
		try {
			// 기록번호를 반환해야함.
			Statement stmt = con.createStatement();
			String latestRecordNumber = "";
			rs = stmt.executeQuery("select count(*) from 회원 where 회원ID = '" + id+"'");
			rs.next();
			if(rs.getInt(1)>0) {
				rs.close();
				rs = stmt.executeQuery("select 기록번호 from(select * from 기록 order by 기록번호 DESC) where 기록회원 = '"+id+"' and rownum = 1");
				
				if(rs.next()) { 
					latestRecordNumber = getNextRecordNumber(rs.getString(1)); }
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
	
	private String getNextRecordNumber(String rec_id) {
		char[] cur = rec_id.toCharArray();
		int i = 3;
		while(cur[i] == '0') {
			i++;
		}
		
		char[] next_num = new char[i];
		next_num[0] = 'R'; next_num[1] = 'E'; next_num[2] = 'C';
		for(int k = 3; k < i; k++) {
			next_num[k] = '0';
		}
		
		return new String(next_num) + (Integer.parseInt(rec_id.substring(i)) + 1);
	}
	
	
	// 간이 테스트 드라이버
	public static void main(String[]args) {
		System.out.println("hello");
		System.out.println((new RecordingController()).getNextRecordNumber("REC00001"));
		
	}
}
