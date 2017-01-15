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

public class DomainResource extends BaseJsonReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(DomainResource.class);

	private String protocol = null;
	private Long numTtlSeconds = null;
	private Integer numPriority = null;
	private String type = null;
	private String target = null;
	private Integer weight = null;
	private Long resourceId = null;
	private Integer port = null;
	private Long domainId = null;
	private String name = null;

	
	/**
	 *       {
	 *          "PROTOCOL":"",
	 *          "TTL_SEC":0,
	 *          "PRIORITY":0,
	 *          "TYPE":"A",
	 *          "TARGET":"75.127.96.245",
	 *          "WEIGHT":0,
	 *          "RESOURCEID":28536,
	 *          "PORT":0,
	 *          "DOMAINID":5093,
	 *          "NAME":"www"
	 *       },
	 * 
	 * @param jsonObject the json object to extract the data from
	 */
	public DomainResource(JSONObject jsonObject) {
		this.protocol = readString(jsonObject, JSON_KEY_PROTOCOL);
		this.numTtlSeconds = readLong(jsonObject, JSON_KEY_TTL_SEC);
		this.type = readString(jsonObject, JSON_KEY_TYPE);
		this.target = readString(jsonObject, JSON_KEY_TARGET);
		this.weight = readInt(jsonObject, JSON_KEY_WEIGHT);
		this.resourceId = readLong(jsonObject, JSON_KEY_RESOURCEID);
		this.port = readInt(jsonObject, JSON_KEY_PORT);
		this.domainId = readLong(jsonObject, JSON_KEY_DOMAINID);
		this.name = readString(jsonObject, JSON_KEY_NAME);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}


	public String getProtocol() {
		return this.protocol;
	}


	public Long getNumTtlSeconds() {
		return this.numTtlSeconds;
	}


	public Integer getNumPriority() {
		return this.numPriority;
	}


	public String getType() {
		return this.type;
	}


	public String getTarget() {
		return this.target;
	}


	public Integer getWeight() {
		return this.weight;
	}


	public Long getResourceId() {
		return this.resourceId;
	}


	public Integer getPort() {
		return this.port;
	}


	public Long getDomainId() {
		return this.domainId;
	}


	public String getName() {
		return this.name;
	}
}
