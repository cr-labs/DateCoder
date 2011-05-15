package com.challengeandresponse.datecoder;

import java.util.Iterator;
import java.util.Vector;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This datecoder does DD/MM/YYYY style encodings, where day of month comes first (European-style)
 * 
 * @author jim
 */
public class DDMMYYDatecoder implements DatecoderI {
	public static final String	PRODUCT_SHORT 	= "DDMMYYDatecoder";
	public static final String 	PRODUCT_LONG	= "Challenge/Response DDMMYY Datecoder";
	public static final String	VERSION_SHORT	= "0.20";
	public static final String	VERSION_LONG 	=  PRODUCT_LONG + " " + VERSION_SHORT;
	public static final String	COPYRIGHT		= "Copyright (c) 2007 Challenge/Response, LLC, Cambridge, MA";

	private static final Vector <DateTimeFormatter> formattersWithYear;
	private static final Vector <DateTimeFormatter> formattersWithoutYear;	
	static {
		formattersWithYear = new Vector <DateTimeFormatter> ();
		// the patterns this one can handle - WITH YEAR
		formattersWithYear.add(DateTimeFormat.forPattern("dd/MM/yy"));
		formattersWithYear.add(DateTimeFormat.forPattern("dd/MM/yyyy"));
		// patterns WITHOUT YEAR - the year will be forced to "this year" if one of these matches
		formattersWithoutYear = new Vector <DateTimeFormatter> ();
		formattersWithoutYear.add(DateTimeFormat.forPattern("dd/MM"));
	}
	
	
	
	/**
	 * Basic constructor
	 */
	public DDMMYYDatecoder() {
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
					result = result.withYear(new DateTime().getYear());
					break;
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
		DDMMYYDatecoder dc = new DDMMYYDatecoder();
		System.out.println(dc.parseDate("30/12/06"));
		System.out.println(dc.parseDate("30/6/2007"));
		System.out.println(dc.parseDate("30/13"));
	}
	
	
}


