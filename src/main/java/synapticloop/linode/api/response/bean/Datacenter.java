package synapticloop.linode.api.response.bean;

import org.json.JSONObject;

public class Datacenter {
	private static final String JSON_KEY_ABBREVIATION = "ABBR";
	private static final String JSON_KEY_DATACENTREID = "DATACENTERID";
	private static final String JSON_KEY_LOCATION = "LOCATION";

	private Long id = null;
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
		this.id = jsonObject.getLong(JSON_KEY_DATACENTREID);
		this.location = jsonObject.getString(JSON_KEY_LOCATION);
		this.abbreviation = jsonObject.getString(JSON_KEY_ABBREVIATION);
	}

	public Long getId() {
		return this.id;
	}

	public String getLocation() {
		return this.location;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}
}
