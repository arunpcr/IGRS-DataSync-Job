package in.gov.ap.igrs.datasync.app.data;

import java.sql.Date;

public class UpdatedAt {
	
	//public String $date;
	
	public Date $date;

	public Date get$date() {
		return $date;
	}

	public void set$date(Date $date) {
		this.$date = $date;
	}

	@Override
	public String toString() {
		return "UpdatedAt [$date=" + $date + "]";
	}

	

}
