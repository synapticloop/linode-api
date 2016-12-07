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

public class NodeBalancerPrice {
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
		this.priceMonthly = jsonObject.getDouble("HOURLY");
		jsonObject.remove("HOURLY");
		this.priceHourly = jsonObject.getDouble("MONTHLY");
		jsonObject.remove("MONTHLY");
		this.numConnections = jsonObject.getLong("CONNECTIONS");
		jsonObject.remove("CONNECTIONS");

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
