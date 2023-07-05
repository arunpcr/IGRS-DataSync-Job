package com.ca.data;

import java.util.List;

public class PartyDetails {

	public int age;
	public String name;
	public String applicationId;
	public String relationType;
	public String relationName;
	public String representSubType;
	public String panNoOrForm60or61;
	public String tan;
	public String partyCode;
	public long aadhaar;
	public long phone;
	public int seqNumber;
	public String email;
	public String address;
	public boolean isPresenter;
	public List<Represent> represent;
	
	public long getAadhaar() {
		return aadhaar;
	}
	public void setAadhaar(long aadhaar) {
		this.aadhaar = aadhaar;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	
	public int getSeqNumber() {
		return seqNumber;
	}
	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}
	
public boolean isPresenter() {
		return isPresenter;
	}
	public void setPresenter(boolean isPresenter) {
		this.isPresenter = isPresenter;
	}
		public List<Represent> getRepresent() {
		return represent;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public void setRepresent(List<Represent> represent) {
		this.represent = represent;
	}
	public String getPartyCode() {
		return partyCode;
	}
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public String getRelationName() {
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	public String getPanNoOrForm60or61() {
		return panNoOrForm60or61;
	}
	public void setPanNoOrForm60or61(String panNoOrForm60or61) {
		this.panNoOrForm60or61 = panNoOrForm60or61;
	}
	public String getTan() {
		return tan;
	}
	public void setTan(String tan) {
		this.tan = tan;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "PartyDetails [age=" + age + ", name=" + name + ", applicationId=" + applicationId + ", relationType="
				+ relationType + ", relationName=" + relationName + ", representSubType=" + representSubType
				+ ", panNoOrForm60or61=" + panNoOrForm60or61 + ", tan=" + tan + ", partyCode=" + partyCode
				+ ", aadhaar=" + aadhaar + ", phone=" + phone + ", seqNumber=" + seqNumber + ", email=" + email
				+ ", address=" + address + ", isPresenter=" + isPresenter + ", represent=" + represent + "]";
	}
	
	
}
