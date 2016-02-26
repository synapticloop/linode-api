package synapticloop.linode.api.response;

import org.json.JSONObject;

public class StackscriptResponse extends BaseResponse {
	private Long stackscriptId = null;

	public StackscriptResponse(JSONObject jsonObject) {
		super(jsonObject);
		this.stackscriptId = jsonObject.getJSONObject("DATA").getLong("StackScriptID");
	}

	public Long getStackscriptId() {
		return this.stackscriptId;
	}
}
