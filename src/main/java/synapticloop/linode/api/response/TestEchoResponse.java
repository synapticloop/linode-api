package synapticloop.linode.api.response;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class TestEchoResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestEchoResponse.class);

	private Map<String, String> responses = new HashMap<String, String>();

	public TestEchoResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			Iterator<String> keys = dataObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				getResponses().put(key, dataObject.getString(key));
			}
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Map<String, String> getResponses() {
		return responses;
	}
}
