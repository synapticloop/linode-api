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
		Long linodeId = linodeApiHighLevel.createLinode(TestHelper.getDatacenterId(), 
				TestHelper.getPlanId(), 
				TestHelper.getUbuntuDistribution(), 
				"LINODE-API-HIGH-LEVEL", 
				"^&*678yuiYUI", 
				true);
		linodeApiHighLevel.destroyLinode(linodeId);
	}

	@Test
	public void testLinodeNodeBalancer() throws ApiException {
		Long linodeIdOne = linodeApiHighLevel.createLinode(TestHelper.getDatacenterId(), 
				TestHelper.getPlanId(), 
				TestHelper.getUbuntuDistribution(), 
				"LINODE-API-HIGH-LEVEL-1", 
				"^&*678yuiYUI", 
				true);
		Long linodeIdTwo = linodeApiHighLevel.createLinode(TestHelper.getDatacenterId(), 
				TestHelper.getPlanId(), 
				TestHelper.getUbuntuDistribution(), 
				"LINODE-API-HIGH-LEVEL-2", 
				"^&*678yuiYUI", 
				true);

		
		linodeApiHighLevel.destroyLinode(linodeIdOne);
		linodeApiHighLevel.destroyLinode(linodeIdTwo);
	}

}
