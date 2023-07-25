package in.gov.ap.igrs.datasync.app.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum IGRSDateUtil {
	
	INSTANCE;
	
	public String convertToDateStringFromDate(Date date)
	{
		String formatDate = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			formatDate = dateFormat.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return formatDate;
	}

}
