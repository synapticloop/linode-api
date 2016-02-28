package synapticloop.linode.api.response.bean;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class ApiError {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiError.class);

	private Integer errorCode = null;
	private String errorMessage = null;

	public ApiError(JSONObject jsonObject) {
		this.errorCode = jsonObject.getInt("ERRORCODE");
		jsonObject.remove("ERRORCODE");
		this.errorMessage = jsonObject.getString("ERRORMESSAGE");
		jsonObject.remove("ERRORMESSAGE");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Integer getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}
}
