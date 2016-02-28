package synapticloop.linode.api.request;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeApiResponse;
import synapticloop.linode.exception.ApiException;

public class NodebalancerRequestTest {
	private LinodeApi linodeApi = null;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"), false);
	}

	@Test
	public void testList() throws ApiException {
		LinodeApiResponse linodeResponse = linodeApi.execute(NodebalancerRequest.list());
		System.out.println(linodeResponse.getJSON().toString(2));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(NodebalancerRequest.list(-1l));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(NodebalancerRequest.list(-1l));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}
}
