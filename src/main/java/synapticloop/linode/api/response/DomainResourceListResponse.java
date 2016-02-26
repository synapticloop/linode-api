package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.linode.api.response.bean.DomainResource;

public class DomainResourceListResponse extends BaseResponse {
	private List<DomainResource> domainResources = new ArrayList<DomainResource>();

	public DomainResourceListResponse(JSONObject jsonObject) {
		super(jsonObject);

		JSONArray dataArray = jsonObject.getJSONArray("DATA");
		for (Object object : dataArray) {
			domainResources.add(new DomainResource((JSONObject)object));
		}
	}

	public List<DomainResource> getDomainResources() {
		return this.domainResources;
	}

}
