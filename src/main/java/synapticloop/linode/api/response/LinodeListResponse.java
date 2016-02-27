package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Linode;

public class LinodeListResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeListResponse.class);
	List<Linode> linodes = new ArrayList<Linode>();

	public LinodeListResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray("DATA");
			for (Object object : dataArray) {
				linodes.add(new Linode((JSONObject)object));
			}
		}
		jsonObject.remove("DATA");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<Linode> getLinodes() {
		return this.linodes;
	}

}
