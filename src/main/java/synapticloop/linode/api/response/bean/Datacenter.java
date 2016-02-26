package synapticloop.linode.api.response.bean;

import java.util.logging.Logger;

import org.json.JSONObject;

import synapticloop.linode.api.helper.ResponseHelper;

public class Datacenter {
	private static final Logger LOGGER = Logger.getLogger(Datacenter.class.getName());

	private static final String JSON_KEY_ABBREVIATION = "ABBR";
	private static final String JSON_KEY_DATACENTREID = "DATACENTERID";
	private static final String JSON_KEY_LOCATION = "LOCATION";

	private Long datacenterId = null;
	private String location = null;
	private String abbreviation = null;

	/**
	 *       {
	 *          "DATACENTERID":2,
	 *          "LOCATION":"Dallas, TX, USA",
	 *          "ABBR":"dallas"
	 *       },
	 * 
	 * @param jsonObject
	 */
	public Datacenter(JSONObject jsonObject) {
		this.datacenterId = jsonObject.getLong(JSON_KEY_DATACENTREID);
		jsonObject.remove(JSON_KEY_DATACENTREID);

		this.location = jsonObject.getString(JSON_KEY_LOCATION);
		jsonObject.remove(JSON_KEY_LOCATION);

		this.abbreviation = jsonObject.getString(JSON_KEY_ABBREVIATION);
		jsonObject.remove(JSON_KEY_ABBREVIATION);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getDatacenterId() {
		return this.datacenterId;
	}

	public String getLocation() {
		return this.location;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}
}
