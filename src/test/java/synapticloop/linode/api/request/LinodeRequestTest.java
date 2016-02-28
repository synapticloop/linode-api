package synapticloop.linode.api.request;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeApiResponse;
import synapticloop.linode.exception.ApiException;

public class LinodeRequestTest {
	private LinodeApi linodeApi = null;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"), false);
	}

	@Test
	public void testList() throws ApiException {
		LinodeApiResponse linodeResponse = linodeApi.execute(LinodeRequest.iplist());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(LinodeRequest.list(-1l));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(LinodeRequest.list());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}
}
