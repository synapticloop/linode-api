package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class NodebalancerNodeResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodebalancerNodeResponse.class);

	private Long nodeId = null;

	public NodebalancerNodeResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			this.nodeId = dataObject.getLong("NodeID");
			dataObject.remove("NodeID");
			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}
		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getNodeId() {
		return this.nodeId;
	}
}
