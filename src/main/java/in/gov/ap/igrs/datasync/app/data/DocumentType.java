package in.gov.ap.igrs.datasync.app.data;

public class DocumentType {
	
String TRAN_MAJ_CODE;

String TRAN_MIN_CODE;

String TRAN_DESC;

String PARTY1;

String PARTY1_CODE;

String PARTY2;

String PARTY2_CODE;

public String getTRAN_MAJ_CODE() {
	return TRAN_MAJ_CODE;
}

public void setTRAN_MAJ_CODE(String tRAN_MAJ_CODE) {
	TRAN_MAJ_CODE = tRAN_MAJ_CODE;
}

public String getTRAN_MIN_CODE() {
	return TRAN_MIN_CODE;
}

public void setTRAN_MIN_CODE(String tRAN_MIN_CODE) {
	TRAN_MIN_CODE = tRAN_MIN_CODE;
}

public String getTRAN_DESC() {
	return TRAN_DESC;
}

public void setTRAN_DESC(String tRAN_DESC) {
	TRAN_DESC = tRAN_DESC;
}

public String getPARTY1() {
	return PARTY1;
}

public void setPARTY1(String pARTY1) {
	PARTY1 = pARTY1;
}

public String getPARTY1_CODE() {
	return PARTY1_CODE;
}

public void setPARTY1_CODE(String pARTY1_CODE) {
	PARTY1_CODE = pARTY1_CODE;
}

public String getPARTY2() {
	return PARTY2;
}

public void setPARTY2(String pARTY2) {
	PARTY2 = pARTY2;
}

public String getPARTY2_CODE() {
	return PARTY2_CODE;
}

public void setPARTY2_CODE(String pARTY2_CODE) {
	PARTY2_CODE = pARTY2_CODE;
}

@Override
public String toString() {
	return "DocumentType [TRAN_MAJ_CODE=" + TRAN_MAJ_CODE + ", TRAN_MIN_CODE=" + TRAN_MIN_CODE + ", TRAN_DESC="
			+ TRAN_DESC + ", PARTY1=" + PARTY1 + ", PARTY1_CODE=" + PARTY1_CODE + ", PARTY2=" + PARTY2
			+ ", PARTY2_CODE=" + PARTY2_CODE + "]";
}



}
