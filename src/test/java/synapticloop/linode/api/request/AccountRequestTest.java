package synapticloop.linode.api.request;

import static org.junit.Assert.*;

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
		try {
			LinodeApiRequest linodeRequest = AccountRequest.estimateinvoice("linode_new");
			LinodeApiResponse linodeResponse = linodeApi.execute(linodeRequest);
		} catch(ApiException ex) {
			// this is expected
			System.out.println(ex.getMessage());
		}
	}

	@Test
	public void testInfo() throws ApiException {
		LinodeApiRequest linodeRequest = AccountRequest.info();
		LinodeApiResponse linodeResponse = linodeApi.execute(linodeRequest);
		assertEquals(0, linodeResponse.getErrorArray().length());
	}

}
