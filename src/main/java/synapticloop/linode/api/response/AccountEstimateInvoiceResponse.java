package synapticloop.linode.api.response;

import java.util.Date;
import java.util.logging.Logger;

import org.json.JSONObject;

public class AccountEstimateInvoiceResponse extends BaseResponse {
	private static final Logger LOGGER = Logger.getLogger(AccountEstimateInvoiceResponse.class.getName());

	private Date invoiceTo = null;
	private Double amount = null;

	public AccountEstimateInvoiceResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject("DATA");
			this.invoiceTo = BaseResponse.convertDate(dataObject.getString("INVOICE_TO"));
			dataObject.remove("INVOICE_TO");

			this.amount = dataObject.getDouble("AMOUNT");
			dataObject.remove("AMOUNT");

			warnOnMissedKeys(LOGGER, dataObject);
		}

		jsonObject.remove("DATA");

		warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Date getInvoiceTo() {
		return this.invoiceTo;
	}

	public Double getAmount() {
		return this.amount;
	}
}
