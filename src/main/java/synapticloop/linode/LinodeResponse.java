package synapticloop.linode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.linode.exception.ApiException;

/**
 * Response from an Linode API call
 * 
 */
public class LinodeResponse {
	private JSONObject json = null;

	private static final String DATA = "DATA";
	private static final String ACTION = "ACTION";
	private static final String ERRORARRAY = "ERRORARRAY";

	/**
	 * @param json the json object returned from the http call
	 */
	public LinodeResponse(JSONObject json) {
		this.json = json;
	}

	/**
	 * Data as JSONObject
	 * 
	 * @return JSONObject the data as a JSONObject
	 * 
	 * @throws ApiException if there was an error parsing the JSONObject
	 */
	public JSONObject getDataAsJSONObject() throws ApiException {
		try {
			return json.getJSONObject(DATA);
		} catch (JSONException ex) {
			throw new ApiException("Could not parse JSON.", ex);
		}
	}

	/**
	 * Data as JSONArray
	 * 
	 * @return JSONArray the data as a JSON array
	 *
	 * @throws ApiException if there was an error parsing the JSONObject
	 */
	public JSONArray getDataAsJSONArray() throws ApiException {
		try {
			return json.getJSONArray(DATA);
		} catch (JSONException ex) {
			throw new ApiException("Could not parse JSON.", ex);
		}
	}

	/**
	 * Action value
	 * 
	 * @return the action that was performed for the request
	 * 
	 * @throws ApiException if there was an error parsing the JSONObject
	 */
	public String getAction() throws ApiException {
		try {
			return json.getString(ACTION);
		} catch (JSONException ex) {
			throw new ApiException("Could not parse JSON.", ex);
		}
	}

	/**
	 * returns JSONArray of errors
	 * 
	 * @return JSONArray of errors
	 * 
	 * @throws ApiException if there was an error parsing the JSONObject
	 */
	public JSONArray getErrorArray() throws ApiException {
		try {
			return json.getJSONArray(ERRORARRAY);
		} catch (JSONException ex) {
			throw new ApiException("Could not parse JSON.", ex);
		}
	}

	public JSONObject getJSON() {
		return json;
	}
}
