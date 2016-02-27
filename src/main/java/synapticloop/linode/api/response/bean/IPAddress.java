package synapticloop.linode.api.response.bean;



import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class IPAddress {
	private static final Logger LOGGER = LoggerFactory.getLogger(IPAddress.class);

	private Long linodeId = null;
	private boolean isPublic = false;
	private String ipAddress = null;
	private String reverseDNSName = null;
	private Long ipAddressId = null;

	/**
	 *       {
	 *          "LINODEID":8099,
	 *          "ISPUBLIC":1,
	 *          "IPADDRESS":"75.127.96.245",
	 *          "RDNS_NAME":"li22-245.members.linode.com",
	 *          "IPADDRESSID":5575
	 *       }
	 * @param jsonObject
	 */
	public IPAddress(JSONObject jsonObject) {
		this.linodeId = jsonObject.getLong("LINODEID");
		jsonObject.remove("LINODEID");
		this.isPublic = (1 == jsonObject.getInt("ISPUBLIC"));
		jsonObject.remove("ISPUBLIC");
		this.reverseDNSName = jsonObject.getString("RDNS_NAME");
		jsonObject.remove("RDNS_NAME");
		this.ipAddress = jsonObject.getString("IPADDRESS");
		jsonObject.remove("IPADDRESS");
		this.ipAddressId = jsonObject.getLong("IPADDRESSID");
		jsonObject.remove("IPADDRESSID");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}
	public Long getLinodeId() {
		return this.linodeId;
	}
	public boolean getIsPublic() {
		return this.isPublic;
	}
	public String getIpAddress() {
		return this.ipAddress;
	}
	public String getReverseDNSName() {
		return this.reverseDNSName;
	}
	public Long getIpAddressId() {
		return this.ipAddressId;
	}

}
