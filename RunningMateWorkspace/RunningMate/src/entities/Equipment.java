package entities;

public class Equipment {
	private String equipmentID;
	private String memberID;
	private String categry;
	private String brand;
	private String name;
	private int privce;
	
	public Equipment(String equipmentID, String memberID, String categry, String brand, String name, int privce) {
		super();
		this.equipmentID = equipmentID;
		this.memberID = memberID;
		this.categry = categry;
		this.brand = brand;
		this.name = name;
		this.privce = privce;
	}
	public String getEquipmentID() {
		return equipmentID;
	}
	public void setEquipmentID(String equipmentID) {
		this.equipmentID = equipmentID;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getCategry() {
		return categry;
	}
	public void setCategry(String categry) {
		this.categry = categry;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrivce() {
		return privce;
	}
	public void setPrivce(int privce) {
		this.privce = privce;
	}
	
	
}
