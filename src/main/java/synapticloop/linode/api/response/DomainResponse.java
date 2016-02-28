package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class DomainResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(DomainResponse.class);
	private Long domainId = null;

	public DomainResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
		this.domainId = jsonObject.getJSONObject(JSON_KEY_DATA).getLong("DOMAINID");
		}
		
		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getDomainId() {
		return this.domainId;
	}
}
