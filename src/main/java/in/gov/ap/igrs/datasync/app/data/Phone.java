package in.gov.ap.igrs.datasync.app.data;

public class Phone {

	//public String $numberLong;
	public long $numberLong;

	public long get$numberLong() {
		return $numberLong;
	}

	public void set$numberLong(long $numberLong) {
		this.$numberLong = $numberLong;
	}

	@Override
	public String toString() {
		return "Phone [$numberLong=" + $numberLong + "]";
	}

	
}
