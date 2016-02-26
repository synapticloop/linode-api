package synapticloop.linode.api.response;

import org.json.JSONObject;

public class UserGetApiKeyResponse extends BaseResponse {
	private String username = null;
	private String apiKey = null;

	public UserGetApiKeyResponse(JSONObject jsonObject) {
		super(jsonObject);
		this.username = jsonObject.getJSONObject("DATA").getString("USERNAME");
		this.apiKey = jsonObject.getJSONObject("DATA").getString("API_KEY");
	}

	public String getUsername() {
		return this.username;
	}

	public String getApiKey() {
		return this.apiKey;
	}

}
