package com.ca.data;

import com.google.gson.annotations.SerializedName;

public class MemberData {
	@SerializedName("MembershipID")
	long membershipID;
	
	@SerializedName("EntryDate")
	String entryDate;
	
	@SerializedName("status")
	int status;
	
	public Long getMembershipID() {
		return membershipID;
	}
	public void setMembershipID(long membershipID) {
		this.membershipID = membershipID;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "MemberData [membershipID=" + membershipID + ", entryDate=" + entryDate + ", status=" + status + "]";
	}
	
	
	
	

}
