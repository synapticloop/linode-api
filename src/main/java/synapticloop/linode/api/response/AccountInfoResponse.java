package synapticloop.linode.api.response;

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

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.exception.ApiException;

public class AccountInfoResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountInfoResponse.class);

	private Date activeSince = null;
	private Long transferPool = null;
	private Long transferUsed = null;
	private Long transferBillable = null;
	private boolean isManaged = false;
	private String billingMethod = null;
	private Long balance = null;

	/**
	 *       {
	 *          "ACTIVE_SINCE":"2011-09-23 15:08:13.0",
	 *          "TRANSFER_POOL":200,
	 *          "TRANSFER_USED":150,
	 *          "TRANSFER_BILLABLE":0,
	 *          "MANAGED":true,
	 *          "BALANCE":20
	 *       }
	 * 
	 * @param jsonObject the json object to parse
	 * @throws ApiException if there was an error converting the date
	 */
	public AccountInfoResponse(JSONObject jsonObject) throws ApiException {
		super(jsonObject);

		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			this.activeSince = ResponseHelper.convertDate(dataObject.getString("ACTIVE_SINCE"));
			dataObject.remove("ACTIVE_SINCE");
			this.transferPool = dataObject.getLong("TRANSFER_POOL");
			dataObject.remove("TRANSFER_POOL");
			this.transferUsed = dataObject.getLong("TRANSFER_USED");
			dataObject.remove("TRANSFER_USED");
			this.transferBillable = dataObject.getLong("TRANSFER_BILLABLE");
			dataObject.remove("TRANSFER_BILLABLE");
			this.isManaged = dataObject.getBoolean("MANAGED");
			dataObject.remove("MANAGED");
			this.billingMethod = dataObject.getString("BILLING_METHOD");
			dataObject.remove("BILLING_METHOD");
			this.balance = dataObject.getLong("BALANCE");
			dataObject.remove("BALANCE");

			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Date getActiveSince() {
		return this.activeSince;
	}

	public Long getTransferPool() {
		return this.transferPool;
	}

	public Long getTransferUsed() {
		return this.transferUsed;
	}

	public Long getTransferBillable() {
		return this.transferBillable;
	}

	public boolean getIsManaged() {
		return this.isManaged;
	}

	public Long getBalance() {
		return this.balance;
	}

	public String getBillingMethod() {
		return this.billingMethod;
	}

}
