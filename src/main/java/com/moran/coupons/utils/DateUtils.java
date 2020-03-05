package com.moran.coupons.utils;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Calendar;

public class DateUtils {

	public static LocalDate getCurrentDate() {
		// Gets the current date and time
		Calendar now = Calendar.getInstance();

		//Split current time
		int currentYear = now.get(Calendar.YEAR);

		// This function drop 1 month when getting the current date
		int currentMonth = now.get(Calendar.MONTH) + 1;
		int currentDay = now.get(Calendar.DAY_OF_MONTH);

		//Setting format for dates
		LocalDate currentDate = LocalDate.of(currentYear, currentMonth, currentDay);

		//Checking what the time received from the current date is
		return currentDate;
	}



	public static boolean isDatePassed (ChronoLocalDate expiryDate) {
		LocalDate currentDate = getCurrentDate();
		return currentDate.isAfter(expiryDate);
	}



	public static boolean isTodayBeforeDate (ChronoLocalDate startDate) {
		LocalDate currentDate = getCurrentDate();
		return currentDate.isBefore(startDate);
	}
}
