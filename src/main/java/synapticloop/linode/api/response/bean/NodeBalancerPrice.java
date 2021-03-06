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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.BaseJsonReader;

public class NodeBalancerPrice extends BaseJsonReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodeBalancerPrice.class);

	private Double priceMonthly = null;
	private Double priceHourly = null;
	private Long numConnections = null;

	/**
	 * {
	 *         "MONTHLY": 20.00,
	 *         "HOURLY": 0.0300,
	 *         "CONNECTIONS": 10000
	 *  }
	 * @param jsonObject the json object to extract the data from
	 */
	public NodeBalancerPrice(JSONObject jsonObject) {
		this.priceMonthly = readDouble(jsonObject, JSON_KEY_HOURLY);
		this.priceHourly = readDouble(jsonObject, JSON_KEY_MONTHLY);
		this.numConnections = readLong(jsonObject, JSON_KEY_CONNECTIONS);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Double getPriceMonthly() {
		return this.priceMonthly;
	}

	public Double getPriceHourly() {
		return this.priceHourly;
	}

	public Long getNumConnections() {
		return this.numConnections;
	}
}
