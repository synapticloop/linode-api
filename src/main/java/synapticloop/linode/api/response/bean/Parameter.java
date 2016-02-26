package synapticloop.linode.api.response.bean;

import org.json.JSONObject;

public class Parameter {
	private String name = null;
	private String description = null;
	private boolean required = false;
	private String type = null;

	/**
	 * {
	 *    "NAME": "LinodeID",
	 *    "DESCRIPTION": "",
	 *    "REQUIRED": true,
	 *    "TYPE": "numeric"
	 *  }
	 * @param jsonObject
	 */
	public Parameter(JSONObject jsonObject) {
		this.name = jsonObject.getString("NAME");
		this.description = jsonObject.getString("DESCRIPTION");
		this.required = jsonObject.getBoolean("REQUIRED");
		this.type = jsonObject.getString("TYPE");
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public boolean getRequired() {
		return this.required;
	}

	public String getType() {
		return this.type;
	}

}
