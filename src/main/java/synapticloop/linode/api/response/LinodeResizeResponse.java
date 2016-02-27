package synapticloop.linode.api.response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class LinodeResizeResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeResizeResponse.class);

	private Integer code = null;
	private String message = null;

	public LinodeResizeResponse(JSONObject jsonObject) {
		// we are ignoring the rror array as this returns the reponse
		super(jsonObject, true);

		JSONArray errorArray = jsonObject.getJSONArray("ERRORARRAY");
		
		for (Object object : errorArray) {
			this.code = ((JSONObject)object).getInt("ERRORCODE");
			this.message = ((JSONObject)object).getString("ERRORMESSAGE");
		}

		jsonObject.remove("ERRORARRAY");
		jsonObject.remove("DATA");
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Integer getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

}
