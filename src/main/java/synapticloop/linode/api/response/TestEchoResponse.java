package synapticloop.linode.api.response;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

public class TestEchoResponse extends BaseResponse {
	private Map<String, String> responses = new HashMap<String, String>();

	public TestEchoResponse(JSONObject jsonObject) {
		super(jsonObject);
		JSONObject dataObject = jsonObject.getJSONObject("DATA");
		Iterator<String> keys = dataObject.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			getResponses().put(key, dataObject.getString(key));
		}
	}

	public Map<String, String> getResponses() {
		return responses;
	}
}
