package synapticloop.linode.api.response;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

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
	 * @param jsonObject
	 */
	public AccountInfoResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject("DATA");
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

		jsonObject.remove("DATA");
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
