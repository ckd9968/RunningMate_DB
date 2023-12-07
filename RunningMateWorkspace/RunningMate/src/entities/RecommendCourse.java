package entities;

public class RecommendCourse {
	private String courseNumber;
	private String regMember;
	private String address;
	private String atmosphere;
	private int visitCount;
	
	public RecommendCourse(String courseNumber, String regMember, String address, String atmosphere, int visitCount) {
		super();
		this.courseNumber = courseNumber;
		this.regMember = regMember;
		this.address = address;
		this.atmosphere = atmosphere;
		this.visitCount = visitCount;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getRegMember() {
		return regMember;
	}

	public void setRegMember(String regMember) {
		this.regMember = regMember;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAtmosphere() {
		return atmosphere;
	}

	public void setAtmosphere(String atmosphere) {
		this.atmosphere = atmosphere;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	
	
}


