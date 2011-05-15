package com.challengeandresponse.datecoder;

import java.util.Iterator;
import java.util.Vector;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This datecoder does MM/DD/YYYY style encodings, where month comes first (American-style)
 * 
 * @author jim
 */
public class MMDDYYDatecoder implements DatecoderI {
	public static final String	PRODUCT_SHORT 	= "MMDDYYDatecoder";
	public static final String 	PRODUCT_LONG	= "Challenge/Response MMDDYY Datecoder";
	public static final String	VERSION_SHORT	= "0.20";
	public static final String	VERSION_LONG 	=  PRODUCT_LONG + " " + VERSION_SHORT;
	public static final String	COPYRIGHT		= "Copyright (c) 2007 Challenge/Response, LLC, Cambridge, MA";

	private static final Vector <DateTimeFormatter> formattersWithYear;
	private static final Vector <DateTimeFormatter> formattersWithoutYear;	
	static {
		formattersWithYear = new Vector <DateTimeFormatter> ();
		// the patterns this one can handle - WITH YEAR
		formattersWithYear.add(DateTimeFormat.forPattern("MM/dd/yy"));
		formattersWithYear.add(DateTimeFormat.forPattern("MM/dd/yyyy"));
		// patterns WITHOUT YEAR - the year will be forced to "this year" if one of these matches
		formattersWithoutYear = new Vector <DateTimeFormatter> ();
		formattersWithoutYear.add(DateTimeFormat.forPattern("MM/dd"));
	}
	
	
	
	/**
	 * Basic constructor
	 */
	public MMDDYYDatecoder() {
	}

	/**
	 * Return the version number - required by the interface
	 */
	public String getVersion() {
		return VERSION_SHORT;
	}


	/**
	 * Given a string, attempt to match it to a pattern and return the encoded DateTime
	 */
	public DateTime parseDate(String dateString) {
		DateTime result = null;
		Iterator <DateTimeFormatter> it;
		// try formatters that have a year in them first
		it = formattersWithYear.iterator();
		while (it.hasNext()) {
			DateTimeFormatter dtf = it.next();
			try {
				if ((result = dtf.parseDateTime(dateString)) != null)
					break;
			}
			catch (Exception e) {
			}
		}
		// set time to 00:00:00 if a date was found
		if (result != null)
			return result.withTime(0,0,0,0);
		
		// if it fell through, try formatters that have NO year in them (then make the year "this year")
		it = formattersWithoutYear.iterator();
		while (it.hasNext()) {
			DateTimeFormatter dtf = it.next();
			try {
				if ((result = dtf.parseDateTime(dateString)) != null) {
					return result.withYear(new DateTime().getYear());
				}
			}
			catch (Exception e) {
			}
		}
		// set time to 00:00:00 if a date was found
		if (result != null)
			return result.withTime(0,0,0,0);
		else // nothing matched
			return null;
	}

	
	// TESTING
	public static void main(String[] args) {
		MMDDYYDatecoder dc = new MMDDYYDatecoder();
		System.out.println(dc.parseDate("12/30/06"));
		System.out.println(dc.parseDate("6/30/2007"));
		System.out.println(dc.parseDate("13/30"));
		
	}
	
	
}


