package synapticloop.linode.api.response;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class AccountEstimateInvoiceResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountEstimateInvoiceResponse.class);

	private Date invoiceTo = null;
	private Double amount = null;

	public AccountEstimateInvoiceResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			this.invoiceTo = ResponseHelper.convertDate(dataObject.getString("INVOICE_TO"));
			dataObject.remove("INVOICE_TO");

			this.amount = dataObject.getDouble("AMOUNT");
			dataObject.remove("AMOUNT");

			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}

		jsonObject.remove(JSON_KEY_DATA);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Date getInvoiceTo() {
		return this.invoiceTo;
	}

	public Double getAmount() {
		return this.amount;
	}
}
