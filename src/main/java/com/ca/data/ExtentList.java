package com.ca.data;

public class ExtentList {
	
	String totalExtentAcers;
	String totalExtentCents;
	String conveyedExtentAcers;
	String conveyedExtentCents;
	int khataNumber;
	String survayNo;
	public String getTotalExtentAcers() {
		return totalExtentAcers;
	}
	public void setTotalExtentAcers(String totalExtentAcers) {
		this.totalExtentAcers = totalExtentAcers;
	}
	public String getTotalExtentCents() {
		return totalExtentCents;
	}
	public void setTotalExtentCents(String totalExtentCents) {
		this.totalExtentCents = totalExtentCents;
	}
	public String getConveyedExtentAcers() {
		return conveyedExtentAcers;
	}
	public void setConveyedExtentAcers(String conveyedExtentAcers) {
		this.conveyedExtentAcers = conveyedExtentAcers;
	}
	public String getConveyedExtentCents() {
		return conveyedExtentCents;
	}
	public void setConveyedExtentCents(String conveyedExtentCents) {
		this.conveyedExtentCents = conveyedExtentCents;
	}
	public int getKhataNumber() {
		return khataNumber;
	}
	public void setKhataNumber(int khataNumber) {
		this.khataNumber = khataNumber;
	}
	public String getSurvayNo() {
		return survayNo;
	}
	public void setSurvayNo(String survayNo) {
		this.survayNo = survayNo;
	}
	@Override
	public String toString() {
		return "ExtentList [totalExtentAcers=" + totalExtentAcers + ", totalExtentCents=" + totalExtentCents
				+ ", conveyedExtentAcers=" + conveyedExtentAcers + ", conveyedExtentCents=" + conveyedExtentCents
				+ ", khataNumber=" + khataNumber + ", survayNo=" + survayNo + "]";
	}
	

}
