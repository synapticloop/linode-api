package synapticloop.linode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.linode.exception.ApiException;

/**
 * Response from an Linode API call
 * 
 */
public class LinodeApiResponse {
	private static final String DATA = "DATA";
	private static final String ACTION = "ACTION";
	private static final String ERRORARRAY = "ERRORARRAY";

	private JSONObject json = null;

	/**
	 * @param json the JSON object returned from the HTTP call
	 */
	public LinodeApiResponse(JSONObject json) {
		this.json = json;
	}

	/**
	 * Helper method to return whether the DATA object for this request is of type 
	 * JSONObject, false if there was an error or it is not a JSONObject
	 * 
	 * @return whether the DATA is a JSONObject
	 */
	public boolean getIsDataJSONObject() {
		try {
			return(json.get(DATA) instanceof JSONObject);
		} catch (JSONException jsonex) {
			return(false);
		}
	}

	/**
	 * Helper method to return whether the DATA object for this request is of type 
	 * JSONArray, false if there was an error or it is not a JSONArray
	 * 
	 * @return whether the DATA is a JSONArray
	 */
	public boolean getIsDataJSONArray() {
		try {
			return(json.get(DATA) instanceof JSONArray);
		} catch (JSONException jsonex) {
			return(false);
		}
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

	/**
	 * Return the JSON object in full for this response
	 * 
	 * @return the JSON response object
	 */
	public JSONObject getJSON() {
		return json;
	}
}
