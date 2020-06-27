package com.kakaopay.spreadMoney.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	public static String getNow() {
		Date now = new Date();
		return DATE_FORMAT.format(now);
	}
	
	
	public static boolean isAfterMinute(String stTime, int minute) throws Exception{
		Date now = new Date();
		Date stTimeDate;
		stTimeDate = DATE_FORMAT.parse(stTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(stTimeDate);
		cal.add(Calendar.MINUTE, minute);
		
		if(cal.getTime().after(now)) {
			return true;
		}
		return false;
	}
	
	public static boolean isAfterDay(String stTime, int day) throws 	Exception{
		Date now = new Date();
		Date stTimeDate;
		stTimeDate = DATE_FORMAT.parse(stTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(stTimeDate);
		cal.add(Calendar.DATE, day);
		
		if(cal.getTime().after(now)) {
			return true;
		}
		return false;
	}
}
