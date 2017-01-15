package synapticloop.linode.api.response;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Distribution;
import synapticloop.linode.exception.ApiException;

public class AvailDistributionsResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(AvailDistributionsResponse.class);

	private List<Distribution> distributions = new ArrayList<Distribution>();
	private Map<Long, Distribution> distributionIdLookup = new HashMap<Long, Distribution>();

	/**
	 * 
	 * The json should have been formed by the following:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"avail.distributions",
	 *    "DATA":[
	 *       {
	 *          "IS64BIT":0,
	 *          "LABEL":"Debian 4.0",
	 *          "MINIMAGESIZE":200,
	 *          "DISTRIBUTIONID":28,
	 *          "CREATE_DT":"2007-04-18 00:00:00.0",
	 *          "REQUIRESPVOPSKERNEL":0
	 *       }
	 *    ]
	 * }
	 * </pre>
	 * 
	 * @param jsonObject the json object to parse
	 * @throws ApiException if there was an error converting the date
	 */
	public AvailDistributionsResponse(JSONObject jsonObject) throws ApiException {
		super(jsonObject);

		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray(JSON_KEY_DATA);
			for (Object distributionObject : dataArray) {
				Distribution distribution = new Distribution((JSONObject)distributionObject);
				distributions.add(distribution);
				distributionIdLookup.put(distribution.getDistributionId(), distribution);
			}
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<Distribution> getDistributions() {
		return(distributions);
	}

	public Distribution getDistributionById(Long id) {
		return(distributionIdLookup.get(id));
	}
}
