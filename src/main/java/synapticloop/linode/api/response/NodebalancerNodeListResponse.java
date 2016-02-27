package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Job;
import synapticloop.linode.api.response.bean.NodeBalancerNode;

public class NodebalancerNodeListResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(Job.class);

	List<NodeBalancerNode> nodeBalancerNodes = new ArrayList<NodeBalancerNode>();

	public NodebalancerNodeListResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray("DATA");
			for (Object object : dataArray) {
				nodeBalancerNodes.add(new NodeBalancerNode((JSONObject)object));
			}
		}

		jsonObject.remove("DATA");
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

}
