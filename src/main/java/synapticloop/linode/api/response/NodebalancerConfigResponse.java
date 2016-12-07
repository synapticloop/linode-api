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

public class NodebalancerConfigResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodebalancerConfigResponse.class);

	private Long configId = null;

	public NodebalancerConfigResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			this.configId = dataObject.getLong("ConfigID");
			dataObject.remove("ConfigID");
			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}
		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getConfigId() {
		return this.configId;
	}
}
