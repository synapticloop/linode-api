package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class NodebalancerResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodebalancerResponse.class);

	private Long nodebalancerId = null;

	public NodebalancerResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject("DATA");
			this.nodebalancerId = dataObject.getLong("NodeBalancerID");
			dataObject.remove("NodeBalancerID");
			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}
		jsonObject.remove("DATA");
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getNodebalancerId() {
		return this.nodebalancerId;
	}
}
