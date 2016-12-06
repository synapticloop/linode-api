package synapticloop.linode;

import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.api.response.ApiSpecResponse;
import synapticloop.linode.exception.ApiException;

public class LinodeApiApiSpecTest {
	private LinodeApi linodeApi;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));
	}

	@Test
	public void testMethodName() throws ApiException {
		ApiSpecResponse apiSpec = linodeApi.getApiSpec();
	}

}
