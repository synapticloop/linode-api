package synapticloop.linode;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.response.bean.Datacenter;
import synapticloop.linode.api.response.bean.Distribution;
import synapticloop.linode.api.response.bean.Kernel;
import synapticloop.linode.api.response.bean.LinodePlan;
import synapticloop.linode.exception.ApiException;

public class LinodeApiHighLevel extends LinodeApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeApiHighLevel.class);
	private List<Datacenter> datacenterCache = null;
	private List<Kernel> kernelCache = null;
	private List<LinodePlan> linodePlanCache = null;
	private List<Distribution> distributionCache = null;

	public LinodeApiHighLevel(String apiKey) {
		super(apiKey, false);
	}

	public LinodeApiHighLevel(String apiKey, boolean debug) {
		super(apiKey, debug);
	}

	public void createAndBootLinode(Long idDatacenter, Long idPlan, Long idDistribution) {
		
	}

	public synchronized List<Distribution> getDistributions() throws ApiException {
		if(null == distributionCache) {
			this.distributionCache = getAvailDistributions().getDistributions();
		}

		return(distributionCache);
	}

	/**
	 * Get a list of all of the available datacenters.  Note that this caches the
	 * first response, should you wish to refresh the caches, call clearAllCaches
	 * first, which will clear all caches.
	 * 
	 * @return the list of datacenters
	 * @throws ApiException if there was an error with the call
	 */
	public synchronized List<Datacenter> getDatacenters() throws ApiException {
		if(null == datacenterCache) {
			this.datacenterCache = getAvailDatacenters().getDatacenters();
		}

		return(datacenterCache);
	}

	
	public void clearAllCaches() {
		this.datacenterCache = null;
		this.distributionCache = null;
		this.kernelCache = null;
		this.linodePlanCache = null;
	}
}
