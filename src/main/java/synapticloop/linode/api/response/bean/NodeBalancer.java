package synapticloop.linode.api.response.bean;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class NodeBalancer {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodeBalancer.class);
	private Long nodebalancerId = null;
	private String label = null;
	private Long datacenterId = null;
	private String hostname = null;
	private String ipAddressIPv4 = null;
	private String ipAddressIPv6 = null;
	private Long clientConnectionThrottle = null;

	/**
	 *       {
         "NODEBALANCERID":53,
         "LABEL":"awesomebal",
         "DATACENTERID":6,
         "HOSTNAME":"nb-69-164-223-4.newark.nodebalancer.linode.com",
         "ADDRESS4":"69.164.223.4",
         "ADDRESS6":"2600:3c03:1::45a4:df04",
         "CLIENTCONNTHROTTLE":4203
      }

	 * @param jsonObject
	 */
	public NodeBalancer(JSONObject jsonObject) {
		this.nodebalancerId = jsonObject.getLong("NODEBALANCERID");
		jsonObject.remove("NODEBALANCERID");
		this.label = jsonObject.getString("LABEL");
		jsonObject.remove("LABEL");
		this.datacenterId = jsonObject.getLong("DATACENTERID");
		jsonObject.remove("DATACENTERID");
		this.hostname = jsonObject.getString("HOSTNAME");
		jsonObject.remove("HOSTNAME");
		this.ipAddressIPv4 = jsonObject.getString("ADDRESS4");
		jsonObject.remove("ADDRESS4");
		this.ipAddressIPv6 = jsonObject.getString("ADDRESS6");
		jsonObject.remove("ADDRESS6");
		this.clientConnectionThrottle = jsonObject.getLong("CLIENTCONNTHROTTLE");
		jsonObject.remove("CLIENTCONNTHROTTLE");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getNodebalancerId() {
		return this.nodebalancerId;
	}

	public String getLabel() {
		return this.label;
	}

	public Long getDatacenterId() {
		return this.datacenterId;
	}

	public String getHostname() {
		return this.hostname;
	}

	public String getIpAddressIPv4() {
		return this.ipAddressIPv4;
	}

	public String getIpAddressIPv6() {
		return this.ipAddressIPv6;
	}

	public Long getClientConnectionThrottle() {
		return this.clientConnectionThrottle;
	}

}
