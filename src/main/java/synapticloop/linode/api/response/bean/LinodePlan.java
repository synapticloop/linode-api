package synapticloop.linode.api.response.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

public class LinodePlan {
	private Integer numCores = null;
	private Double priceMonthly = null;
	private Long ram = null;
	private Long xfer = null;
	private Long planId = null;
	private String label = null;
	private Map<Long, Long> availability = new HashMap<Long, Long>();
	private Long diskSize = null;
	private Double priceHourly = null;

	/**
	 *    {
	 *      "CORES": 2,
	 *      "PRICE": 20.00,
	 *      "RAM": 2048,
	 *      "XFER": 3000,
	 *      "PLANID": 2,
	 *      "LABEL": "Linode 2048",
	 *      "AVAIL": {
	 *          "3": 500,
	 *          "2": 500,
	 *          "7": 500,
	 *          "6": 500,
	 *          "4": 500,
	 *          "8": 500
	 *      },
	 *      "DISK": 48,
	 *      "HOURLY": 0.03
	 *    }
	 * 
	 * @param jsonObject
	 */
	public LinodePlan(JSONObject jsonObject) {
		this.numCores = jsonObject.getInt("CORES");
		this.priceMonthly = jsonObject.getDouble("PRICE");
		this.ram = jsonObject.getLong("RAM");
		this.planId = jsonObject.getLong("PLANID");
		this.label = jsonObject.getString("LABEL");
		this.diskSize = jsonObject.getLong("DISK");
		this.priceHourly = jsonObject.getDouble("HOURLY");
		JSONObject availObject = jsonObject.getJSONObject("AVAIL");
		Iterator<String> keys = availObject.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			availability.put(Long.parseLong(key), availObject.getLong(key));
		}
	}

	public Integer getNumCores() {
		return this.numCores;
	}

	public Double getPriceMonthly() {
		return this.priceMonthly;
	}

	public Long getRam() {
		return this.ram;
	}

	public Long getXfer() {
		return this.xfer;
	}

	public Long getPlanId() {
		return this.planId;
	}

	public String getLabel() {
		return this.label;
	}

	public Long getAvailabilityForDatacenter(Long datacenterId) {
		return(availability.get(datacenterId));
	}

	public Map<Long, Long> getAvailability() {
		return this.availability;
	}

	public Long getDiskSize() {
		return this.diskSize;
	}

	public Double getPriceHourly() {
		return this.priceHourly;
	}
}
