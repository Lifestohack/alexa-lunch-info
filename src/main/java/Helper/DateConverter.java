package Helper;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Models.Days;

public class DateConverter {

	public static Days getToday() {
		Date now = new Date();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", Locale.ENGLISH); // the day of the week spelled out completely
		String day = simpleDateformat.format(now).toString().toUpperCase();
		return Days.valueOf(day);
	}

}
