package synapticloop.linode.api.response.bean;

/*
 * Copyright (c) 2016 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class LinodePlan extends BaseLinodeBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodePlan.class);

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
	 * @param jsonObject the json object to extract the data from
	 */
	public LinodePlan(JSONObject jsonObject) {
		this.numCores = readInt(jsonObject, JSON_KEY_CORES);
		this.priceMonthly = readDouble(jsonObject, JSON_KEY_PRICE);
		this.ram = readLong(jsonObject, JSON_KEY_RAM);
		this.planId = readLong(jsonObject, JSON_KEY_PLANID);
		this.label = readString(jsonObject, JSON_KEY_LABEL_UPPER);
		this.diskSize = readLong(jsonObject, JSON_KEY_DISK);
		this.priceHourly = readDouble(jsonObject, JSON_KEY_HOURLY);
		this.xfer = readLong(jsonObject, JSON_KEY_XFER);

		JSONObject availObject = jsonObject.getJSONObject("AVAIL");
		Iterator<String> keys = availObject.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			availability.put(Long.parseLong(key), availObject.getLong(key));
		}
		jsonObject.remove("AVAIL");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
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
