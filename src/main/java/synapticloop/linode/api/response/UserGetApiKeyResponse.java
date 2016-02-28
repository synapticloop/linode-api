package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class UserGetApiKeyResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserGetApiKeyResponse.class);

	private String username = null;
	private String apiKey = null;

	public UserGetApiKeyResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			this.username = dataObject.getString("USERNAME");
			dataObject.remove("USERNAME");
			this.apiKey = dataObject.getString("API_KEY");
			dataObject.remove("API_KEY");
			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public String getUsername() {
		return this.username;
	}

	public String getApiKey() {
		return this.apiKey;
	}

}
