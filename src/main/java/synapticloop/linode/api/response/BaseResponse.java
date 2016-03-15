
package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.response.bean.ApiError;

public abstract class BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseResponse.class);

	private static final String JSON_KEY_ACTION = "ACTION";
	protected static final String JSON_KEY_DATA = "DATA";
	protected static final String JSON_KEY_ERRORARRAY = "ERRORARRAY";
	protected static final String JSON_KEY_VERSION = "VERSION";
	protected static final String JSON_KEY_METHODS = "METHODS";
	protected static final String JSON_KEY_INVOICE_TO = "INVOICE_TO";
	protected static final String JSON_KEY_AMOUNT = "AMOUNT";

	private final JSONObject jsonObject;

	protected List<ApiError> errors = new ArrayList<ApiError>();
	protected String action = null;

	public BaseResponse(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
		parse(jsonObject, false);
	}

	public BaseResponse(JSONObject jsonObject, boolean ignoreErrorArray) {
		this.jsonObject = jsonObject;
		parse(jsonObject, true);
	}

	private void parse(JSONObject jsonObject, boolean ignoreErrorArray) {
		if(!ignoreErrorArray) {
			JSONArray errorArray = jsonObject.getJSONArray(JSON_KEY_ERRORARRAY);
	
			for (Object object : errorArray) {
				errors.add(new ApiError((JSONObject)object));
			}
	
			jsonObject.remove(JSON_KEY_ERRORARRAY);
		}

		this.action = jsonObject.getString(JSON_KEY_ACTION);
		jsonObject.remove(JSON_KEY_ACTION);
	}

	/**
	 * Read and remove String with key from JSON
	 * 
	 * @param key the key to read as a string and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected String readString(String key) {
		return this.readString(jsonObject, key);
	}

	/**
	 * Read and remove String with key from JSON object
	 * 
	 * @param response The JSON object to read from
	 * @param key the key to read as a string and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected String readString(JSONObject response, String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			LOGGER.warn("No field for key {}", key);
			return null;
		}
		return value.toString();
	}

	/**
	 * Read and remove int with key from JSON object
	 * 
	 * @param key the key to read as an int and remove
	 * 
	 * @return the read key (or -1 if it doesn't exist)
	 */
	protected int readInt(String key) {
		final Object value = jsonObject.remove(key);
		if (null == value || JSONObject.NULL == value) {
			LOGGER.warn("No field for key {}", key);
			return -1;
		}
		return value instanceof Number ? ((Number) value).intValue() : Integer.parseInt(value.toString());
	}

	/**
	 * Read and remove long with key from JSON object
	 * 
	 * @param key the key to read as a long and remove
	 * 
	 * @return the read key (or -1L if it doesn't exist)
	 */
	protected long readLong(String key) {
		final Object value = jsonObject.remove(key);
		if (null == value || JSONObject.NULL == value) {
			LOGGER.warn("No field for key {}", key);
			return -1L;
		}
		return value instanceof Number ? ((Number) value).longValue() : Long.parseLong(value.toString());
	}

	/**
	 * Read and remove JSONObject with key from JSON object
	 * 
	 * @param key the key to read as a JSONObject and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected JSONObject readObject(String key) {
		return this.readObject(jsonObject, key);
	}

	/**
	 * Read and remove JSONObject with key from JSON object
	 * 
	 * @param response The JSON object to read from
	 * @param key the key to read as a JSONObject and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected JSONObject readObject(JSONObject response, String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			LOGGER.warn("No field for key {}", key);
			return null;
		}
		return value instanceof JSONObject ? (JSONObject) value : null;
	}

	/**
	 * Read and remove JSONArray with key from JSON object
	 * 
	 * @param key the key to read as a JSONArray and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected JSONArray readObjects(String key) {
		final Object value = jsonObject.remove(key);
		if (null == value || JSONObject.NULL == value) {
			LOGGER.warn("No field for key {}", key);
			return null;
		}
		return value instanceof JSONArray ? (JSONArray) value : null;
	}


	/**
	 * Parse through the expected keys to determine whether any of the keys in
	 * the response will not be mapped.  This will loop through the JSON object
	 * and any key left in the object will generate a 'WARN' message.  The
	 * response class __MUST__ remove the object (i.e. jsonObject.remove(KEY_NAME))
	 * after getting the value, or use the utility methods in this class.  This 
	 * is used more as a testing tool/sanity test than anything else as there 
	 * are some instances in where keys are returned, however are not listed in 
	 * the documentation.
	 * 
	 * {@link BaseResponse#readInt(String)}
	 * {@link BaseResponse#readLong(String)}
	 * {@link BaseResponse#readString(String)}
	 * {@link BaseResponse#readObject(String)}
	 *
	 * @param LOGGER     The logger to use
	 */
	@SuppressWarnings("rawtypes")
	protected void warnOnMissedKeys(Logger LOGGER) {
		if (LOGGER.isWarnEnabled()) {
			Iterator keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				LOGGER.warn("Found an unexpected key of '{}' in JSON that is not mapped to a field.", key);
			}
		}
	}
	public String getAction() {
		return(this.action);
	}

	public boolean hasErrors() {
		return(!this.errors.isEmpty());
	}

	public List<ApiError> getErrors() {
		return(this.errors);
	}

}
