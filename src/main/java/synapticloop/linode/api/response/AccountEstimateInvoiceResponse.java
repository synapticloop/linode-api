package synapticloop.linode.api.response;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.exception.ApiException;

public class AccountEstimateInvoiceResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountEstimateInvoiceResponse.class);

	private Date invoiceTo = null;
	private Double amount = null;

	public AccountEstimateInvoiceResponse(JSONObject jsonObject) throws ApiException {
		super(jsonObject);

		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			this.invoiceTo = ResponseHelper.convertDate(dataObject.getString(JSON_KEY_INVOICE_TO));
			dataObject.remove(JSON_KEY_INVOICE_TO);

			this.amount = dataObject.getDouble(JSON_KEY_AMOUNT);
			dataObject.remove(JSON_KEY_AMOUNT);

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
