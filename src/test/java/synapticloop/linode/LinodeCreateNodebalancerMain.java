package synapticloop.linode;

import synapticloop.linode.api.response.NodebalancerResponse;
import synapticloop.linode.exception.ApiException;

public class LinodeCreateNodebalancerMain {

	public static void main(String[] args) {
		try {
			// create the linodeApi
			LinodeApiFacade linodeApiFacade = new LinodeApiFacade(System.getenv("LINODE_API_KEY"));
			LinodeApi linodeApi = linodeApiFacade.getLinodeApi();

			// create a node balancer - note that node balancer names __MUST__ be unique
			NodebalancerResponse nodebalancerCreateResponse = linodeApi.getNodebalancerCreate(TestHelper.getDatacenterId(), 
					"LINODE-API-TEST-" + System.currentTimeMillis(), 
					0l);

			// create a node balance config - this is all defaults - port 80
			Long nodebalancerConfigId = linodeApi.getNodebalancerConfigCreate(nodebalancerCreateResponse.getNodebalancerId()).getConfigId();

			Long linodeIdOne = linodeApiFacade.createLinode(DatacenterSlug.DALLAS_TX_USA, 
					PlanSlug.LINODE_2048,
					DistributionSlug.UBUNTU_16_04_LTS,
					KernelSlug.KERNEL_LATEST_64_BIT_4_9_7_X86_64_LINODE80_,
					"NODE-1", 
					"^&*678yuiYUI");

			Long linodeIdTwo = linodeApiFacade.createLinode(DatacenterSlug.DALLAS_TX_USA, 
					PlanSlug.LINODE_2048,
					DistributionSlug.UBUNTU_16_04_LTS,
					KernelSlug.KERNEL_LATEST_64_BIT_4_9_7_X86_64_LINODE80_,
					"NODE-2", 
					"^&*678yuiYUI");

			// you may only node balance between private IP addresses - so
			// attach a private ip address to each of the linodes
			String ipAddressOne = linodeApi.getLinodeIpAddressPrivate(linodeIdOne).getIpAddress();
			String ipAddressTwo = linodeApi.getLinodeIpAddressPrivate(linodeIdTwo).getIpAddress();

			// create the two nodes - note the port is appended to the private ip address
			Long nodeIdOne = linodeApi.getNodebalancerNodeCreate(nodebalancerConfigId, "Node-1-config", ipAddressOne + ":80").getNodeId();
			Long nodeIdTwo = linodeApi.getNodebalancerNodeCreate(nodebalancerConfigId, "Node-2-config", ipAddressTwo + ":80").getNodeId();
		} catch(ApiException ex) {
			ex.printStackTrace();
		}
	}
}
