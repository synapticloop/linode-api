package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.linode.api.response.bean.LinodePlan;

public class AvailLinodePlansResponse extends BaseResponse {
	private List<LinodePlan> linodePlans = new ArrayList<LinodePlan>();
	private Map<Long, LinodePlan> linodePlanLookup = new HashMap<Long, LinodePlan>();

	public AvailLinodePlansResponse(JSONObject jsonObject) {
		super(jsonObject);

		JSONArray jsonArray = jsonObject.getJSONArray("DATA");
		for (Object object : jsonArray) {
			LinodePlan linodePlan = new LinodePlan((JSONObject)object);
			linodePlans.add(linodePlan);
			linodePlanLookup.put(linodePlan.getPlanId(), linodePlan);
		}
	}

	public List<LinodePlan> getLinodePlans() {
		return this.linodePlans;
	}

	public LinodePlan getLinodePlanById(Long planId) {
		return(linodePlanLookup.get(planId));
	}
}
