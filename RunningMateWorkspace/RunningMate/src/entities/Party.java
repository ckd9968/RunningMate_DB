package entities;

import java.sql.Timestamp;
import java.util.Date;

public class Party {
	private String partyName;
	private String partyID;
	private String meetingPlace;
	private String leader;
	private Timestamp meetingDate;
	
	
	public Party(String partyID, String partyName, String meetingPlace, String leader, String year, String month, String date, String hour, String minute) {
		super();
		this.partyName = partyName;
		this.partyID = partyID;
		this.meetingPlace = meetingPlace;
		this.leader = leader;
		Date mydate = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(date), Integer.parseInt(hour), Integer.parseInt(minute), 0);
		meetingDate = new Timestamp(mydate.getTime());
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getPartyID() {
		return partyID;
	}
	public void setPartyID(String partyID) {
		this.partyID = partyID;
	}
	public String getMeetingPlace() {
		return meetingPlace;
	}
	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public java.sql.Timestamp getMeetingDate() {
		return meetingDate;
	}
	public void setMeetingDate(java.sql.Timestamp meetingDate) {
		this.meetingDate = meetingDate;
	}
	
	
}
