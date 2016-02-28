package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.DomainResource;

public class DomainResourceListResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(DomainResourceListResponse.class);

	private List<DomainResource> domainResources = new ArrayList<DomainResource>();

	public DomainResourceListResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray(JSON_KEY_DATA);
			for (Object object : dataArray) {
				domainResources.add(new DomainResource((JSONObject)object));
			}
		}
		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<DomainResource> getDomainResources() {
		return this.domainResources;
	}

}
