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

public class Domain extends BaseJsonReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(Domain.class);

	private Long domainId = null;
	private String description = null;
	private String type = null;
	private Integer status = null;
	private String soaEmail = null;
	private String domain = null;
	private Long numRetrySeconds = null;
	private String masterIps = null;
	private Long numExpireSeconds = null;
	private Long numRefreshSeconds = null;
	private Long numTtlSeconds = null;


	/**
	 *       {
	 *          "DOMAINID":5093,
	 *          "DESCRIPTION":"",
	 *          "TYPE":"master",
	 *          "STATUS":1,
	 *          "SOA_EMAIL":"dns@example.com",
	 *          "DOMAIN":"linode.com",
	 *          "RETRY_SEC":0,
	 *          "MASTER_IPS":"",
	 *          "EXPIRE_SEC":0,
	 *          "REFRESH_SEC":0,
	 *          "TTL_SEC":0
	 * 
	 * @param jsonObject the json object to extract the data from
	 */
	public Domain(JSONObject jsonObject) {
		this.domainId = readLong(jsonObject, JSON_KEY_DOMAINID);
		this.description = readString(jsonObject, JSON_KEY_DESCRIPTION);
		this.status = readInt(jsonObject, JSON_KEY_STATUS);
		this.soaEmail = readString(jsonObject, JSON_KEY_SOA_EMAIL);
		this.domain = jsonObject.getString(JSON_KEY_DOMAIN);
		this.numRetrySeconds = readLong(jsonObject, JSON_KEY_RETRY_SEC);
		this.masterIps = readString(jsonObject, JSON_KEY_MASTER_IPS);
		this.numExpireSeconds = readLong(jsonObject, JSON_KEY_EXPIRE_SEC);
		this.numRefreshSeconds = readLong(jsonObject, JSON_KEY_REFRESH_SEC);
		this.numTtlSeconds = readLong(jsonObject, JSON_KEY_TTL_SEC);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}


	public Long getDomainId() {
		return this.domainId;
	}


	public String getDescription() {
		return this.description;
	}


	public String getType() {
		return this.type;
	}


	public Integer getStatus() {
		return this.status;
	}


	public String getSoaEmail() {
		return this.soaEmail;
	}


	public String getDomain() {
		return this.domain;
	}


	public Long getNumRetrySeconds() {
		return this.numRetrySeconds;
	}


	public String getMasterIps() {
		return this.masterIps;
	}


	public Long getNumExpireSeconds() {
		return this.numExpireSeconds;
	}


	public Long getNumRefreshSeconds() {
		return this.numRefreshSeconds;
	}


	public Long getNumTtlSeconds() {
		return this.numTtlSeconds;
	}
}
