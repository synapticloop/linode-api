package synapticloop.linode;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.api.response.LinodeConfigResponse;
import synapticloop.linode.api.response.LinodeDiskResponse;
import synapticloop.linode.api.response.LinodeJobResponse;
import synapticloop.linode.api.response.LinodeResponse;
import synapticloop.linode.api.response.bean.Datacenter;
import synapticloop.linode.api.response.bean.Disk;
import synapticloop.linode.api.response.bean.Distribution;
import synapticloop.linode.api.response.bean.Job;
import synapticloop.linode.api.response.bean.Kernel;
import synapticloop.linode.api.response.bean.Linode;
import synapticloop.linode.api.response.bean.LinodePlan;
import synapticloop.linode.exception.ApiException;

public class LinodeApiLinodeCreateTest {
	private LinodeApi linodeApi = null;
	private Datacenter datacenter = null;
	private LinodePlan linodePlan = null;
	private Kernel kernel;
	private Distribution distribution;

	@Before
	public void setup() throws ApiException {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));

		this.datacenter = linodeApi.getAvailDatacenters().getDatacenters().get(0);
		this.linodePlan = linodeApi.getAvailLinodePlans().getLinodePlans().get(0);
		List<Kernel> kernels = linodeApi.getAvailKernels().getKernels();
		for (Kernel kernel : kernels) {
			// need a 64 bit one
			if(kernel.getLabel().contains("Latest 64 bit")) {
				this.kernel = kernel;
				break;
			}
		}

		List<Distribution> distributions = linodeApi.getAvailDistributions().getDistributions();
		for (Distribution distribution : distributions) {
			// I want an ubuntu distribution
			if(distribution.getLabel().contains("Ubuntu")) {
				this.distribution = distribution;
				break;
			}
		}
	}

	@Test
	public void testBaseLinodeCreateAndDelete() throws ApiException {
		// get the first distribution

		LinodeResponse linodeCreateResponse = linodeApi.getLinodeCreate(datacenter.getDatacenterId(), linodePlan.getPlanId());
		assertFalse(linodeCreateResponse.hasErrors());
		Long linodeId = linodeCreateResponse.getLinodeId();

		LinodeResponse linodeDeleteResponse = linodeApi.getLinodeDelete(linodeId);
		assertFalse(linodeDeleteResponse.hasErrors());
		assertEquals(linodeId, linodeDeleteResponse.getLinodeId());
	}

	@Test
	public void testLinodeCreateAndBoot() throws ApiException {
		LinodeResponse linodeCreateResponse = linodeApi.getLinodeCreate(datacenter.getDatacenterId(), 
				linodePlan.getPlanId());
		assertFalse(linodeCreateResponse.hasErrors());
		Long linodeId = linodeCreateResponse.getLinodeId();

		LinodeDiskResponse linodeDiskCreateFromDistributionResponse = linodeApi.getLinodeDiskCreateFromDistribution(linodeId, 
				distribution.getDistributionId(), 
				"LINODE-API-TEST", 
				1024l, 
				"^&*678yuiYUI");
		Long diskId = linodeDiskCreateFromDistributionResponse.getDiskId();
		Long swapDiskId = linodeApi.getLinodeDiskCreate(linodeId, "LINODE-API-TEST-SWAP", "swap", 256l).getDiskId();

		List<Disk> disks = linodeApi.getLinodeDiskList(linodeId).getDisks();
		boolean foundRoot = false;
		boolean foundSwap = false;
		for (Disk disk : disks) {
			if(disk.getDiskId().equals(diskId)) {
				foundRoot = true;
			} else if(disk.getDiskId().equals(swapDiskId)) {
				foundSwap = true;
			}
		}
		assertTrue(foundRoot && foundSwap);

		assertEquals(diskId, linodeApi.getLinodeDiskList(linodeId, diskId).getDisks().get(0).getDiskId());

		LinodeConfigResponse linodeConfigCreateResponse = linodeApi.getLinodeConfigCreate(linodeId, kernel.getKernelId(), "LINODE-API-TEST", Long.toString(diskId) + "," + Long.toString(swapDiskId));
		assertFalse(linodeConfigCreateResponse.hasErrors());

		linodeApi.getLinodeConfigList(linodeId).getConfigs();

		LinodeJobResponse linodeBootResponse = linodeApi.getLinodeBoot(linodeId, linodeConfigCreateResponse.getConfigId());
		assertFalse(linodeBootResponse.hasErrors());

		List<Linode> linodes = linodeApi.getLinodeList().getLinodes();
		boolean found = false;
		for (Linode linode : linodes) {
			if(linode.getLinodeId().equals(linodeId)) {
				found = true;
			}
		}
		assertTrue(found);

		List<Job> jobs = linodeApi.getLinodeJobList(linodeId).getJobs();

		LinodeResponse linodeDeleteResponse = linodeApi.getLinodeDelete(linodeId, true);
		assertFalse(linodeDeleteResponse.hasErrors());
	}

}
