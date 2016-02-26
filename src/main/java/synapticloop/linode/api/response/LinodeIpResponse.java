package synapticloop.linode.api.response;

import org.json.JSONObject;

public class LinodeIpResponse extends BaseResponse {
	private Long ipAddressId = null;
	private String ipAddress = null;

	public LinodeIpResponse(JSONObject jsonObject) {
		super(jsonObject);
		this.ipAddressId = jsonObject.getJSONObject("DATA").getLong("IpAddressID");
		this.ipAddress = jsonObject.getString("IPAddress");
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
