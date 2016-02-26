package synapticloop.linode.api.response;

import java.util.Date;

import org.json.JSONObject;

public class AccountInfoResponse extends BaseResponse {
	private Date activeSince = null;
	private Long transferPool = null;
	private Long transferUsed = null;
	private Long transferBillable = null;
	private boolean isManaged = false;
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
		this.activeSince = BaseResponse.convertDate(jsonObject.getString("ACTIVE_SINCE"));
		this.transferPool = jsonObject.getLong("TRANSFER_POOL");
		this.transferUsed = jsonObject.getLong("TRANSFER_USED");
		this.transferBillable = jsonObject.getLong("TRANSFER_BILLABLE");
		this.isManaged = jsonObject.getBoolean("MANAGED");
		this.balance = jsonObject.getLong("BALANCE");
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

}
