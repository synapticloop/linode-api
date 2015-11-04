package synapticloop.linode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response from an Linode API call
 * 
 * @author theodore nguyen-cao
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
	 * @throws JSONException if there was an error parsing the JSONObject
	 */
	public JSONObject getDataAsJSONObject() throws JSONException {
		return json.getJSONObject(DATA);
	}

	/**
	 * Data as JSONArray
	 * 
	 * @return JSONArray the data as a JSON array
	 *
	 * @throws JSONException if there was an error parsing the JSONObject
	 */
	public JSONArray getDataAsJSONArray() throws JSONException {
		return json.getJSONArray(DATA);
	}

	/**
	 * Action value
	 * 
	 * @return the action that was performed for the request
	 * 
	 * @throws JSONException if there was an error parsing the JSONObject
	 */
	public String getAction() throws JSONException {
		return json.getString(ACTION);
	}

	/**
	 * returns JSONArray of errors
	 * 
	 * @return JSONArray of errors
	 * 
	 * @throws JSONException if there was an error parsing the JSONObject
	 */
	public JSONArray getErrorArray() throws JSONException {
		return json.getJSONArray(ERRORARRAY);
	}

	public JSONObject getJSON() {
		return json;
	}
}
