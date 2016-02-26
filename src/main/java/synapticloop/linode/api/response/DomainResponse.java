package synapticloop.linode.api.response;

import org.json.JSONObject;

public class DomainResponse extends BaseResponse {
	private Long domainId = null;

	public DomainResponse(JSONObject jsonObject) {
		super(jsonObject);
		this.domainId = jsonObject.getJSONObject("DATA").getLong("DOMAINID");
	}

	public Long getDomainId() {
		return this.domainId;
	}
}
