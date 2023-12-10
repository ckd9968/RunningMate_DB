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
			// 달린거리, 달린날짜, 달린장소는 모두 동일하다.
			pstmt.setDouble(3, Double.parseDouble(arguments[0]));
			pstmt.setDate(4, record_time);
			pstmt.setString(5, arguments[1]);
//			System.out.println(ids.length);
			
			// 각 회원 ID 별로 기록 번호를 생성하여 ID 존재 유무를 판별한다.
			for(int i = 0; i < ids.length; i++) {
				String recordSerialNumber = getRecordNumberOfID(ids[i]); // 기록 번호가 "" 이면 존재하지 않는 ID이다.
				System.out.println("그룹기록하기 시리얼 넘버 출력 : "+recordSerialNumber);
				if(recordSerialNumber.length() > 0) {
					System.out.println(recordSerialNumber);
					pstmt.setString(1, recordSerialNumber);
					pstmt.setString(2, ids[i]);
					pstmt.addBatch(); // 일괄 처리를 위해 저장한다.
					
				} else {
					hitFlag = false; // 존재하지 않는 ID인 경우 hitFlage를 false로 만들어 커밋되지 않도록 한다.
					invalidIDs.add(ids[i]); // ID 수정 요구를 위하여 리스트에 잘못된 ID를 삽입한다.
				}
			}
			
			if(!hitFlag) { // 무효한 ID가 전달된 경우 변경연산 실행 자체를 차단하여 트랜잭션이 수행되지 않도록 한다.
				System.out.println("hit flag : 거짓");
				con.setAutoCommit(true);
				pstmt.close();
				return new Object[] {2, false, invalidIDs};
			}
			
			int[] result = pstmt.executeBatch();
			pstmt.close();
			con.commit(); // 변경 사항을 커밋한다.
			return new Object[] {2, true};
		} catch(SQLException e) {
			try {
				con.rollback(); // 오류 발생 시 롤백을 수행한다.
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
				System.out.println("파티원 : " + rs.getString(1));
				vids.add(rs.getString(1));
			}
			
			String[] partyIDs = new String[vids.size()];
			for(int i = 0; i < vids.size(); i++) {
				partyIDs[i] = vids.get(i);
			}
			
			return this.recordGroup(partyIDs, arguments);
		} catch(SQLException e) {
			
		}
		
		return null;
	}
	
	private String getRecordNumberOfID(String id) {
		ResultSet rs;
		try {
			// 기록번호를 반환해야함.
			// Statement를 사용한다.
			Statement stmt = con.createStatement();
			String latestRecordNumber = "";
			// 우선 전달된 회원ID가 회원테이블에 존재하는지 확인한다.
			rs = stmt.executeQuery("select count(*) from 회원 where 회원ID = '" + id+"'");
			rs.next();
			if(rs.getInt(1)>0) { // 존재한다면 집계함수 결과가 1이므로 아래 코드가 수행된다.
				rs.close(); // 다음 질의를 위한 리소스 해제
				// 기록 번호 별로 정렬하기 위하여 부질의를 from 절에 적용한다. 그리고 행번호가 1인 튜플이 가장 최근 등록된 기록이다.
				rs = stmt.executeQuery("select 기록번호 from(select * from 기록 order by 기록번호 DESC) where 기록회원 = '"+id+"' and rownum = 1");
				
				// 만약 기록이 존재한다면
				if(rs.next()) { 
					// 마지막 기록에서 1 증가한 기록 번호를 만든다.
					latestRecordNumber = getNextRecordNumber(rs.getString(1)); }
				else { latestRecordNumber = "REC00001"; }
				// 없다면 1부터 시작해야 하므로 기록번호 1을 생성한다.
				
				rs.close(); // 리소스 해제
				return latestRecordNumber; // 결과를 반환한다.
			}
			
			return latestRecordNumber; // 존재하지 않는 회원이라면 ""가 반환된다.
			
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
