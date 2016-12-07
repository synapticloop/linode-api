package synapticloop.linode.api.request;

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

import java.util.Map;

import synapticloop.linode.exception.ApiException;

/**
 * The abstract base class with helper methods for all API calls.
 * 
 * @author synapticloop
 */
public abstract class ApiBaseRequest {

	/**
	 * Safely add a parameter to the parameter map, with checks for null values.  
	 * If the parameter is not allowed to be null and is null, then throw an 
	 * ApiException.  If the parameter is null and allowed to be null, the 
	 * parameter will not be added to the parameter map.
	 * 
	 * @param parameterMap The map of parameters key:value pairs
	 * @param parameterName The name of the parameter to set
	 * @param parameterValue The value of the parameter
	 * @param allowNull Whether this parameter value is allowed to be null
	 * 
	 * @throws ApiException If the parameter value is not allowed to be null, but is null
	 */
	protected static void addParameterSafely(Map<String, String> parameterMap, String parameterName, Object parameterValue, boolean allowNull) throws ApiException {
		if(null != parameterValue) {
			parameterMap.put(parameterName, parameterValue.toString());
		} else {
			if(!allowNull) {
				throw new ApiException("Parameter '" + parameterName + "' is required and cannot be null.");
			}
		}
	}
}
