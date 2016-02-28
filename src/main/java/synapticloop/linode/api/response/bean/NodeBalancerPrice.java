package synapticloop.linode.api.response.bean;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class NodeBalancerPrice {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodeBalancerPrice.class);

	private Double priceMonthly = null;
	private Double priceHourly = null;
	private Long numConnections = null;

	/**
	 * {
	 *         "MONTHLY": 20.00,
	 *         "HOURLY": 0.0300,
	 *         "CONNECTIONS": 10000
	 *  }
	 * @param jsonObject
	 */
	public NodeBalancerPrice(JSONObject jsonObject) {
		this.priceMonthly = jsonObject.getDouble("HOURLY");
		jsonObject.remove("HOURLY");
		this.priceHourly = jsonObject.getDouble("MONTHLY");
		jsonObject.remove("MONTHLY");
		this.numConnections = jsonObject.getLong("CONNECTIONS");
		jsonObject.remove("CONNECTIONS");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Double getPriceMonthly() {
		return this.priceMonthly;
	}

	public Double getPriceHourly() {
		return this.priceHourly;
	}

	public Long getNumConnections() {
		return this.numConnections;
	}
}
