package synapticloop.linode.api.request;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeRequest;
import synapticloop.linode.LinodeResponse;
import synapticloop.linode.exception.ApiException;

public class AvailTest {
	private LinodeApi linodeApi = null;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"), false);
	}

	@Test
	public void testBatchAvailable() throws ApiException {
		List<LinodeRequest> linodeRequests = new ArrayList<LinodeRequest>();
		linodeRequests.add(Avail.datacenters());
		linodeRequests.add(Avail.distributions());

		List<LinodeResponse> linodeResponses = linodeApi.execute(linodeRequests);
		for (LinodeResponse linodeResponse : linodeResponses) {
			Assert.assertEquals(0, linodeResponse.getErrorArray().length());
		}
	}

	@Test
	public void testAvailableDatacentres() throws ApiException {
		LinodeResponse linodeResponse = linodeApi.execute(Avail.datacenters());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testAvailableDistributions() throws ApiException, JSONException {
		LinodeResponse linodeResponse = linodeApi.execute(Avail.distributions());
		long distributionID = linodeResponse.getDataAsJSONArray().getJSONObject(0).getLong("DISTRIBUTIONID");
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		// test distributions with distributon id
		linodeResponse = linodeApi.execute(Avail.distributions(distributionID));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testInvalidDatacentre() throws ApiException, JSONException {
		// test distributions with distributon id
		LinodeResponse linodeResponse = linodeApi.execute(Avail.distributions(-109809809l));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testAvailableKernels() throws ApiException {
		LinodeResponse linodeResponse = linodeApi.execute(Avail.kernels());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(Avail.kernels(true, true));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(Avail.kernels(true, false));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(Avail.kernels(false, true));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(Avail.kernels(false, false));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testLinodePlans() throws ApiException, JSONException {
		LinodeResponse linodeResponse = linodeApi.execute(Avail.linodeplans());
		Assert.assertFalse(linodeResponse.getIsDataJSONObject());
		Assert.assertTrue(linodeResponse.getIsDataJSONArray());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(Avail.linodeplans(1l));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testNodeBalancers() throws ApiException {
		LinodeResponse linodeResponse = linodeApi.execute(Avail.nodebalancers());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testStackScripts() throws ApiException, JSONException {
		LinodeResponse linodeResponse = linodeApi.execute(Avail.stackscripts());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(Avail.stackscripts(null, null, "Install"));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

}
