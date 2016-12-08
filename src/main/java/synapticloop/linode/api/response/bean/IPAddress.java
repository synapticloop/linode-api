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

public class IPAddress extends BaseLinodeBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(IPAddress.class);

	private Long linodeId = null;
	private boolean isPublic = false;
	private String ipAddress = null;
	private String reverseDNSName = null;
	private Long ipAddressId = null;

	/**
	 *       {
	 *          "LINODEID":8099,
	 *          "ISPUBLIC":1,
	 *          "IPADDRESS":"75.127.96.245",
	 *          "RDNS_NAME":"li22-245.members.linode.com",
	 *          "IPADDRESSID":5575
	 *       }
	 * @param jsonObject the json object to extract the data from
	 */
	public IPAddress(JSONObject jsonObject) {
		this.linodeId = readLong(jsonObject, JSON_KEY_LINODEID);
		this.isPublic = (1 == readInt(jsonObject, JSON_KEY_ISPUBLIC, 0));
		this.reverseDNSName = readString(jsonObject, JSON_KEY_RDNS_NAME, null);
		this.ipAddress = readString(jsonObject, JSON_KEY_IPADDRESS);
		this.ipAddressId = readLong(jsonObject, JSON_KEY_IPADDRESSID);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getLinodeId() {
		return this.linodeId;
	}

	public boolean getIsPublic() {
		return this.isPublic;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public String getReverseDNSName() {
		return this.reverseDNSName;
	}

	public Long getIpAddressId() {
		return this.ipAddressId;
	}

}
