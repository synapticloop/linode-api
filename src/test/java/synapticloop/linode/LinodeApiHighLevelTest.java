package synapticloop.linode;

import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.exception.ApiException;

public class LinodeApiHighLevelTest {
	private LinodeApiHighLevel linodeApiHighLevel;

	@Before
	public void setup() {
		linodeApiHighLevel = new LinodeApiHighLevel(System.getenv("LINODE_API_KEY"));
	}

	@Test
	public void testLinodeCreateAndDestroy() throws ApiException {
		Long linodeId = linodeApiHighLevel.createLinode(DatacenterSlug.DALLAS_TX_USA, 
				PlanSlug.LINODE_1024,
				DistributionSlug.UBUNTU_14_04_LTS,
				KernelSlug.KERNEL_LATEST_64_BIT_4_4_0_X86_64_LINODE63_,
				"LINODE-API-HIGH-LEVEL", 
				"^&*678yuiYUI");
		linodeApiHighLevel.destroyLinode(linodeId);
	}

	@Test
	public void testLinodeNodeBalancer() throws ApiException {
		Long linodeIdOne = linodeApiHighLevel.createLinode(DatacenterSlug.DALLAS_TX_USA, 
				PlanSlug.LINODE_1024,
				DistributionSlug.UBUNTU_14_04_LTS,
				KernelSlug.KERNEL_LATEST_64_BIT_4_4_0_X86_64_LINODE63_,
				"LINODE-API-HIGH-LEVEL-1", 
				"^&*678yuiYUI");
		Long linodeIdTwo = linodeApiHighLevel.createLinode(DatacenterSlug.DALLAS_TX_USA, 
				PlanSlug.LINODE_1024,
				DistributionSlug.UBUNTU_14_04_LTS,
				KernelSlug.KERNEL_LATEST_64_BIT_4_4_0_X86_64_LINODE63_,
				"LINODE-API-HIGH-LEVEL-2", 
				"^&*678yuiYUI");


		linodeApiHighLevel.destroyLinode(linodeIdOne);
		linodeApiHighLevel.destroyLinode(linodeIdTwo);
	}

}
