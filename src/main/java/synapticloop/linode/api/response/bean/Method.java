package synapticloop.linode.api.response.bean;

/*
 * Copyright (c) 2016-2017 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.BaseJsonReader;

public class Method extends BaseJsonReader {
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
	 * @param name the name of the method
	 * @param jsonObject the json object to extract the data from
	 */
	public Method(String name, JSONObject jsonObject) {
		this.name = name;

		this.description = readString(jsonObject, JSON_KEY_DESCRIPTION);

		this.errors = readString(jsonObject, JSON_KEY_THROWS);

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
