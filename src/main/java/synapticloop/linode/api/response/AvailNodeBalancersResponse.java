package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.NodeBalancer;

public class AvailNodeBalancersResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(AvailNodeBalancersResponse.class);

	private List<NodeBalancer> nodeBalancers = new ArrayList<NodeBalancer>();

	public AvailNodeBalancersResponse(JSONObject jsonObject) {
		super(jsonObject);
		
		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray("DATA");
			for (Object object : dataArray) {
				nodeBalancers.add(new NodeBalancer((JSONObject)object));
			}
		}

		jsonObject.remove("DATA");
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<NodeBalancer> getNodeBalancers() {
		return this.nodeBalancers;
	}

}
