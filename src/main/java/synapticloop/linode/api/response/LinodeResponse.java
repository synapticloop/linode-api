package synapticloop.linode.api.response;

import org.json.JSONObject;

public class LinodeResponse extends BaseResponse {
	private Long linodeId = null;

	public LinodeResponse(JSONObject jsonObject) {
		super(jsonObject);
		this.linodeId = jsonObject.getJSONObject("DATA").getLong("LinodeID");
	}

	public Long getLinodeId() {
		return this.linodeId;
	}
}
