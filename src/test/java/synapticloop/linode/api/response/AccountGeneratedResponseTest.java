package synapticloop.linode.api.response;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import synapticloop.linode.exception.ApiException;


/**
 * This is a generated test class for the Account api calls, this was 
 * automatically generated from the linode api documentation - which can be 
 * found here:
 * <a href="http://www.linode.com/api/account">http://www.linode.com/api/account</a>
 * 
 * @author synapticloop
 */

public class AccountGeneratedResponseTest {

	@Test
	public void testAccountEstimateInvoice_estimateinvoice() throws JSONException, ApiException {

		new AccountEstimateInvoiceResponse(new JSONObject("{" + 
				"   ERRORARRAY: [ ]," + 
				"   DATA: {" + 
				"      \"INVOICE_TO\": \"2013-09-30 23:59:59\"," + 
				"      \"AMOUNT\": 22.31" + 
				"   }," + 
				"   ACTION: \"account.estimateinvoice\"" + 
				"}"));

	}


	@Test
	public void testAccountInfo_info() throws JSONException, ApiException {

		new AccountInfoResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"account.info\"," + 
				"   \"DATA\": {" + 
				"      \"ACTIVE_SINCE\":\"2011-09-23 15:08:13.0\"," + 
				"      \"TRANSFER_POOL\":200," + 
				"      \"TRANSFER_USED\":150," + 
				"      \"TRANSFER_BILLABLE\":0," + 
				"      \"MANAGED\":true," + 
				"      \"BALANCE\":20," + 
				"      \"BILLING_METHOD\": \"\"" + 
				"   }" +
				"}"));

	}

};