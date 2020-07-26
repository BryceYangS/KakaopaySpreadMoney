package com.kakaopay.spreadMoney.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class DateUtil {

	public static boolean isAfterMinute(LocalDateTime stTime, int minute) throws Exception{
		
		LocalDateTime now = LocalDateTime.now();
		
		Duration du = Duration.between(stTime, now);
		
		int betweenMin = (int) (du.getSeconds() / 60);
		if(betweenMin >= minute) return true;
		
		return false;
	}
	
	public static boolean isAfterDay(LocalDateTime stTime, int day) throws 	Exception{
		
		LocalDate now = LocalDate.now();
		
		Period pe = Period.between(stTime.toLocalDate(), now);
		
		if(pe.getDays() > day) return true;
		
		return false;
	}
}
