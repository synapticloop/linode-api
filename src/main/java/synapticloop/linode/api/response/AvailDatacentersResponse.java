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
import synapticloop.linode.api.response.bean.Datacenter;

public class AvailDatacentersResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(AvailDatacentersResponse.class);

	private List<Datacenter> datacenters = new ArrayList<Datacenter>();
	private Map<Long, Datacenter> datacenterIdLookup = new HashMap<Long, Datacenter>();
	private Map<String, Datacenter> datacenterAbbreviationLookup = new HashMap<String, Datacenter>();

	/**
	 * 
	 * The json should have been formed by the following:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"avail.datacenters",
	 *    "DATA":[
	 *       {
	 *          "DATACENTERID":2,
	 *          "LOCATION":"Dallas, TX, USA",
	 *          "ABBR":"dallas"
	 *       },
	 *       {
	 *          "DATACENTERID":3,
	 *          "LOCATION":"Fremont, CA, USA",
	 *          "ABBR":"fremont"
	 *       },
	 *       {
	 *          "DATACENTERID":4,
	 *          "LOCATION":"Atlanta, GA, USA",
	 *          "ABBR":"atlanta"
	 *       },
	 *       {
	 *          "DATACENTERID":6,
	 *          "LOCATION":"Newark, NJ, USA",
	 *          "ABBR":"newark"
	 *       },
	 *       {
	 *          "DATACENTERID":7,
	 *          "LOCATION":"London, England, UK",
	 *          "ABBR":"london"
	 *       },
	 *       {
	 *          "DATACENTERID":8,
	 *          "LOCATION":"Tokyo, JP",
	 *          "ABBR":"tokyo"
	 *       },
	 *       {
	 *           "DATACENTERID":9,
	 *           "LOCATION":"Singapore, SG",
	 *           "ABBR":"singapore"
	 *       },
	 *       {
	 *           "DATACENTERID":10,
	 *           "LOCATION":"Frankfurt, DE",
	 *           "ABBR":"frankfurt"
	 *       }
	 *    ]
	 * }
	 * </pre>
	 * 
	 * @param jsonObject the json object to parse
	 */
	public AvailDatacentersResponse(JSONObject jsonObject) {
		super(jsonObject);
		
		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray(JSON_KEY_DATA);
	
			for (Object datacentreObject : dataArray) {
				Datacenter datacenter = new Datacenter((JSONObject)datacentreObject);
				datacenters.add(datacenter);
				datacenterIdLookup.put(datacenter.getDatacenterId(), datacenter);
				datacenterAbbreviationLookup.put(datacenter.getAbbreviation(), datacenter);
			}
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<Datacenter> getDatacenters() {
		return(datacenters);
	}

	public Datacenter getDatacenterById(Long id) {
		return(datacenterIdLookup.get(id));
	}

	public Datacenter getDatacenterByAbbreviation(String abbreviation) {
		return(datacenterAbbreviationLookup.get(abbreviation));
	}

}
