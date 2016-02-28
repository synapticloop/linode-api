package synapticloop.linode;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
		try {
			linodeApi.getAccountEstimateInvoice("linode_new");
		} catch(ApiException ex) {
		}

		try {
			linodeApi.getAccountEstimateInvoice("linode_new", 1l, linodePlan.getPlanId(), null);
		} catch(ApiException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testAccountInfo() throws ApiException {
		AccountInfoResponse accountInfo = linodeApi.getAccountInfo();
		assertFalse(accountInfo.hasErrors());

	}

}
