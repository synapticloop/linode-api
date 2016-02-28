package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.NodeBalancerConfig;

public class NodebalancerConfigListResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodebalancerConfigListResponse.class);
	private List<NodeBalancerConfig> nodeBalancerConfigs = new ArrayList<NodeBalancerConfig>();

	public NodebalancerConfigListResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_DATA);
			for (Object object : jsonArray) {
				nodeBalancerConfigs.add(new NodeBalancerConfig((JSONObject)object));
			}
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<NodeBalancerConfig> getNodeBalancerConfigs() {
		return this.nodeBalancerConfigs;
	}

}
