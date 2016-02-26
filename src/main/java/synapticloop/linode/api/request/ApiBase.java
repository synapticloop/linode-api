package synapticloop.linode.api.request;

import java.util.Map;

import synapticloop.linode.exception.ApiException;

/**
 * The abstract base class with helper methods for all API calls.
 * 
 * @author synapticloop
 */
public abstract class ApiBase {

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
