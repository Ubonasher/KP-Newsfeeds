package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class AutoDateReslolver {
													// YYYY					MM					DD
	private static final String DATE_1_FORMAT = "([12][0-9][0-9][0-9])-([0][0-9]|[1][0-2])-[0123][0-9]";
	private static final String DATE_2_FORMAT = "([12][0-9][0-9][0-9]).([0][0-9]|[1][0-2]).[0123][0-9]";

													// MM				DD				YYYY
	private static final String DATE_3_FORMAT = "([0][1-9]|[1][0-2])-[0123][0-9]-([12][0-9][0-9][0-9])";
	private static final String DATE_4_FORMAT = "([0][1-9]|[1][0-2]).[0123][0-9].([12][0-9][0-9][0-9])";

													// DD				MM				YYYY
	private static final String DATE_5_FORMAT = "[0123][0-9]-([0][1-9]|[1][0-2])-([12][0-9][0-9][0-9])";
	private static final String DATE_6_FORMAT = "[0123][0-9].([0][1-9]|[1][0-2]).([12][0-9][0-9][0-9])";

	private static final Pattern pattern1 = Pattern.compile(DATE_1_FORMAT);
	private static final Pattern pattern2 = Pattern.compile(DATE_2_FORMAT);
	private static final Pattern pattern3 = Pattern.compile(DATE_3_FORMAT);
	private static final Pattern pattern4 = Pattern.compile(DATE_4_FORMAT);
	private static final Pattern pattern5 = Pattern.compile(DATE_5_FORMAT);
	private static final Pattern pattern6 = Pattern.compile(DATE_6_FORMAT);
	
	private static final SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy.MM.dd");
	
	private static final SimpleDateFormat formatter3 = new SimpleDateFormat("MM-dd-yyyy");
	private static final SimpleDateFormat formatter4 = new SimpleDateFormat("MM.dd.yyyy");

	private static final SimpleDateFormat formatter5 = new SimpleDateFormat("dd-MM-yyyy");
	private static final SimpleDateFormat formatter6 = new SimpleDateFormat("dd.MM.yyyy");

	public static Date resolve(String date){
		try {
			if(pattern1.matcher(date).matches()) {
				return formatter1.parse(date);
			} else
			if(pattern2.matcher(date).matches()) {
				return formatter2.parse(date);
			} else
			if(pattern3.matcher(date).matches()) {
				return formatter3.parse(date);
			} else
			if(pattern4.matcher(date).matches()) {
				return formatter4.parse(date);
			} else
			if(pattern5.matcher(date).matches()) {
				return formatter5.parse(date);
			} else
			if(pattern6.matcher(date).matches()) {
				return formatter6.parse(date);
			}
		} catch (Exception ex) {
			return null;
		}
		return null;
	}
}
