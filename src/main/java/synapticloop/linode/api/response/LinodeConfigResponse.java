package synapticloop.linode.api.response;

import org.json.JSONObject;

public class LinodeConfigResponse extends BaseResponse {
	private Long configId = null;

	public LinodeConfigResponse(JSONObject jsonObject) {
		super(jsonObject);
		this.configId = jsonObject.getJSONObject("DATA").getLong("ConfigID");
	}

	public Long getConfigId() {
		return this.configId;
	}
}
