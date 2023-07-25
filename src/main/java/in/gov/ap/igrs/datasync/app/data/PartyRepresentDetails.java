package in.gov.ap.igrs.datasync.app.data;

import java.sql.Date;

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
	//public SimplePOJO aadhar;
	public long aadhaar;
	public String email;
	//public Phone phone;
	public long phone;
	public Date createdAt;
	public boolean isPresenter;
	public String currentAddress;
	public String address;
	public int seqNumber;
	public String gender;
	public Date DOB;
	
	
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public boolean isPresenter() {
		return isPresenter;
	}
	public void setPresenter(boolean isPresenter) {
		this.isPresenter = isPresenter;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date updatedAt;
	/*public Phone getPhone() {
		return phone;
	}
	public void setPhone(Phone phone) {
		this.phone = phone;
	}*/
	
	/*public Date createdAt;
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}*/
	//public Date updatedAt;
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
	
	/*public SimplePOJO getAadhar() {
		return aadhar;
	}
	public void setAadhar(SimplePOJO aadhar) {
		this.aadhar = aadhar;
	}*/
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
				+ ", email=" + email + ", phone=" + phone + ", createdAt=" + createdAt + ", isPresenter=" + isPresenter
				+ ", currentAddress=" + currentAddress + ", address=" + address + ", seqNumber=" + seqNumber
				+ ", gender=" + gender + ", DOB=" + DOB + ", updatedAt=" + updatedAt + "]";
	}
	
	
	
	
	
	/*public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}*/
	
	
}













