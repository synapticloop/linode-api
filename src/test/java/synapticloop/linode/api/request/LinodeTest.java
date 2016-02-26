package synapticloop.linode.api.request;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeResponse;
import synapticloop.linode.exception.ApiException;

public class LinodeTest {
	private LinodeApi linodeApi = null;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"), false);
	}

	@Test
	public void testList() throws ApiException {
		LinodeResponse linodeResponse = linodeApi.execute(Linode.iplist());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(Linode.list(-1l));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(Linode.list());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}
}
