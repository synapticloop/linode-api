package synapticloop.linode.api.response.bean;

import java.util.logging.Logger;

import org.json.JSONObject;

import synapticloop.linode.api.response.BaseResponse;

public class Parameter {
	private static final Logger LOGGER = Logger.getLogger(Parameter.class.getName());

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
		jsonObject.remove("NAME");

		this.description = jsonObject.getString("DESCRIPTION");
		jsonObject.remove("DESCRIPTION");

		this.required = jsonObject.getBoolean("REQUIRED");
		jsonObject.remove("REQUIRED");

		this.type = jsonObject.getString("TYPE");
		jsonObject.remove("TYPE");

		// we are removing the default value - but not addigning it
		jsonObject.remove("default");

		BaseResponse.warnOnMissedKeys(LOGGER, jsonObject);

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
