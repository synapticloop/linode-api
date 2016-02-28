package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class NodebalancerConfigResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodebalancerConfigResponse.class);

	private Long configId = null;

	public NodebalancerConfigResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			this.configId = dataObject.getLong("ConfigID");
			dataObject.remove("ConfigID");
			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}
		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getConfigId() {
		return this.configId;
	}
}
