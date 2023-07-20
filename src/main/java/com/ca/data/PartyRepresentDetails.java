package com.ca.data;

public class PartyRepresentDetails {

	public String id;	
	public String name;
	public String parentPartyId;
	public String documentId;
	public String relationType;
	public String relationName;
	public int age;
	public String panNoOrForm60or61;
	public String tan;
	public long aadhaar;
	public String email;
	public long phone;
	public CreatedAt createdAt;
	public UpdatedAt updatedAt;
	public String currentAddress;
	public String address;
	public int seqNumber;
	public boolean isPresenter;
	public boolean isPresenter() {
		return isPresenter;
	}
	public void setPresenter(boolean isPresenter) {
		this.isPresenter = isPresenter;
	}
	public CreatedAt getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(CreatedAt createdAt) {
		this.createdAt = createdAt;
	}
	public UpdatedAt getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(UpdatedAt updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentPartyId() {
		return parentPartyId;
	}
	public void setParentPartyId(String parentPartyId) {
		this.parentPartyId = parentPartyId;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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
	
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getSeqNumber() {
		return seqNumber;
	}
	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}
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
	@Override
	public String toString() {
		return "PartyRepresentDetails [id=" + id + ", name=" + name + ", parentPartyId=" + parentPartyId
				+ ", documentId=" + documentId + ", relationType=" + relationType + ", relationName=" + relationName
				+ ", age=" + age + ", panNoOrForm60or61=" + panNoOrForm60or61 + ", tan=" + tan + ", aadhaar=" + aadhaar
				+ ", email=" + email + ", phone=" + phone + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", currentAddress=" + currentAddress + ", address=" + address + ", seqNumber=" + seqNumber
				+ ", isPresenter=" + isPresenter + "]";
	}
	
	
	
}













