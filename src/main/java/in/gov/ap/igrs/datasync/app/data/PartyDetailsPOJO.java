package in.gov.ap.igrs.datasync.app.data;

import java.util.List;

public class PartyDetailsPOJO {
	
	public int age;
	//public String phone;
	public String name;
	public String applicationId;
	public String relationType;
	public String relationName;
	public String representSubType;
	public String panNoOrForm60or61;
	public String tan;
	public String partyCode;
	public String aadhaar;
	public String phoneNo;
	//public int aadhaar;
	public int seqNumber;
	//public int phone;
	public String email;
	public String address;
	public boolean isPresenter;
	public List<Represent> represent;
	
	public String getAadhaar() {
		return aadhaar;
	}
	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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
	/*public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}*/
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
	/*public String getRepresentSubType() {
		return representSubType;
	}
	public void setRepresentSubType(String representSubType) {
		this.representSubType = representSubType;
	}*/
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
	/*public int getPartyId() {
		return partyId;
	}
	public void setPartyId(int partyId) {
		this.partyId = partyId;
	}*/

	
	
	
	
	


}
