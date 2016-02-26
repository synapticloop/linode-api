package synapticloop.linode.api.response.bean;

import org.json.JSONObject;

public class ApiError {
	private Integer errorCode = null;
	private String errorMessage = null;

	public ApiError(JSONObject jsonObject) {
		this.errorCode = jsonObject.getInt("ERRORCODE");
		this.errorMessage = jsonObject.getString("ERRORMESSAGE");
	}

	public Integer getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}
}
