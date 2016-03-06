

# linode api version 2

This supercedes version 1 - which is now considered deprecated.

Major differences:

 - All errors returned in the JSON payload now throw an `ApiException` with the JSON error array as the message.
 - All responses are now fully parsed into beans for easy access.
 - High Level API convenience methods for easy creation of linodes
 - Caching of Availability calls
 - Swapped to slf4j over `java.util.logging`
 - Package refactoring


> The api calls are automatically generated from the [https://www.linode.com/api](https://www.linode.com/api) pages so that they are kept in sync.

# Usage

## Requirements

1. A linode Account - sign up/sign in for one here: [www.linode.com](https://www.linode.com/?r=42653249231ed8ab1ffd936e604749386c3f5160)
2. A linode API Access key - once you are logged in, go here to create one [https://manager.linode.com/profile/api](https://manager.linode.com/profile/api)
3. that's it!

## Creating a Linode

### High Level Interface

This is desiegned to get a lindode up and running as quickly as possible.  This provides a very small subset of available calls.

```
/**
 * Create and boot a linode in a datacenter, for a specific plan, using a
 * specific distribution.
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
public Long createLinode(DatacenterSlug datacenter, 
		PlanSlug plan, 
		DistributionSlug distribution, 
		KernelSlug kernel, 
		String label,
		String rootPassword) throws ApiException {

```

### Low Level Interface

The general flow is

 1. Choose a datacenter
 1. Choose a plan
 1. Choose a kernel
 1. Choose a distribution
 1. Create a linode
 1. Create the disks (2 are required)
 1. Create a configuration for the linode
 1. Boot the linode
 

```
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
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

	}

}
```
# Creating A Node Balancer

```
// create a node balancer - note that node balancer names __MUST__ be unique
NodebalancerResponse nodebalancerCreateResponse = linodeApi.getNodebalancerCreate(TestHelper.getDatacenterId(), 
		"LINODE-API-TEST-" + System.currentTimeMillis(), 
		0l);

// create a node balance config - this is all defaults - port 80
Long nodebalancerConfigId = linodeApi.getNodebalancerConfigCreate(nodebalancerId).getConfigId();

Long linodeIdOne = linodeApiHighLevel.createLinode(DatacenterSlug.DALLAS_TX_USA, 
		PlanSlug.LINODE_1024,
		DistributionSlug.UBUNTU_14_04_LTS,
		KernelSlug.KERNEL_LATEST_64_BIT_4_4_0_X86_64_LINODE63_,
		"NODE-1", 
		"^&*678yuiYUI");

Long linodeIdTwo = linodeApiHighLevel.createLinode(DatacenterSlug.DALLAS_TX_USA, 
		PlanSlug.LINODE_1024,
		DistributionSlug.UBUNTU_14_04_LTS,
		KernelSlug.KERNEL_LATEST_64_BIT_4_4_0_X86_64_LINODE63_,
		"NODE-2", 
		"^&*678yuiYUI");

// you may only node balance between private IP addresses - so
// attach a private ip address to each of the linodes
String ipAddressOne = linodeApi.getLinodeIpAddressPrivate(linodeIdOne).getIpAddress();
String ipAddressTwo = linodeApi.getLinodeIpAddressPrivate(linodeIdTwo).getIpAddress();

// create the two nodes - note the port is appended to the private ip address
Long nodeIdOne = linodeApi.getNodebalancerNodeCreate(nodebalancerConfigId, "Node-1-config", ipAddressOne + ":80").getNodeId();
Long nodeIdTwo = linodeApi.getNodebalancerNodeCreate(nodebalancerConfigId, "Node-2-config", ipAddressTwo + ":80").getNodeId();
```




## Calling the API (deprecated)

These are deprecated and the `LinodeApi` or `LinodeApiHighLevel` objects should be used instead

### Single Requests

    // create a  linodeApi object
    LinodeApi linodeApi = new LinodeApi("<YOUR_SUPER_SECRET_API_KEY>");
    
    // create a single request to get the available datacentres
    LinodeResponse linodeResponse = linodeApi.execute(Avail.datacenters());
    
    // the response should look something like this:
    
    {
       "ERRORARRAY":[],
       "ACTION":"avail.datacenters",
       "DATA":[
          {
             "DATACENTERID":2,
             "LOCATION":"Dallas, TX, USA",
             "ABBR":"dallas"
          },
          {
             "DATACENTERID":3,
             "LOCATION":"Fremont, CA, USA",
             "ABBR":"fremont"
          },
          {
             "DATACENTERID":4,
             "LOCATION":"Atlanta, GA, USA",
             "ABBR":"atlanta"
          },
          {
             "DATACENTERID":6,
             "LOCATION":"Newark, NJ, USA",
             "ABBR":"newark"
          },
          {
             "DATACENTERID":7,
             "LOCATION":"London, England, UK",
             "ABBR":"london"
          },
          {
             "DATACENTERID":8,
             "LOCATION":"Tokyo, JP",
             "ABBR":"tokyo"
          },
          {
              "DATACENTERID":9,
              "LOCATION":"Singapore, SG",
              "ABBR":"singapore"
          },
          {
              "DATACENTERID":10,
              "LOCATION":"Frankfurt, DE",
              "ABBR":"frankfurt"
          }
       ]
    }
    
### Multiple Requests

    // create a  linodeApi object
    LinodeApi linodeApi = new LinodeApi("<YOUR_SUPER_SECRET_API_KEY>");

    // create an list of requests to perform
    List<LinodeRequest> linodeRequests = new ArrayList<LinodeRequest>();
    
    // add in the requests
    linodeRequests.add(Avail.datacenters());
    linodeRequests.add(Avail.distributions());
    
    // perform the requests and get back the responses
    List<LinodeResponse> linodeResponses = linodeApi.execute(linodeRequests);

### Available API calls

have a look at [https://github.com/synapticloop/linode-api/tree/master/src/main/java/synapticloop/linode/api](https://github.com/synapticloop/linode-api/tree/master/src/main/java/synapticloop/linode/api) for the list of available API classes.

