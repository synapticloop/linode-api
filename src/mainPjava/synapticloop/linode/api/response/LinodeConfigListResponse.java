package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.linode.api.response.bean.Config;

public class LinodeConfigListResponse extends AvailStackscriptsResponse {
	private List<Config> configs = new ArrayList<Config>();
	/**
	 * 
	 * @param jsonObject
	 */
	public LinodeConfigListResponse(JSONObject jsonObject) {
		super(jsonObject);
		JSONArray dataArray = jsonObject.getJSONArray("DATA");
		for (Object object : dataArray) {
			configs.add(new Config((JSONObject)object));
		}
	}

	public List<Config> getConfigs() {
		return this.configs;
	}

}
