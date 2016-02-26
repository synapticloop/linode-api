package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.linode.api.response.bean.Domain;

public class DomainListResponse extends BaseResponse {
	private List<Domain> domains = new ArrayList<Domain>();

	public DomainListResponse(JSONObject jsonObject) {
		super(jsonObject);

		JSONArray dataArray = jsonObject.getJSONArray("DATA");
		for (Object object : dataArray) {
			domains.add(new Domain((JSONObject)object));
		}
	}

}
