package synapticloop.linode;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.api.response.NodebalancerResponse;
import synapticloop.linode.api.response.bean.IPAddress;
import synapticloop.linode.api.response.bean.NodeBalancer;
import synapticloop.linode.api.response.bean.NodeBalancerConfig;
import synapticloop.linode.api.response.bean.NodeBalancerNode;
import synapticloop.linode.exception.ApiException;

public class LinodeApiNodebalancerTest {
	private LinodeApi linodeApi;
	private LinodeApiFacade linodeApiHighLevel;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));
		linodeApiHighLevel = new LinodeApiFacade(System.getenv("LINODE_API_KEY"));
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

		List<NodeBalancer> nodeBalancers = linodeApi.getNodebalancerList(nodebalancerId).getNodeBalancers();
		boolean found = false;
		for (NodeBalancer nodeBalancer : nodeBalancers) {
			if(nodeBalancer.getNodebalancerId().equals(nodebalancerId)) {
				found = true;
			}
		}
		assertTrue(found);

		Long nodebalancerConfigId = linodeApi.getNodebalancerConfigCreate(nodebalancerId).getConfigId();

		Long linodeIdOne = linodeApiHighLevel.createLinode(DatacenterSlug.DALLAS_TX_USA, 
				PlanSlug.LINODE_1024,
				DistributionSlug.UBUNTU_14_04_LTS,
				KernelSlug.KERNEL_LATEST_64_BIT_4_4_0_X86_64_LINODE63_,
				"NODE-1", "^&*678yuiYUI");
		Long linodeIdTwo = linodeApiHighLevel.createLinode(DatacenterSlug.DALLAS_TX_USA, 
				PlanSlug.LINODE_1024,
				DistributionSlug.UBUNTU_14_04_LTS,
				KernelSlug.KERNEL_LATEST_64_BIT_4_4_0_X86_64_LINODE63_,
				"NODE-2", "^&*678yuiYUI");

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

		Long nodeIdOne = linodeApi.getNodebalancerNodeCreate(nodebalancerConfigId, "Node-1-config", ipAddressOne + ":80").getNodeId();
		Long nodeIdTwo = linodeApi.getNodebalancerNodeCreate(nodebalancerConfigId, "Node-2-config", ipAddressTwo + ":80").getNodeId();

		// before we do this - ensure that we are getting the lists back
		List<NodeBalancerConfig> nodeBalancerConfigs = linodeApi.getNodebalancerConfigList(nodebalancerId).getNodeBalancerConfigs();
		found = false;
		for (NodeBalancerConfig nodeBalancerConfig : nodeBalancerConfigs) {
			if(nodeBalancerConfig.getConfigId().equals(nodebalancerConfigId)) {
				found = true;
			}
		}
		assertTrue(found);

		List<NodeBalancerNode> nodeBalancerNodes = linodeApi.getNodebalancerNodeList(nodebalancerConfigId).getNodeBalancerNodes();
		boolean foundOne = false;
		boolean foundTwo = false;
		for (NodeBalancerNode nodeBalancerNode : nodeBalancerNodes) {
			if(nodeBalancerNode.getNodeId().equals(nodeIdOne)) {
				foundOne = true;
			} else if(nodeBalancerNode.getNodeId().equals(nodeIdTwo)) {
				foundTwo = true;
			}
		}

		assertTrue(foundOne && foundTwo);

		linodeApi.getNodebalancerDelete(nodebalancerId);

		linodeApiHighLevel.destroyLinode(linodeIdOne);
		linodeApiHighLevel.destroyLinode(linodeIdTwo);

	}

}
