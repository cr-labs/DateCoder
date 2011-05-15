package com.challengeandresponse.datecoder;

import org.joda.time.DateTime;

public class HolidayDatecoder implements DatecoderI {
	public static final String	PRODUCT_SHORT 	= "HolidayDatecoder";
	public static final String 	PRODUCT_LONG	= "Challenge/Response Holiday Datecoder";
	public static final String	VERSION_SHORT	= "0.10";
	public static final String	VERSION_LONG 	=  PRODUCT_LONG + " " + VERSION_SHORT;
	public static final String	COPYRIGHT		= "Copyright (c) 2007 Challenge/Response, LLC, Cambridge, MA";

	public String getVersion() {
		return VERSION_SHORT;
	}

	public DateTime parseDate(String dateString) {
		DateTime result = null;
		
		String ds = dateString.toLowerCase().trim();
		ds = ds.replaceAll("'", "");
		if (ds.equals("christmas"))
			result = new DateTime().withMonthOfYear(12).withDayOfMonth(25);
		else if (ds.equals("christmas day"))
			result = new DateTime().withMonthOfYear(12).withDayOfMonth(25);
		else if (ds.equals("christmas eve"))
			result = new DateTime().withMonthOfYear(12).withDayOfMonth(24);
		else if (ds.equals("boxing day"))
			result = new DateTime().withMonthOfYear(12).withDayOfMonth(26);
		else if (ds.equals("new years day"))
			result = new DateTime().withDate(new DateTime().getYear()+1,1,1);
		else if (ds.equals("new years eve"))
			result = new DateTime().withMonthOfYear(12).withDayOfMonth(31);
		
		if (result != null)
			return result.withTime(0,0,0,0);
		else
			return null;
	}

	
	
	
	// TESTING
	public static void main(String[] args) {
		HolidayDatecoder hdc = new HolidayDatecoder();
		System.out.println(hdc.parseDate("christmas day"));
		System.out.println(hdc.parseDate("christmas"));
		System.out.println(hdc.parseDate("christmas eve"));
		System.out.println(hdc.parseDate("boxing day"));
		System.out.println(hdc.parseDate("new year's day"));
		System.out.println(hdc.parseDate("new year's eve"));
		
	}
	
	
}
