package synapticloop.linode;

import java.util.List;

import synapticloop.linode.api.response.bean.Distribution;
import synapticloop.linode.exception.ApiException;

public class TestHelper {
	private static LinodeApiFacade linodeApiHighLevel = new LinodeApiFacade(System.getenv("LINODE_API_KEY"));

	private static Long ubuntuDistributionId = null;
	private static Long defaultPlanId = null;
	private static Long defaultDatacenterId = null;

	public static Long getUbuntuDistribution() throws ApiException {
		if(null == ubuntuDistributionId) {
			List<Distribution> distributions = linodeApiHighLevel.getDistributions();
			for (Distribution distribution : distributions) {
				if(distribution.getLabel().contains("Ubuntu")) {
					ubuntuDistributionId = distribution.getDistributionId();
					break;
				}
			}
		}
		return(ubuntuDistributionId);
	}

	public static Long getPlanId() throws ApiException {
		if(null == defaultPlanId) {
			defaultPlanId = linodeApiHighLevel.getLinodePlans().get(0).getPlanId();
		}

		return(defaultPlanId);
	}

	public static Long getDatacenterId() throws ApiException {
		if(null == defaultDatacenterId) {
			defaultDatacenterId = linodeApiHighLevel.getDatacenters().get(0).getDatacenterId();
		}

		return(defaultDatacenterId);
	}
}
