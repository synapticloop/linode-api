
package synapticloop.linode.api.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class BaseResponse {
	private static final Logger LOGGER = Logger.getLogger(BaseResponse.class.getName());

	private static final String JSON_KEY_ACTION = "ACTION";
	private static final String JSON_KEY_ERRORARRAY = "ERRORARRAY";
	// "2014-10-08 09:39:07.0"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

	protected List<String> errors = new ArrayList<String>();
	protected String action = null;

	protected BaseResponse(JSONObject jsonObject) {
		JSONArray errorArray = jsonObject.getJSONArray(JSON_KEY_ERRORARRAY);
		Iterator<Object> errorIterator = errorArray.iterator();
		while (errorIterator.hasNext()) {
			errors.add((String) errorIterator.next());
		}
		this.action = jsonObject.getString(JSON_KEY_ACTION);
	}

	public static Date convertDate(String dateString) {
		if(null == dateString || dateString.trim().length() == 0) {
			return(null);
		} else {
			try {
				return(SIMPLE_DATE_FORMAT.parse(dateString));
			} catch (ParseException ex) {
				LOGGER.severe("Could not parse date, exception was: " + ex.getMessage());
			}
		}
		return(null);
	}

	public String getAction() {
		return(this.action);
	}

	public boolean hasErrors() {
		return(!this.errors.isEmpty());
	}

	public List<String> getErrors() {
		return(this.errors);
	}

}
