package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class LinodeIpSetRdnsResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeIpSetRdnsResponse.class);

	private String hostname = null;
	private Long ipAddressId = null;
	private String ipAddress = null;

	/**
	 * {
      "HOSTNAME": "li13-10.members.linode.com",
      "IPADDRESSID": 5384,
      "IPADDRESS": "69.93.127.10"
   },
	 * @param jsonObject the json object to parse
	 */
	public LinodeIpSetRdnsResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			jsonObject.getJSONObject(JSON_KEY_DATA);
			this.hostname = jsonObject.getString("HOSTNAME");
			jsonObject.remove("HOSTNAME");
			this.ipAddressId = jsonObject.getLong("IPADDRESSID");
			jsonObject.remove("IPADDRESSID");
			this.ipAddress = jsonObject.getString("IPADDRESS");
			jsonObject.remove("IPADDRESS");
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public String getHostname() {
		return this.hostname;
	}

	public Long getIpAddressId() {
		return this.ipAddressId;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

}
