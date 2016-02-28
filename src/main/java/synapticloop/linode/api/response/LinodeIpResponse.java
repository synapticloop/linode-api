package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class LinodeIpResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeIpResponse.class);

	private Long ipAddressId = null;
	private String ipAddress = null;

	public LinodeIpResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);

			// for both of these - for some unknown reason, the key is all UPPERCASE 
			// (private ip addresses)
			this.ipAddressId = dataObject.optLong("IpAddressID", -1l);
			if(-1l == this.ipAddressId) {
				this.ipAddressId = dataObject.optLong("IPADDRESSID", -1l);
			}
			this.ipAddress = dataObject.optString("IPAddress", null);
			if(null == this.ipAddress) {
				this.ipAddress = dataObject.optString("IPADDRESS");
			}

			dataObject.remove("IpAddressID");
			dataObject.remove("IPADDRESSID");
			dataObject.remove("IpAddress");
			dataObject.remove("IPADDRESS");

			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getConfigId() {
		return this.ipAddressId;
	}

	public Long getIpAddressId() {
		return this.ipAddressId;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}
}
