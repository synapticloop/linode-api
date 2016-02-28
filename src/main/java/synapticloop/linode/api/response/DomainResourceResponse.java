package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class DomainResourceResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(DomainResourceResponse.class);
	private Long resourceId = null;

	public DomainResourceResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
		this.resourceId = jsonObject.getJSONObject(JSON_KEY_DATA).getLong("ResourceId");
		}
		
		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getResourceId() {
		return this.resourceId;
	}
}
