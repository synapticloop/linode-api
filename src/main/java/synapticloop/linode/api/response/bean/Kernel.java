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
import synapticloop.linode.api.response.BaseJsonReader;

public class Kernel extends BaseJsonReader {
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
		this.label = readString(jsonObject, JSON_KEY_LABEL_UPPER);
		this.isXen = (1 == readInt(jsonObject, JSON_KEY_ISXEN));
		this.isKvm = (1 == readInt(jsonObject, JSON_KEY_ISKVM));
		this.isPVOps = (1 == readInt(jsonObject, JSON_KEY_ISPVOPS));
		this.kernelId = readLong(jsonObject, JSON_KEY_KERNELID);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	/**
	 * Get the label for this kernel
	 * 
	 * @return The label for this kerne;
	 */
	public String getLabel() { return this.label; }

	/**
	 * Return whether this runs on a Xen virtualisation platform
	 * 
	 * @return whether this runs on a Xen virtualisation platform
	 */
	public boolean getIsXen() { return this.isXen; }

	/**
	 * Return whether this runs on a KVM virtualisation platform
	 * 
	 * @return whether this runs on a KVM virtualisation platform
	 */
	public boolean getIsKvm() { return this.isKvm; }

	/**
	 * Return whether this uses ParaVirtual operations
	 * 
	 * @return whether this uses ParaVirtual operations
	 */
	public boolean getIsPVOps() { return this.isPVOps; }

	/**
	 * Return the ID of the Linode
	 *  
	 * @return the ID of the Linode
	 */
	public Long getKernelId() { return this.kernelId; }
}
