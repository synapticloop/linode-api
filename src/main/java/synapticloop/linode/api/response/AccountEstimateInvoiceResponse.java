package synapticloop.linode.api.response;

import java.util.Date;

import org.json.JSONObject;

public class AccountEstimateInvoiceResponse extends BaseResponse {
	private Date invoiceTo = null;
	private Double price = null;

	public AccountEstimateInvoiceResponse(JSONObject jsonObject) {
		super(jsonObject);
		this.invoiceTo = BaseResponse.convertDate(jsonObject.getJSONObject("DATA").getString("INVOICE_TO"));
		this.price = jsonObject.getJSONObject("DATA").getDouble("PRICE");
	}

	public Date getInvoiceTo() {
		return this.invoiceTo;
	}

	public Double getPrice() {
		return this.price;
	}
}
