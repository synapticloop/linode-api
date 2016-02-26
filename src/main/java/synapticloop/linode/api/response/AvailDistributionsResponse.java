package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.linode.api.response.bean.Distribution;

public class AvailDistributionsResponse extends BaseResponse {
	private List<Distribution> distributions = new ArrayList<Distribution>();
	private Map<Long, Distribution> distributionIdLookup = new HashMap<Long, Distribution>();

	/**
	 * 
	 * The json should have been formed by the following:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"avail.distributions",
	 *    "DATA":[
	 *       {
	 *          "IS64BIT":0,
	 *          "LABEL":"Debian 4.0",
	 *          "MINIMAGESIZE":200,
	 *          "DISTRIBUTIONID":28,
	 *          "CREATE_DT":"2007-04-18 00:00:00.0",
	 *          "REQUIRESPVOPSKERNEL":0
	 *       }
	 *    ]
	 * }
	 * </pre>
	 * 
	 * @param jsonObject
	 */
	public AvailDistributionsResponse(JSONObject jsonObject) {
		super(jsonObject);
		JSONArray dataArray = jsonObject.getJSONArray("DATA");

		for (Object distributionObject : dataArray) {
			Distribution distribution = new Distribution((JSONObject)distributionObject);
			distributions.add(distribution);
			distributionIdLookup.put(distribution.getDistributionId(), distribution);
		}
	}

	public List<Distribution> getDistributions() {
		return(distributions);
	}

	public Distribution getDistributionById(Long id) {
		return(distributionIdLookup.get(id));
	}
}
