package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.NodeBalancerNode;

public class NodebalancerNodeListResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodebalancerNodeListResponse.class);

	List<NodeBalancerNode> nodeBalancerNodes = new ArrayList<NodeBalancerNode>();

	public NodebalancerNodeListResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray(JSON_KEY_DATA);
			for (Object object : dataArray) {
				nodeBalancerNodes.add(new NodeBalancerNode((JSONObject)object));
			}
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<NodeBalancerNode> getNodeBalancerNodes() {
		return this.nodeBalancerNodes;
	}

}
