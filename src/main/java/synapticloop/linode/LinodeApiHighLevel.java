package synapticloop.linode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinodeApiHighLevel extends LinodeApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeApiHighLevel.class);

	public LinodeApiHighLevel(String apiKey) {
		super(apiKey, false);
	}

	public LinodeApiHighLevel(String apiKey, boolean debug) {
		super(apiKey, debug);
	}

	public void createAndBootLinode(Long idDatacenter, Long idPlan, Long idDistribution) {
		
	}
}
