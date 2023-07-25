package in.gov.ap.igrs.datasync.app.data;

import java.sql.Date;

public class StampPurchaseDate {
	
	/* public long $date;

		public long get$date() {
			return $date;
		}

		public void set$date(long $date) {
			this.$date = $date;
		}
*/
	public Date $date;

	public Date get$date() {
		return $date;
	}

	public void set$date(Date $date) {
		this.$date = $date;
	}

	@Override
	public String toString() {
		return "StampPurchaseDate [$date=" + $date + "]";
	}

}
