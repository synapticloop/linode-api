package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.linode.api.response.bean.NodeBalancer;

public class AvailNodeBalancersResponse extends BaseResponse {
	private List<NodeBalancer> nodeBalancers = new ArrayList<NodeBalancer>();

	public AvailNodeBalancersResponse(JSONObject jsonObject) {
		super(jsonObject);
		JSONArray dataArray = jsonObject.getJSONArray("DATA");
		for (Object object : dataArray) {
			nodeBalancers.add(new NodeBalancer((JSONObject)object));
		}
	}

}
