package synapticloop.linode;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.api.response.AvailDatacentersResponse;
import synapticloop.linode.api.response.AvailDistributionsResponse;
import synapticloop.linode.api.response.AvailKernelsResponse;
import synapticloop.linode.api.response.bean.Distribution;
import synapticloop.linode.api.response.bean.Kernel;
import synapticloop.linode.exception.ApiException;

public class LinodeApiAvailTest {
	private LinodeApi linodeApi;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));
	}

	@Test
	public void testAvailDatacenters() throws ApiException {
		AvailDatacentersResponse availDatacenters = linodeApi.getAvailDatacenters();
		assertTrue(availDatacenters.getDatacenters().size() > 1);
		assertFalse(availDatacenters.hasErrors());
	}

	@Test
	public void testAvailDistributions() throws ApiException {
		AvailDistributionsResponse availDistributions = linodeApi.getAvailDistributions();
		List<Distribution> distributions = availDistributions.getDistributions();
		assertTrue(distributions.size() > 1);
		assertFalse(availDistributions.hasErrors());
	}

	@Test
	public void testAvailKernels() throws ApiException {
		AvailKernelsResponse availKernels = linodeApi.getAvailKernels();
		List<Kernel> kernels = availKernels.getKernels();
		assertTrue(kernels.size() > 1);
		assertFalse(availKernels.hasErrors());
	}
}
