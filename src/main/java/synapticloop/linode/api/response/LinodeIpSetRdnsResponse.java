package synapticloop.linode.api.response;

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

public class LinodeIpSetRdnsResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeIpSetRdnsResponse.class);

	private String hostname = null;
	private Long ipAddressId = null;
	private String ipAddress = null;

	/**
	 * {
      "HOSTNAME": "li13-10.members.linode.com",
      "IPADDRESSID": 5384,
      "IPADDRESS": "69.93.127.10"
   },
	 * @param jsonObject the json object to parse
	 */
	public LinodeIpSetRdnsResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			jsonObject.getJSONObject(JSON_KEY_DATA);
			this.hostname = jsonObject.getString("HOSTNAME");
			jsonObject.remove("HOSTNAME");
			this.ipAddressId = jsonObject.getLong("IPADDRESSID");
			jsonObject.remove("IPADDRESSID");
			this.ipAddress = jsonObject.getString("IPADDRESS");
			jsonObject.remove("IPADDRESS");
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public String getHostname() {
		return this.hostname;
	}

	public Long getIpAddressId() {
		return this.ipAddressId;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

}
