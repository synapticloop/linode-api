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

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.IPAddress;

public class LinodeIpSwapResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeIpSwapResponse.class);

	private IPAddress ipAddressFrom = null;
	private IPAddress ipAddressTo = null;
	/**
	 *    "DATA": [
      {
         "LINODEID": 8099,
         "IPADDRESS": "75.128.96.54",
         "IPADDRESSID": 5384
      },
      {
         "IPADDRESS": "75.127.96.245",
         "LINODEID": 8098,
         "IPADDRESSID": 5575
      }
   ],

	 * @param jsonObject the json object to parse
	 */
	public LinodeIpSwapResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray(JSON_KEY_DATA);
			switch (dataArray.length()) {
			case 2:
				ipAddressTo = new IPAddress(dataArray.getJSONObject(1));
			case 1:
				ipAddressFrom = new IPAddress(dataArray.getJSONObject(0));
				break;
			default:
				break;
			}
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public IPAddress getIpAddressFrom() {
		return this.ipAddressFrom;
	}

	public IPAddress getIpAddressTo() {
		return this.ipAddressTo;
	}
}
