package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class StackscriptResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(StackscriptResponse.class);

	private Long stackscriptId = null;

	public StackscriptResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			this.stackscriptId = dataObject.getLong("StackScriptID");
			dataObject.remove("StackScriptID");
			
			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getStackscriptId() {
		return this.stackscriptId;
	}
}
