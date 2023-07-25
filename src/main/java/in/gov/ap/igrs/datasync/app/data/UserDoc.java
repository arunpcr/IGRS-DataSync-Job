package in.gov.ap.igrs.datasync.app.data;

public class UserDoc {
	String loginName;
	String loginId;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	@Override
	public String toString() {
		return "UserDoc [loginName=" + loginName + ", loginId=" + loginId + "]";
	}

}
