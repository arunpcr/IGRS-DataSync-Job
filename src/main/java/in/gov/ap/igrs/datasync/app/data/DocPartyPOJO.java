package in.gov.ap.igrs.datasync.app.data;

import java.util.List;

public class DocPartyPOJO {
	
	String documentId;
	String sroOffice;
	String district;
	String status;
	String TRAN_DESC;
	//StampPurchaseDate stampPurchaseDate;
	//CreatedAt $date2;
	//DocumentType documentType;
	//DocumentSubType documentSubType;
	//DutyFeeData dutyFeeData;
	//ExecutionDate executionDate;
	int sroCode;
	int distCode;
	String stampPaperValue;
	int tmarketValue;
	
	int amount;
	//UpdatedAt $date3;
	List <Customer> customer;
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getSroOffice() {
		return sroOffice;
	}
	public void setSroOffice(String sroOffice) {
		this.sroOffice = sroOffice;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTRAN_DESC() {
		return TRAN_DESC;
	}
	public void setTRAN_DESC(String tRAN_DESC) {
		TRAN_DESC = tRAN_DESC;
	}
	
	public int getSroCode() {
		return sroCode;
	}
	public void setSroCode(int sroCode) {
		this.sroCode = sroCode;
	}
	public int getDistCode() {
		return distCode;
	}
	public void setDistCode(int distCode) {
		this.distCode = distCode;
	}
	public String getStampPaperValue() {
		return stampPaperValue;
	}
	public void setStampPaperValue(String stampPaperValue) {
		this.stampPaperValue = stampPaperValue;
	}
	public int getTmarketValue() {
		return tmarketValue;
	}
	public void setTmarketValue(int tmarketValue) {
		this.tmarketValue = tmarketValue;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public List<Customer> getCustomer() {
		return customer;
	}
	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}
	@Override
	public String toString() {
		return "DocPartyPOJO [documentId=" + documentId + ", sroOffice=" + sroOffice + ", district=" + district
				+ ", status=" + status + ", TRAN_DESC=" + TRAN_DESC + ", sroCode=" + sroCode + ", distCode=" + distCode
				+ ", stampPaperValue=" + stampPaperValue + ", tmarketValue=" + tmarketValue + ", amount=" + amount
				+ ", customer=" + customer + "]";
	}
	
	
	

}
