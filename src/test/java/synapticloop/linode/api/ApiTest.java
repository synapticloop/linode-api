package synapticloop.linode.api;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeResponse;
import synapticloop.linode.exception.ApiException;

public class ApiTest {
	private LinodeApi linodeApi = null;


	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"), true);
	}

	@Test
	public void testMethodName() throws ApiException, JSONException {
		LinodeResponse linodeResponse = linodeApi.execute(Api.spec());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

}
