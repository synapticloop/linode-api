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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class Kernel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Kernel.class);

	private String label = null;
	private boolean isXen = false;
	private boolean isKvm = false;
	private boolean isPVOps = false;
	private Long kernelId = null;

	/**
	 *       {
	 *          "LABEL":"2.6.32.16-linode28",
	 *          "ISXEN":1,
	 *          "ISKVM":0,
	 *          "ISPVOPS":1,
	 *          "KERNELID":123
	 *       },
	 * 
	 * @param jsonObject the json object to extract the data from
	 */

	public Kernel(JSONObject jsonObject) {
		this.label = jsonObject.getString("LABEL");
		jsonObject.remove("LABEL");

		this.isXen = (1 == jsonObject.getInt("ISXEN"));
		jsonObject.remove("ISXEN");

		this.isKvm = (1 == jsonObject.getInt("ISKVM"));
		jsonObject.remove("ISKVM");

		this.isPVOps = (1 == jsonObject.getInt("ISPVOPS"));
		jsonObject.remove("ISPVOPS");

		this.kernelId = jsonObject.getLong("KERNELID");
		jsonObject.remove("KERNELID");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public String getLabel() {
		return this.label;
	}

	public boolean getIsXen() {
		return this.isXen;
	}

	public boolean getIsKvm() {
		return this.isKvm;
	}

	public boolean getIsPVOps() {
		return this.isPVOps;
	}

	public Long getKernelId() {
		return this.kernelId;
	}
}
