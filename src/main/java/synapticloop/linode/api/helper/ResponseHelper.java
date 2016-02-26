package synapticloop.linode.api.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

public class ResponseHelper {
	private static final Logger LOGGER = Logger.getLogger(ResponseHelper.class.getName());

	// "2014-10-08 09:39:07.0"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	// "2014-10-08 09:39:07"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT_SHORTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Warn if there are still remaining keys on a JSONObject
	 * 
	 * @param logger The Logger to be used
	 * @param jsonObject the jsonObject to parse
	 */
	public static void warnOnMissedKeys(Logger logger, JSONObject jsonObject) {
		if(logger.isLoggable(Level.WARNING)) {
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				logger.warning("Found an unexpected json key of '" + key + "', this is not mapped to a field...");
			}
		}
	}

	public static Date convertDate(String dateString) {
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
				LOGGER.severe("Could not parse date, exception was: " + ex.getMessage());
			}
		}
		return(null);
	}
}
