package in.gov.ap.igrs.datasync.app.data;

public class DutyFeeData {
	
	int rf_p;
	int sd_p;
	int td_p;
	int uc_p;
	public int getRf_p() {
		return rf_p;
	}
	public void setRf_p(int rf_p) {
		this.rf_p = rf_p;
	}
	public int getSd_p() {
		return sd_p;
	}
	public void setSd_p(int sd_p) {
		this.sd_p = sd_p;
	}
	
	public int getTd_p() {
		return td_p;
	}
	public void setTd_p(int td_p) {
		this.td_p = td_p;
	}
	public int getUc_p() {
		return uc_p;
	}
	public void setUc_p(int uc_p) {
		this.uc_p = uc_p;
	}
	@Override
	public String toString() {
		return "DutyFeeData [rf_p=" + rf_p + ", sd_p=" + sd_p + ", td_p=" + td_p + ", uc_p=" + uc_p + "]";
	}
	
	

}
