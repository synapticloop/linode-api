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

public class Datacenter extends BaseLinodeBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(Datacenter.class);

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
	 * @param jsonObject the json object to extract the data from
	 */
	public Datacenter(JSONObject jsonObject) {
		this.datacenterId = readLong(jsonObject, JSON_KEY_DATACENTERID);
		this.location = readString(jsonObject, JSON_KEY_LOCATION);
		this.abbreviation = readString(jsonObject, JSON_KEY_ABBREVIATION);

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
