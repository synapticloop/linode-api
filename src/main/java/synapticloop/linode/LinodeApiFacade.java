package synapticloop.linode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.response.bean.Datacenter;
import synapticloop.linode.api.response.bean.Distribution;
import synapticloop.linode.api.response.bean.Kernel;
import synapticloop.linode.api.response.bean.LinodePlan;
import synapticloop.linode.exception.ApiException;

public class LinodeApiFacade {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeApiFacade.class);

	private List<Datacenter> datacenterCache = null;
	private List<Kernel> kernelCache = null;
	private List<LinodePlan> linodePlanCache = null;
	private Map<Long, LinodePlan> linodePlanCacheMap = new HashMap<Long, LinodePlan>();
	private List<Distribution> distributionCache = null;

	private Kernel default64BitKernel = null;
	private Kernel default32BitKernel = null;

	private LinodeApi linodeApi = null;

	private boolean isInitialised = false;

	public LinodeApiFacade(String apiKey) {
		this(apiKey, false);
	}

	public LinodeApiFacade(String apiKey, boolean debug) {
		this.linodeApi = new LinodeApi(apiKey, debug);
	}

	/**
	 * Create and boot a linode in a datacenter, for a specific plan, using a
	 * specific distribution and kernel.
	 * 
	 * @param datacenterId the id of the datacenter to launch the linode in
	 * @param planId the id of the linode plan 
	 * @param distributionId the distribution that fills the root disk
	 * @param kernelId the kernel to use
	 * @param label the label for this linode
	 * @param rootPassword the root password
	 * 
	 * @return the id of the linode that was created
	 * 
	 * @throws ApiException if there was an error creating the linode
	 */
	public Long createLinode(long datacenterId, long planId, long distributionId, long kernelId, String label, String rootPassword) throws ApiException {
		initialise();
		Long linodeId = linodeApi.getLinodeCreate(datacenterId, planId).getLinodeId();

		Long rootDiskId = linodeApi.getLinodeDiskCreateFromDistribution(linodeId, 
				distributionId, 
				label + "-root-disk", 
				(linodePlanCacheMap.get(planId).getDiskSize() * 1024) - 256,
				rootPassword).getDiskId();

		// create a swap disk - NOTE that you MUST have two disks
		Long swapDiskId = linodeApi.getLinodeDiskCreate(linodeId, 
				label + "-swap-disk", 
				"swap", 256l).getDiskId();

		Long configId = linodeApi.getLinodeConfigCreate(linodeId, 
				kernelId, 
				label, 
				Long.toString(rootDiskId) + "," + Long.toString(swapDiskId)).getConfigId();

		// now boot the machine
		linodeApi.getLinodeBoot(linodeId, configId);

		return(linodeId);
	}

	/**
	 * Create and boot a linode in a datacenter, for a specific plan, using a
	 * specific distribution and kernel.
	 * 
	 * @param datacenter the id of the datacenter to launch the linode in
	 * @param plan the id of the linode plan 
	 * @param distribution the distribution that fills the root disk
	 * @param kernel the kernel to use
	 * @param label the label for this linode
	 * @param rootPassword the root password
	 * 
	 * @return the id of the linode that was created
	 * 
	 * @throws ApiException if there was an error creating the linode
	 */
	public Long createLinode(DatacenterSlug datacenter, PlanSlug plan, DistributionSlug distribution, 
			KernelSlug kernel, String label, String rootPassword) throws ApiException {
		return(createLinode(datacenter.datacenterId(), plan.planId(), distribution.distributionId(), kernel.kernelId(), label, rootPassword));
	}

	public void destroyLinode(Long linodeId) throws ApiException {
		linodeApi.getLinodeDelete(linodeId, true);
	}

	public synchronized List<Distribution> getDistributions() throws ApiException {
		if(null == distributionCache) {
			this.distributionCache = linodeApi.getAvailDistributions().getDistributions();
		}

		return(distributionCache);
	}

	/**
	 * Get a list of all of the available datacenters.  Note that this caches the
	 * first response, should you wish to refresh the caches, call resetAllCaches
	 * first, which will clear all caches.
	 * 
	 * @return the list of datacenters
	 * @throws ApiException if there was an error with the call
	 */
	public synchronized List<Datacenter> getDatacenters() throws ApiException {
		if(null == datacenterCache) {
			this.datacenterCache = linodeApi.getAvailDatacenters().getDatacenters();
		}

		return(datacenterCache);
	}

	public synchronized List<Kernel> getKernels() throws ApiException {
		if(null == kernelCache) {
			this.kernelCache = linodeApi.getAvailKernels().getKernels();
			for (Kernel kernel : kernelCache) {
				if(kernel.getLabel().contains("Latest 64 bit")) {
					this.default64BitKernel = kernel;
				} else if(kernel.getLabel().contains("Latest 32 bit")) {
					this.default32BitKernel = kernel;
				}
			}
		}

		return(kernelCache);
	}

	public synchronized List<LinodePlan> getLinodePlans() throws ApiException {
		if(null == linodePlanCache) {
			this.linodePlanCache = linodeApi.getAvailLinodePlans().getLinodePlans();
			linodePlanCacheMap.clear();
			for (LinodePlan linodePlan : linodePlanCache) {
				linodePlanCacheMap.put(linodePlan.getPlanId(), linodePlan);
			}
		}

		return(linodePlanCache);
	}

	/**
	 * Clear all the caches i.e. the availability calls (apart from avail 
	 * stackscripts) and then re-initialise them.
	 * 
	 * @throws ApiException if there was an error with the cache refreshing
	 */
	public synchronized void resetAllCaches() throws ApiException {
		this.datacenterCache = null;
		this.distributionCache = null;
		this.kernelCache = null;
		this.linodePlanCache = null;
		getDatacenters();
		getDistributions();
		getKernels();
		getLinodePlans();
	}

	private synchronized void initialise() throws ApiException {
		if(isInitialised) {
			return;
		}

		this.isInitialised = true;
		resetAllCaches();
	}
}
