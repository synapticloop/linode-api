package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Distribution;
import synapticloop.linode.exception.ApiException;

public class AvailDistributionsResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(AvailDistributionsResponse.class);

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
	public AvailDistributionsResponse(JSONObject jsonObject) throws ApiException {
		super(jsonObject);

		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray(JSON_KEY_DATA);
			for (Object distributionObject : dataArray) {
				Distribution distribution = new Distribution((JSONObject)distributionObject);
				distributions.add(distribution);
				distributionIdLookup.put(distribution.getDistributionId(), distribution);
			}
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<Distribution> getDistributions() {
		return(distributions);
	}

	public Distribution getDistributionById(Long id) {
		return(distributionIdLookup.get(id));
	}
}
