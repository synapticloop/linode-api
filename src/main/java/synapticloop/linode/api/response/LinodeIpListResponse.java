package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.IPAddress;

public class LinodeIpListResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeIpListResponse.class);

	private List<IPAddress> ipAddresses= new ArrayList<IPAddress>();

	public LinodeIpListResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONArray ipArray = jsonObject.getJSONArray("DATA");
			for (Object object : ipArray) {
				getIpAddresses().add(new IPAddress((JSONObject)object));
			}
		}

		jsonObject.remove("DATA");
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<IPAddress> getIpAddresses() {
		return ipAddresses;
	}
}
