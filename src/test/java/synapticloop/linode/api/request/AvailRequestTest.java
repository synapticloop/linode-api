package synapticloop.linode.api.request;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeApiRequest;
import synapticloop.linode.LinodeApiResponse;
import synapticloop.linode.exception.ApiException;

public class AvailRequestTest {
	private LinodeApi linodeApi = null;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"), false);
	}

	@Test
	public void testBatchAvailable() throws ApiException {
		List<LinodeApiRequest> linodeRequests = new ArrayList<LinodeApiRequest>();
		linodeRequests.add(AvailRequest.datacenters());
		linodeRequests.add(AvailRequest.distributions());

		List<LinodeApiResponse> linodeResponses = linodeApi.execute(linodeRequests);
		for (LinodeApiResponse linodeResponse : linodeResponses) {
			Assert.assertEquals(0, linodeResponse.getErrorArray().length());
		}
	}

	@Test
	public void testAvailableDatacentres() throws ApiException {
		LinodeApiResponse linodeResponse = linodeApi.execute(AvailRequest.datacenters());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testAvailableDistributions() throws ApiException, JSONException {
		LinodeApiResponse linodeResponse = linodeApi.execute(AvailRequest.distributions());
		long distributionID = linodeResponse.getDataAsJSONArray().getJSONObject(0).getLong("DISTRIBUTIONID");
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		// test distributions with distributon id
		linodeResponse = linodeApi.execute(AvailRequest.distributions(distributionID));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testInvalidDatacentre() throws ApiException, JSONException {
		// test distributions with distributon id
		LinodeApiResponse linodeResponse = linodeApi.execute(AvailRequest.distributions(-109809809l));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testAvailableKernels() throws ApiException {
		LinodeApiResponse linodeResponse = linodeApi.execute(AvailRequest.kernels());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(AvailRequest.kernels(true, true));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(AvailRequest.kernels(true, false));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(AvailRequest.kernels(false, true));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(AvailRequest.kernels(false, false));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testLinodePlans() throws ApiException, JSONException {
		LinodeApiResponse linodeResponse = linodeApi.execute(AvailRequest.linodeplans());
		Assert.assertFalse(linodeResponse.getIsDataJSONObject());
		Assert.assertTrue(linodeResponse.getIsDataJSONArray());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(AvailRequest.linodeplans(1l));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testNodeBalancers() throws ApiException {
		LinodeApiResponse linodeResponse = linodeApi.execute(AvailRequest.nodebalancers());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testStackScripts() throws ApiException, JSONException {
		LinodeApiResponse linodeResponse = linodeApi.execute(AvailRequest.stackscripts());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());

		linodeResponse = linodeApi.execute(AvailRequest.stackscripts(null, null, "Install"));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

}
