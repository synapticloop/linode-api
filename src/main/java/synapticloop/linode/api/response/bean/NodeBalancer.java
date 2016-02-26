package synapticloop.linode.api.response.bean;

import org.json.JSONObject;

public class NodeBalancer {
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
	public NodeBalancer(JSONObject jsonObject) {
		this.priceMonthly = jsonObject.getDouble("HOURLY");
		this.priceHourly = jsonObject.getDouble("MONTHLY");
		this.numConnections = jsonObject.getLong("CONNECTIONS");
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
