package synapticloop.linode.api.request;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeApiRequest;
import synapticloop.linode.LinodeApiResponse;
import synapticloop.linode.exception.ApiException;

public class AccountRequestTest {
private LinodeApi linodeApi;
	
	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));
	}
	
	@Test
	public void testInvalidEstimateInvoice() throws ApiException {
		LinodeApiRequest linodeRequest = AccountRequest.estimateinvoice("linode_new");
		LinodeApiResponse linodeResponse = linodeApi.execute(linodeRequest);
		Assert.assertEquals(1, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testInfo() throws ApiException {
		LinodeApiRequest linodeRequest = AccountRequest.info();
		LinodeApiResponse linodeResponse = linodeApi.execute(linodeRequest);
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

}
