package synapticloop.linode;

import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.exception.ApiException;

public class LinodeApiFacadeTest {
	private LinodeApiFacade linodeApiFacade;

	@Before
	public void setup() {
		linodeApiFacade = new LinodeApiFacade(System.getenv("LINODE_API_KEY"));
	}

	@Test
	public void testLinodeCreateAndDestroy() throws ApiException {
		Long linodeId = linodeApiFacade.createLinode(DatacenterSlug.DALLAS_TX_USA, 
				PlanSlug.LINODE_2048,
				DistributionSlug.UBUNTU_16_04_LTS,
				KernelSlug.KERNEL_LATEST_64_BIT_4_8_6_X86_64_LINODE78_,
				"LINODE-API-HIGH-LEVEL", 
				"^&*678yuiYUI");
		linodeApiFacade.destroyLinode(linodeId);
	}

	@Test
	public void testLinodeNodeBalancer() throws ApiException {
		Long linodeIdOne = linodeApiFacade.createLinode(DatacenterSlug.DALLAS_TX_USA, 
				PlanSlug.LINODE_2048,
				DistributionSlug.UBUNTU_16_04_LTS,
				KernelSlug.KERNEL_LATEST_64_BIT_4_8_6_X86_64_LINODE78_,
				"LINODE-API-HIGH-LEVEL-1", 
				"^&*678yuiYUI");
		Long linodeIdTwo = linodeApiFacade.createLinode(DatacenterSlug.DALLAS_TX_USA, 
				PlanSlug.LINODE_2048,
				DistributionSlug.UBUNTU_16_04_LTS,
				KernelSlug.KERNEL_LATEST_64_BIT_4_8_6_X86_64_LINODE78_,
				"LINODE-API-HIGH-LEVEL-2", 
				"^&*678yuiYUI");


		linodeApiFacade.destroyLinode(linodeIdOne);
		linodeApiFacade.destroyLinode(linodeIdTwo);
	}

}
