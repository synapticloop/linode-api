package synapticloop.linode.api.request;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeApiResponse;
import synapticloop.linode.exception.ApiException;

public class DomainRequestTest {
	private LinodeApi linodeApi = null;


	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"), true);
	}

	@Test
	public void testDomainList() throws Exception, ApiException {
		LinodeApiResponse linodeResponse = linodeApi.execute(DomainRequest.list());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}
}
