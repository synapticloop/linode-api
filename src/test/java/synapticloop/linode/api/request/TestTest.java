package synapticloop.linode.api.request;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeResponse;
import synapticloop.linode.exception.ApiException;

public class TestTest {
	private LinodeApi linodeApi = null;


	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"), true);
	}

	@Test
	public void testEcho() throws ApiException, JSONException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("foo", "bar");
		parameters.put("one", "two");
		LinodeResponse linodeResponse = linodeApi.execute(synapticloop.linode.api.request.Test.echo(parameters));

		Assert.assertTrue(linodeResponse.getIsDataJSONObject());
		Assert.assertFalse(linodeResponse.getIsDataJSONArray());

		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
		// TODO THIS IS INCORRECT - THERE IS AN ERROR IN THE LINODE API...
		Assert.assertEquals("bar", linodeResponse.getDataAsJSONObject().getString("FOO"));

	}
}
