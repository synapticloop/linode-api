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

public class NodeBalancer extends BaseJsonReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodeBalancer.class);

	private Long nodebalancerId = null;
	private String label = null;
	private Long datacenterId = null;
	private String hostname = null;
	private String ipAddressIPv4 = null;
	private String ipAddressIPv6 = null;
	private Long clientConnectionThrottle = null;

	/**
	 *       {
         "NODEBALANCERID":53,
         "LABEL":"awesomebal",
         "DATACENTERID":6,
         "HOSTNAME":"nb-69-164-223-4.newark.nodebalancer.linode.com",
         "ADDRESS4":"69.164.223.4",
         "ADDRESS6":"2600:3c03:1::45a4:df04",
         "CLIENTCONNTHROTTLE":4203
      }

	 * @param jsonObject the json object to extract the data from
	 */
	public NodeBalancer(JSONObject jsonObject) {
		this.nodebalancerId = readLong(jsonObject, JSON_KEY_NODEBALANCERID);
		this.label = readString(jsonObject, JSON_KEY_LABEL_UPPER);
		this.datacenterId = readLong(jsonObject, JSON_KEY_DATACENTERID);
		this.hostname = readString(jsonObject, JSON_KEY_HOSTNAME);
		this.ipAddressIPv4 = readString(jsonObject, JSON_KEY_ADDRESS4);
		this.ipAddressIPv6 = readString(jsonObject, JSON_KEY_ADDRESS6);
		this.clientConnectionThrottle = readLong(jsonObject, JSON_KEY_CLIENTCONNTHROTTLE);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getNodebalancerId() {
		return this.nodebalancerId;
	}

	public String getLabel() {
		return this.label;
	}

	public Long getDatacenterId() {
		return this.datacenterId;
	}

	public String getHostname() {
		return this.hostname;
	}

	public String getIpAddressIPv4() {
		return this.ipAddressIPv4;
	}

	public String getIpAddressIPv6() {
		return this.ipAddressIPv6;
	}

	public Long getClientConnectionThrottle() {
		return this.clientConnectionThrottle;
	}

}
