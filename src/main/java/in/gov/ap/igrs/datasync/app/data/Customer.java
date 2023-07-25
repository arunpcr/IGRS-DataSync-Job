package in.gov.ap.igrs.datasync.app.data;

public class Customer {
	String name;
String partyCode;
public Boolean isPresenter;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPartyCode() {
		return partyCode;
	}
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	public Boolean getIsPresenter() {
		return isPresenter;
	}
	public void setIsPresenter(Boolean isPresenter) {
		this.isPresenter = isPresenter;
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", partyCode=" + partyCode + ", isPresenter=" + isPresenter + "]";
	}
	
	

}
