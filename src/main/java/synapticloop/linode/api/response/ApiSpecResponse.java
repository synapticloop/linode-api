package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Method;

public class ApiSpecResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiSpecResponse.class);

	private List<Method> methods = new ArrayList<Method>();
	private Map<String, Method> methodLookup = new HashMap<String, Method>();
	private Double version = null;
	/**
	 *   "METHODS":{
	 *    "account.info":{
	 *     "DESCRIPTION":"Shows information about your account such as
	 *      the date your account was opened as well as your network
	 *      utilization for the current month in gigabytes.",
	 *      "PARAMETERS":{},
	 *      "THROWS":""
	 *     },
	 *     "api.spec":{
	 *     "DESCRIPTION" : "Returns a data structure of the entire
	 *      Linode API specification.",
	 *      "PARAMETERS":{},
	 *      "THROWS":""
	 *    }
	 *   }
	 * 
	 * @param jsonObject
	 */
	public ApiSpecResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {

			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			this.version = dataObject.getDouble(JSON_KEY_VERSION);
			dataObject.remove(JSON_KEY_VERSION);

			JSONObject methodsObject = dataObject.getJSONObject(JSON_KEY_METHODS);
			Iterator<String> keys = methodsObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				methods.add(new Method(key, methodsObject.getJSONObject(key)));
			}

			dataObject.remove(JSON_KEY_METHODS);

			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}

		jsonObject.remove(JSON_KEY_DATA);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);

	}
	public List<Method> getMethods() {
		return this.methods;
	}

	public Method getMethodForName(String name) {
		return(methodLookup.get(name));
	}

	public Double getVersion() {
		return this.version;
	}

}
