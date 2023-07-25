package in.gov.ap.igrs.datasync.app.data;

import java.sql.Date;

public class DocumentDetails {

	String documentId;
	String userId;
	String sroOffice;
	String district;
	String status;
	String TRAN_DESC;
	String docProcessType;
	String docProcessCode;
	String regWithCode;
	String regWithValue;
	Date stampPurchaseDate;
	Date createdAt;
	DocumentType documentType;
	DocumentSubType documentSubType;
	DutyFeeData dutyFeeData;
	Date executionDate;
	int sroCode;
	int distCode;
	String stampPaperValue;
	//int tmarketValue;
	double tmarketValue;
	int amount;
	Date updatedAt;
	
	public double getTmarketValue() {
		return tmarketValue;
	}

	public void setTmarketValue(double tmarketValue) {
		this.tmarketValue = tmarketValue;
	}

	
	//int noOfDocuments=0;
	
/*	public int getTmarketValue() {
		return tmarketValue;
	}*/

	public DutyFeeData getDutyFeeData() {
		return dutyFeeData;
	}

	public void setDutyFeeData(DutyFeeData dutyFeeData) {
		this.dutyFeeData = dutyFeeData;
	}

	/*public void setTmarketValue(int tmarketValue) {
		this.tmarketValue = tmarketValue;
	}*/
	public String getTRAN_DESC() {
		return TRAN_DESC;
	}
	
	public void setTRAN_DESC(String tRAN_DESC) {
		TRAN_DESC = tRAN_DESC;
	}
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
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public DocumentType getDocumentType() {
		return documentType;
	}
	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}
	public DocumentSubType getDocumentSubType() {
		return documentSubType;
	}
	public void setDocumentSubType(DocumentSubType documentSubType) {
		this.documentSubType = documentSubType;
	}
	
	
	public String getStampPaperValue() {
		return stampPaperValue;
	}
	public void setStampPaperValue(String stampPaperValue) {
		this.stampPaperValue = stampPaperValue;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getUpdatedAt() {
		return this.updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}
	public Date getStampPurchaseDate() {
		return stampPurchaseDate;
	}
	public void setStampPurchaseDate(Date stampPurchaseDate) {
		this.stampPurchaseDate = stampPurchaseDate;
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
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDocProcessType() {
		return docProcessType;
	}

	public void setDocProcessType(String docProcessType) {
		this.docProcessType = docProcessType;
	}

	public String getDocProcessCode() {
		return docProcessCode;
	}

	public void setDocProcessCode(String docProcessCode) {
		this.docProcessCode = docProcessCode;
	}

	public String getRegWithCode() {
		return regWithCode;
	}

	public void setRegWithCode(String regWithCode) {
		this.regWithCode = regWithCode;
	}

	public String getRegWithValue() {
		return regWithValue;
	}

	public void setRegWithValue(String regWithValue) {
		this.regWithValue = regWithValue;
	}

	@Override
	public String toString() {
		return "DocumentDetails [documentId=" + documentId + ", userId=" + userId + ", sroOffice=" + sroOffice
				+ ", district=" + district + ", status=" + status + ", TRAN_DESC=" + TRAN_DESC + ", docProcessType="
				+ docProcessType + ", docProcessCode=" + docProcessCode + ", regWithCode=" + regWithCode
				+ ", regWithValue=" + regWithValue + ", stampPurchaseDate=" + stampPurchaseDate + ", createdAt="
				+ createdAt + ", documentType=" + documentType + ", documentSubType=" + documentSubType
				+ ", dutyFeeData=" + dutyFeeData + ", executionDate=" + executionDate + ", sroCode=" + sroCode
				+ ", distCode=" + distCode + ", stampPaperValue=" + stampPaperValue + ", tmarketValue=" + tmarketValue
				+ ", amount=" + amount + ", updatedAt=" + updatedAt + "]";
	}

	public char[] toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
}