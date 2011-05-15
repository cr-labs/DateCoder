package com.challengeandresponse.datecoder;

import org.joda.time.DateTime;


/**
 * Interface for all datecoders
 * @author jim
 *
 */
public interface DatecoderI {
	
	/**
	 * Given a string, parse it into a DateTime object (in the default time zone, if TZ is not a part of the string)
	 * @param dateString the text to attempt to parse into a date
	 * @return the parsed DatTime object, or null if parsing failed
	 */
	public DateTime parseDate(String dateString);
	
	
	/** 
	 * @return the version number of this Datecoder
	 */
	public String getVersion();

}
