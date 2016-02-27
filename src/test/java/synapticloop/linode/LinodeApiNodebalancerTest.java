package synapticloop.linode;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.api.response.NodebalancerResponse;
import synapticloop.linode.api.response.bean.IPAddress;
import synapticloop.linode.exception.ApiException;

public class LinodeApiNodebalancerTest {
	private LinodeApi linodeApi;
	private LinodeApiHighLevel linodeApiHighLevel;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));
		linodeApiHighLevel = new LinodeApiHighLevel(System.getenv("LINODE_API_KEY"));
	}

	@Test
	public void testNodebalancerCreateAndDestroy() throws ApiException {
		NodebalancerResponse nodebalancerCreateResponse = linodeApi.getNodebalancerCreate(TestHelper.getDatacenterId(), "LINODE-API-TEST", 0l);
		assertFalse(nodebalancerCreateResponse.hasErrors());

		NodebalancerResponse nodebalancerDeleteResponse = linodeApi.getNodebalancerDelete(nodebalancerCreateResponse.getNodebalancerId());
		assertFalse(nodebalancerDeleteResponse.hasErrors());
	}

	@Test
	public void testNodebalancerCreateAndConfigure() throws ApiException {
		NodebalancerResponse nodebalancerCreateResponse = linodeApi.getNodebalancerCreate(TestHelper.getDatacenterId(), "LINODE-API-TEST-" + System.currentTimeMillis(), 0l);
		assertFalse(nodebalancerCreateResponse.hasErrors());

		Long nodebalancerId = nodebalancerCreateResponse.getNodebalancerId();

		Long nodebalanceConfigId = linodeApi.getNodebalancerConfigCreate(nodebalancerId).getConfigId();

		Long linodeIdOne = linodeApiHighLevel.createLinode(TestHelper.getDatacenterId(), TestHelper.getPlanId(), TestHelper.getUbuntuDistribution(), "NODE-1", "^&*678yuiYUI", true);
		Long linodeIdTwo = linodeApiHighLevel.createLinode(TestHelper.getDatacenterId(), TestHelper.getPlanId(), TestHelper.getUbuntuDistribution(), "NODE-2", "^&*678yuiYUI", true);

		linodeApi.getLinodeIpAddressPrivate(linodeIdOne);
		linodeApi.getLinodeIpAddressPrivate(linodeIdTwo);

		List<IPAddress> ipAddresses = linodeApi.getLinodeIpList(linodeIdOne, null).getIpAddresses();
		String ipAddressOne = null;
		for (IPAddress ipAddress : ipAddresses) {
			if(!ipAddress.getIsPublic()) {
				ipAddressOne = ipAddress.getIpAddress();
			}
		}

		ipAddresses = linodeApi.getLinodeIpList(linodeIdTwo, null).getIpAddresses();
		String ipAddressTwo = null;
		for (IPAddress ipAddress : ipAddresses) {
			if(!ipAddress.getIsPublic()) {
				ipAddressTwo = ipAddress.getIpAddress();
			}
		}

		linodeApi.getNodebalancerNodeCreate(nodebalanceConfigId, "Node-1-config", ipAddressOne + ":80");
		linodeApi.getNodebalancerNodeCreate(nodebalanceConfigId, "Node-2-config", ipAddressTwo + ":80");

		linodeApi.getNodebalancerDelete(nodebalancerId);

		linodeApiHighLevel.destroyLinode(linodeIdOne);
		linodeApiHighLevel.destroyLinode(linodeIdTwo);

	}

}
