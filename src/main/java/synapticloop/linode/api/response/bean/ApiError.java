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

public class ApiError extends BaseJsonReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiError.class);

	private Integer errorCode = null;
	private String errorMessage = null;

	/**
	 * Instantiate an APIError object by extracting the relevant data from the
	 * json object.
	 * 
	 * @param jsonObject the json object to extract the data from
	 */
	public ApiError(JSONObject jsonObject) {
		this.errorCode = readInt(jsonObject, JSON_KEY_ERRORCODE);
		this.errorMessage = readString(jsonObject, JSON_KEY_ERRORMESSAGE);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	/**
	 * return the error code associated with the API
	 * 
	 * @return the error code
	 */
	public Integer getErrorCode() {
		return this.errorCode;
	}

	/**
	 * Return the human readable error message for this error
	 * 
	 * @return the human readable error message for this error
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
}
