package com.ca.data;

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
	StampPurchaseDate stampPurchaseDate;
	CreatedAt $date2;
	DocumentType documentType;
	DocumentSubType documentSubType;
	DutyFeeData dutyFeeData;
	ExecutionDate executionDate;
	int sroCode;
	int distCode;
	String stampPaperValue;
	double tmarketValue;
	//int amount;
	long amount;
	UpdatedAt $date3;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public double getTmarketValue() {
		return tmarketValue;
	}

	public void setTmarketValue(double tmarketValue) {
		this.tmarketValue = tmarketValue;
	}

	
	public DutyFeeData getDutyFeeData() {
		return dutyFeeData;
	}

	public void setDutyFeeData(DutyFeeData dutyFeeData) {
		this.dutyFeeData = dutyFeeData;
	}

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
	
	public CreatedAt get$date2() {
		return $date2;
	}
	public void set$date2(CreatedAt $date2) {
		this.$date2 = $date2;
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
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public UpdatedAt get$date3() {
		return $date3;
	}
	public void set$date3(UpdatedAt $date3) {
		this.$date3 = $date3;
	}
	public ExecutionDate getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(ExecutionDate executionDate) {
		this.executionDate = executionDate;
	}
	public StampPurchaseDate getStampPurchaseDate() {
		return stampPurchaseDate;
	}
	public void setStampPurchaseDate(StampPurchaseDate stampPurchaseDate) {
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
				+ ", regWithValue=" + regWithValue + ", stampPurchaseDate=" + stampPurchaseDate + ", $date2=" + $date2
				+ ", documentType=" + documentType + ", documentSubType=" + documentSubType + ", dutyFeeData="
				+ dutyFeeData + ", executionDate=" + executionDate + ", sroCode=" + sroCode + ", distCode=" + distCode
				+ ", stampPaperValue=" + stampPaperValue + ", tmarketValue=" + tmarketValue + ", amount=" + amount
				+ ", $date3=" + $date3 + "]";
	}

	public char[] toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
}