package synapticloop.linode.api.response.bean;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class ApiError {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiError.class);

	private Integer errorCode = null;
	private String errorMessage = null;

	/**
	 * Instantiate an APIError object by extracting the relevant data from the
	 * json object.
	 * 
	 * @param jsonObject the json object to extract the data from
	 */
	public ApiError(JSONObject jsonObject) {
		this.errorCode = jsonObject.getInt("ERRORCODE");
		jsonObject.remove("ERRORCODE");
		this.errorMessage = jsonObject.getString("ERRORMESSAGE");
		jsonObject.remove("ERRORMESSAGE");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	/**
	 * return the error code associated with the API
	 * 
	 * @return the error code
	 */
	public Integer getErrorCode() {
		return this.errorCode;
	}

	/**
	 * Return the human readable error message for this error
	 * 
	 * @return the human readable error message for this error
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
}
