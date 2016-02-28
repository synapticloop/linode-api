package synapticloop.linode.api.request;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeApiResponse;
import synapticloop.linode.exception.ApiException;

public class ApiRequestTest {
	private LinodeApi linodeApi = null;


	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"), true);
	}

	@Test
	public void testMethodName() throws ApiException, JSONException {
		LinodeApiResponse linodeResponse = linodeApi.execute(ApiRequest.spec());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

}
