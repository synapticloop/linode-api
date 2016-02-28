package synapticloop.linode.api.response.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class Method {
	private static final Logger LOGGER = LoggerFactory.getLogger(Method.class);

	private String name = null;
	private String description = null;
	private String errors = null;
	private Map<String, Parameter> parameters = new HashMap<String, Parameter>();

	/**
	 *    "account.info":{
	 *     "DESCRIPTION":"Shows information about your account such as
	 *      the date your account was opened as well as your network
	 *      utilization for the current month in gigabytes.",
	 *      "PARAMETERS":{},
	 *      "THROWS":""
	 *     },
	 * 
	 * @param jsonObject
	 */
	public Method(String name, JSONObject jsonObject) {
		this.name = name;

		this.description = jsonObject.getString("DESCRIPTION");
		jsonObject.remove("DESCRIPTION");

		this.errors = jsonObject.getString("THROWS");
		jsonObject.remove("THROWS");

		JSONObject parametersObject = jsonObject.getJSONObject("PARAMETERS");
		Iterator<String> keys = parametersObject.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			parameters.put(key, new Parameter(parametersObject.getJSONObject(key)));
		}
		jsonObject.remove("PARAMETERS");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getErrors() {
		return this.errors;
	}

	public Map<String, Parameter> getParameters() {
		return this.parameters;
	}
}
