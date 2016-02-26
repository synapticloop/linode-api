package synapticloop.linode.api.response;

import org.json.JSONObject;

public class DomainResourceResponse extends BaseResponse {
	private Long resourceId = null;

	public DomainResourceResponse(JSONObject jsonObject) {
		super(jsonObject);
		this.resourceId = jsonObject.getJSONObject("DATA").getLong("ResourceId");
	}

	public Long getResourceId() {
		return this.resourceId;
	}
}
