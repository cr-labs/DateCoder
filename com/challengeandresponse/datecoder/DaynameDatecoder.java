package com.challengeandresponse.datecoder;

import java.util.Hashtable;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DaynameDatecoder implements DatecoderI {
	public static final String	PRODUCT_SHORT 	= "DaynameDatecoder";
	public static final String 	PRODUCT_LONG	= "Challenge/Response Day-name Datecoder";
	public static final String	VERSION_SHORT	= "0.15";
	public static final String	VERSION_LONG 	=  PRODUCT_LONG + " " + VERSION_SHORT;
	public static final String	COPYRIGHT		= "Copyright (c) 2007 Challenge/Response, LLC, Cambridge, MA";

	private LocalDate 	dayLookupStartDate; 	// whenever this is NOT "today", regenerate the table
	private Hashtable <String,DateTime>	daynamesToDates;

	private static final DateTimeFormatter daynameShortFormatter = DateTimeFormat.forPattern("E");
	private static final DateTimeFormatter daynameLongFormatter = DateTimeFormat.forPattern("EEEE");

	public DaynameDatecoder() {
		daynamesToDates = new Hashtable <String, DateTime> ();
		refreshDaynamesToDates();
	}

	/**
	 * Refresh the daynames-to-dates table. Only one thread should be in here 
	 * at a time, and that thread will update the dayLookupStartDate, thereby
	 * preventing any other from running the method again before the next expiration
	 *
	 */
	private synchronized void refreshDaynamesToDates() {
		// only run if it's a new day 
		// this is a safety so in the case of multiple callers, it only updates once per day
		if ((dayLookupStartDate != null) &&
			(dayLookupStartDate.getDayOfMonth() == (new LocalDate().getDayOfMonth())))
			return;
		// update the table
		dayLookupStartDate = new LocalDate();
		DateTime nextDate = dayLookupStartDate.toDateTimeAtCurrentTime();
		for (int i = 1; i <= 7; i++) {
			nextDate = nextDate.plusDays(1);
			daynamesToDates.put(nextDate.toString(daynameShortFormatter).toLowerCase(),nextDate);
			daynamesToDates.put(nextDate.toString(daynameLongFormatter).toLowerCase(),nextDate);
		}
	}

	/**
	 * Given a day-name, get the matching date. Also periodically check the 
	 * @param dayname
	 * @return
	 */
	private DateTime getDateForDayname(String dayname) {
		// refresh only if necessary
		if (dayLookupStartDate.getDayOfMonth() != (new LocalDate().getDayOfMonth())) {
			refreshDaynamesToDates();
		}
		return (DateTime) daynamesToDates.get(dayname.trim().toLowerCase());
	}


	public String getVersion() {
		return VERSION_SHORT;
	}

	public DateTime parseDate(String dateString) {
		// short circuit if any digits are in the date - we don't code numerics here
		if (dateString.matches(".*?[0-9].*?")) {
			return null;
		}
		String ds = dateString.toLowerCase().trim();
		DateTime result = null;
		if (ds.equals("today"))
			result = new DateTime();
		else if (ds.equals("tomorrow"))
			result = new DateTime().plusDays(1);
		else if (ds.equals("yesterday"))
			result = new DateTime().minusDays(1);
		else 
			result = getDateForDayname(dateString);

		// if a result was found, return it with the time set to 00:00:00.0, else return null
		if (result != null)
			return result.withTime(0,0,0,0);
		else
			return null;
	}



//	TESTING
	public static void main(String[] args) {
		DaynameDatecoder hdc = new DaynameDatecoder();
		System.out.println(hdc.parseDate("today"));
		System.out.println(hdc.parseDate("tomorrow"));
		System.out.println(hdc.parseDate("yesterday"));

		System.out.println(hdc.parseDate("aaaaaaaaa0eeeeee"));
		System.out.println(hdc.parseDate("sat"));


		System.out.println(hdc.daynamesToDates);
	}





}

