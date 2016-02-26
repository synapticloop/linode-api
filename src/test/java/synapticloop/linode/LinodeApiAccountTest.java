package synapticloop.linode;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.api.response.AccountEstimateInvoiceResponse;
import synapticloop.linode.api.response.AccountInfoResponse;
import synapticloop.linode.api.response.AvailLinodePlansResponse;
import synapticloop.linode.api.response.bean.LinodePlan;
import synapticloop.linode.exception.ApiException;

public class LinodeApiAccountTest {
	private LinodeApi linodeApi;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));
	}

	@Test
	public void testEstimateInvoiceName() throws ApiException {
		AvailLinodePlansResponse availLinodePlans = linodeApi.getAvailLinodePlans();
		LinodePlan linodePlan = availLinodePlans.getLinodePlans().get(0);
		AccountEstimateInvoiceResponse accountEstimateInvoice = linodeApi.getAccountEstimateInvoice("linode_new");
		assertTrue(accountEstimateInvoice.hasErrors());
		accountEstimateInvoice = linodeApi.getAccountEstimateInvoice("linode_new", 1l, linodePlan.getPlanId(), null);
		assertFalse(accountEstimateInvoice.hasErrors());
	}
	
	@Test
	public void testAccountInfo() throws ApiException {
		AccountInfoResponse accountInfo = linodeApi.getAccountInfo();
		assertFalse(accountInfo.hasErrors());
		
	}

}
