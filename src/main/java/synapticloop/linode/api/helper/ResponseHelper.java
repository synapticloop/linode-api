package synapticloop.linode.api.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.exception.ApiException;

public class ResponseHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseHelper.class);

	// "2014-10-08 09:39:07.0"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	// "2014-10-08 09:39:07"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT_SHORTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Warn if there are still remaining keys on a JSONObject, this is used more
	 * through the testing phase to ensure that all passed in JSON keys are 
	 * correctly assigned
	 * 
	 * @param LOGGER The Logger to be used
	 * @param jsonObject the jsonObject to parse
	 */
	public static void warnOnMissedKeys(Logger LOGGER, JSONObject jsonObject) {
		if(LOGGER.isWarnEnabled()) {
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				LOGGER.warn("Found an unexpected json key of '{}', this is not mapped to a field...", key);
			}
		}
	}

	/**
	 * Convert a date string to a java.util.Date (or null if the passed in string
	 * is null or empty)
	 * 
	 * @param dateString the date formatted as a string
	 * 
	 * @return the parsed date or null if it is an empty string
	 * 
	 * @throws ApiException if there was an error parsing the date
	 */
	public static Date convertDate(String dateString) throws ApiException {
		if(null == dateString || dateString.trim().length() == 0) {
			return(null);
		} else {
			try {
				if(dateString.length() == 19) {
					return(SIMPLE_DATE_FORMAT_SHORTER.parse(dateString));
				} else {
					return(SIMPLE_DATE_FORMAT.parse(dateString));
				}
			} catch (ParseException ex) {
				throw new ApiException("Could not parse the date '" + dateString + "', exception message was: " + ex.getMessage());
			}
		}
	}
}
