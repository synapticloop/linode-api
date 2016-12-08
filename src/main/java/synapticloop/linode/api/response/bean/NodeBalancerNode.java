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
import synapticloop.linode.api.response.BaseJsonReader;

public class NodeBalancerNode extends BaseJsonReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodeBalancerNode.class);
	
	private Integer weight = null;
	private String address = null;
	private String label = null;
	private Long nodeId = null;
	private String mode = null;
	private Long configId = null;
	private String status = null;
	private Long nodeBalancerId = null;

	/**
      {
         WEIGHT: 100,
         ADDRESS: "192.168.42.42:80",
         LABEL: "Node001",
         NODEID: 47,
         MODE: "accept",
         CONFIGID: 39,
         STATUS: "Unknown",
         NODEBALANCERID: 714
      }

	 * @param jsonObject the json object to extract the data from
	 */
	public NodeBalancerNode(JSONObject jsonObject) {
		this.weight = readInt(jsonObject, JSON_KEY_WEIGHT);
		this.address = readString(jsonObject, JSON_KEY_ADDRESS);
		this.label = readString(jsonObject, JSON_KEY_LABEL_UPPER);
		this.nodeId = readLong(jsonObject, JSON_KEY_NODEID);
		this.mode = readString(jsonObject, JSON_KEY_MODE);
		this.configId = readLong(jsonObject, JSON_KEY_CONFIGID);
		this.status = readString(jsonObject, JSON_KEY_STATUS);
		this.nodeBalancerId = readLong(jsonObject, JSON_KEY_NODEBALANCERID);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Integer getWeight() {
		return this.weight;
	}

	public String getAddress() {
		return this.address;
	}

	public String getLabel() {
		return this.label;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public String getMode() {
		return this.mode;
	}

	public Long getConfigId() {
		return this.configId;
	}

	public String getStatus() {
		return this.status;
	}

	public Long getNodeBalancerId() {
		return this.nodeBalancerId;
	}
}
