package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Config;

public class LinodeConfigListResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeConfigListResponse.class);

	private List<Config> configs = new ArrayList<Config>();
	/**
	 * 
	 * @param jsonObject
	 */
	public LinodeConfigListResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray(JSON_KEY_DATA);
			for (Object object : dataArray) {
				configs.add(new Config((JSONObject)object));
			}
		}
		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);

	}

	public List<Config> getConfigs() {
		return this.configs;
	}

}
