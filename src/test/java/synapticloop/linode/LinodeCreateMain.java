package synapticloop.linode;

import java.util.List;

import synapticloop.linode.api.response.LinodeConfigResponse;
import synapticloop.linode.api.response.LinodeJobResponse;
import synapticloop.linode.api.response.bean.Datacenter;
import synapticloop.linode.api.response.bean.Distribution;
import synapticloop.linode.api.response.bean.Kernel;
import synapticloop.linode.api.response.bean.LinodePlan;
import synapticloop.linode.exception.ApiException;

public class LinodeCreateMain {

	public static void main(String[] args) {
		// create the linodeApi
		LinodeApi linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));

		try {
			// get the first available datacenter
			Datacenter datacenter = linodeApi.getAvailDatacenters()
					.getDatacenters()
					.get(0);

			// get the first available plan
			LinodePlan linodePlan = linodeApi.getAvailLinodePlans()
					.getLinodePlans()
					.get(0);

			// get the kernel
			Kernel kernel = null;
			List<Kernel> kernels = linodeApi.getAvailKernels()
					.getKernels();

			for (Kernel theKernel : kernels) {
				// need a 64 bit one
				if(theKernel.getLabel().contains("Latest 64 bit")) {
					kernel = theKernel;
					break;
				}
			}

			// get the distribution
			Distribution distribution = null;
			List<Distribution> distributions = linodeApi.getAvailDistributions()
					.getDistributions();

			for (Distribution theDistribution : distributions) {
				// I want an ubuntu distribution
				if(theDistribution.getLabel().contains("Ubuntu")) {
					distribution = theDistribution;
					break;
				}
			}

			// create a linode
			Long linodeId = linodeApi.getLinodeCreate(datacenter.getDatacenterId(), linodePlan.getPlanId()).getLinodeId();

			// create the root disk from the distribution
			Long diskId = linodeApi.getLinodeDiskCreateFromDistribution(linodeId, 
					distribution.getDistributionId(), 
					"LINODE-API-DISK-ROOT", 
					1024l, 
					"^&*678yuiYUI").getDiskId();

			// create a swap disk - NOTE that you MUST have two disks
			Long swapDiskId = linodeApi.getLinodeDiskCreate(linodeId, 
					"LINODE-API-DISK-SWAP", 
					"swap", 256l).getDiskId();


			// now create the configuration
			LinodeConfigResponse linodeConfigCreateResponse = linodeApi.getLinodeConfigCreate(linodeId, 
					kernel.getKernelId(), 
					"LINODE-API-TEST", 
					Long.toString(diskId) + "," + Long.toString(swapDiskId));

			// now boot the machine
			LinodeJobResponse linodeBootResponse = linodeApi.getLinodeBoot(linodeId, 
					linodeConfigCreateResponse.getConfigId());

		} catch (ApiException ex) {
			ex.printStackTrace();
		}

	}

}
