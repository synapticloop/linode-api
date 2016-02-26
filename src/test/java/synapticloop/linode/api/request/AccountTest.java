package synapticloop.linode.api.request;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeApiRequest;
import synapticloop.linode.LinodeApiResponse;
import synapticloop.linode.exception.ApiException;

public class AccountTest {
private LinodeApi linodeApi;
	
	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));
	}
	
	@Test
	public void testInvalidEstimateInvoice() throws ApiException {
		LinodeApiRequest linodeRequest = Account.estimateinvoice("linode_new");
		LinodeApiResponse linodeResponse = linodeApi.execute(linodeRequest);
		Assert.assertEquals(1, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testInfo() throws ApiException {
		LinodeApiRequest linodeRequest = Account.info();
		LinodeApiResponse linodeResponse = linodeApi.execute(linodeRequest);
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

}
