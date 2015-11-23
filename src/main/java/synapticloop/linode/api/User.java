package synapticloop.linode.api;

// - - - - thoughtfully generated by synapticloop linode-api - - - - 
//     with the use of synapticloop templar templating language
//               (java-create-api.templar)

import java.util.HashMap;
import java.util.Map;

import synapticloop.linode.LinodeRequest;
import synapticloop.linode.exception.ApiException;

/**
 * This is the interaction class for the User api calls, this was automatically
 * generated from the linode api documentation - which can be found here:
 * <a href="http://www.linode.com/api/account">http://www.linode.com/api/account</a>
 * 
 * @author synapticloop
 */

public class User extends ApiBase {
	private static final String PARAM_CONSTANT_USERNAME = "username";
	private static final String PARAM_CONSTANT_PASSWORD = "password";
	private static final String PARAM_CONSTANT_TOKEN = "token";
	private static final String PARAM_CONSTANT_EXPIRES = "expires";
	private static final String PARAM_CONSTANT_LABEL = "label";

	/**
	 * Private constructor to deter instantiation
	 */
	private User() {}

	/**
	 * <p>Authenticates a Linode Manager user against their username, password, and two-factor 
	 * token (when enabled), and then returns a new API key, which can be used until it expires. The number of 
	 * active keys is limited to 20.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"user.getAPIKey",
	 *    "DATA":{
	 *       "USERNAME":"chris",
	 *       "API_KEY":"aNW3ydF53LpVftU9s50e0reFEwy0piwGhwaLKAj4okRZ"
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - AUTHFAIL
	 *
	 * @param username <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param password <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public static LinodeRequest getapikey(String username, String password) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_USERNAME, username, false);
		addParameterSafely(parameters, PARAM_CONSTANT_PASSWORD, password, false);
		return(new LinodeRequest("user.getapikey", parameters));
	}

	/**
	 * <p>Authenticates a Linode Manager user against their username, password, and two-factor 
	 * token (when enabled), and then returns a new API key, which can be used until it expires. The number of 
	 * active keys is limited to 20.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"user.getAPIKey",
	 *    "DATA":{
	 *       "USERNAME":"chris",
	 *       "API_KEY":"aNW3ydF53LpVftU9s50e0reFEwy0piwGhwaLKAj4okRZ"
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - AUTHFAIL
	 *
	 * @param username <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param password <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param token  <em>(OPTIONAL)</em> Required when two-factor authentication is enabled.
	 * @param expires  <em>(OPTIONAL)</em> Number of hours the key will remain valid, between 0 and 8760. 0 means no expiration. Defaults to 168.
	 * @param label  <em>(OPTIONAL)</em> An optional label for this key.
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public static LinodeRequest getapikey(String username, String password, String token, Long expires, String label) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_USERNAME, username, false);
		addParameterSafely(parameters, PARAM_CONSTANT_PASSWORD, password, false);
		addParameterSafely(parameters, PARAM_CONSTANT_TOKEN, token, true);
		addParameterSafely(parameters, PARAM_CONSTANT_EXPIRES, expires, true);
		addParameterSafely(parameters, PARAM_CONSTANT_LABEL, label, true);
		return(new LinodeRequest("user.getapikey", parameters));
	}

};