package in.gov.ap.igrs.datasync.app.data;

public class DocumentSubType {

	String TRAN_MAJ_CODE;
	String TRAN_MIN_CODE;
	String TRAN_DESC;
		
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

	@Override
	public String toString() {
		return "DocumentSubType [TRAN_MAJ_CODE=" + TRAN_MAJ_CODE + ", TRAN_MIN_CODE=" + TRAN_MIN_CODE + ", TRAN_DESC="
				+ TRAN_DESC + "]";
	}
	

}
