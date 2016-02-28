package synapticloop.linode.api.response.bean;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class NodeBalancerNode {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodeBalancerNode.class);
	
	private Integer weight = null;
	private String address = null;
	private String label = null;
	private Long nodeId = null;
	private String mode = null;
	private Long configId = null;
	private String status = null;
	private Long nodeBalancerId = null;

	/**
      {
         WEIGHT: 100,
         ADDRESS: "192.168.42.42:80",
         LABEL: "Node001",
         NODEID: 47,
         MODE: "accept",
         CONFIGID: 39,
         STATUS: "Unknown",
         NODEBALANCERID: 714
      }

	 * @param jsonObject the json object to extract the data from
	 */
	public NodeBalancerNode(JSONObject jsonObject) {
		this.weight = jsonObject.getInt("WEIGHT");
		jsonObject.remove("WEIGHT");
		this.address = jsonObject.getString("ADDRESS");
		jsonObject.remove("ADDRESS");
		this.label = jsonObject.getString("LABEL");
		jsonObject.remove("LABEL");
		this.nodeId = jsonObject.getLong("NODEID");
		jsonObject.remove("NODEID");
		this.mode = jsonObject.getString("MODE");
		jsonObject.remove("MODE");
		this.configId = jsonObject.getLong("CONFIGID");
		jsonObject.remove("CONFIGID");
		this.status = jsonObject.getString("STATUS");
		jsonObject.remove("STATUS");
		this.nodeBalancerId = jsonObject.getLong("NODEBALANCERID");
		jsonObject.remove("NODEBALANCERID");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Integer getWeight() {
		return this.weight;
	}

	public String getAddress() {
		return this.address;
	}

	public String getLabel() {
		return this.label;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public String getMode() {
		return this.mode;
	}

	public Long getConfigId() {
		return this.configId;
	}

	public String getStatus() {
		return this.status;
	}

	public Long getNodeBalancerId() {
		return this.nodeBalancerId;
	}
}
