package com.challengeandresponse.datecoder;

import java.util.Iterator;
import java.util.Vector;

import org.joda.time.DateTime;

/**
 * Stackable date recognition modules for parsing strings into dates
 * @author jim
 *
 */

/*
 * REVISION HISTORY
 * 0.10 2007-02-06 Created
 */


public class Datecoder implements DatecoderI {
	public static final String	PRODUCT_SHORT 	= "Datecoder";
	public static final String 	PRODUCT_LONG	= "Challenge/Response Datecoder";
	public static final String	VERSION_SHORT	= "0.10";
	public static final String	VERSION_LONG 	=  PRODUCT_LONG + " " + VERSION_SHORT;
	public static final String	COPYRIGHT		= "Copyright (c) 2007 Challenge/Response, LLC, Cambridge, MA";
	
	private Vector <DatecoderI> datecoders;
	
	public Datecoder() {
		datecoders = new Vector <DatecoderI> ();
	}
	
	
	public DateTime parseDate(String testDate) {
		DateTime result;
		Iterator <DatecoderI> it = datecoders.iterator();
 		while (it.hasNext()) {
			DatecoderI dc = it.next();
			if ((result = dc.parseDate(testDate)) != null)
				return result;
		}
		// fell through - no success - return null
		return null;
	}

	
	public String getVersion() {
		return VERSION_SHORT;
	}


	/**
	 * Add a dateccoder to the end of call stack. Coders are called in order until one
	 * of them returns a non-null result
	 * @param datecoder the datecoder to add to the end of the call stack
	 */
	public void addDatecoder(DatecoderI datecoder) {
		datecoders.add(datecoder);
	}
	
	
	/**
	 * Add a datecoder to the call stack at a given position.
	 * @param datecoder the datecoder to add to the given position in the call stac
	 * @param position add the datecoder at 'position' in the stack. If position is > the size of the stack, the datecoder is added to the end
	 * @throws DatecoderException if position is < 0, or datecoder is null
	 */
	public void addDatecoder(DatecoderI datecoder, int position)
	throws DatecoderException {
		if ((position < 0) || (datecoder == null))
			throw new DatecoderException("Invalid parameter");
		datecoders.add(Math.min(datecoders.size(),position), datecoder);
	}


	
}
