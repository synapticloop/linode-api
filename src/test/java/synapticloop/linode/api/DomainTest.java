package synapticloop.linode.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeResponse;
import synapticloop.linode.exception.ApiException;

public class DomainTest {
	private LinodeApi linodeApi = null;


	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"), true);
	}

	@Test
	public void testDomainList() throws Exception, ApiException {
		LinodeResponse linodeResponse = linodeApi.execute(Domain.list());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}
}
