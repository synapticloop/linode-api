package synapticloop.linode.api.response.bean;

/*
 * Copyright (c) 2016 Synapticloop.
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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class Parameter {
	private static final Logger LOGGER = LoggerFactory.getLogger(Parameter.class);

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
	 * @param jsonObject the json object to extract the data from
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

		// we are removing the default value - but not adding it as the type varies
		jsonObject.remove("default");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);

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
