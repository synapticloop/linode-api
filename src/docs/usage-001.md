
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

### High Level Facade

This is designed to get a linode up and running as quickly as possible.  This provides a very small subset of available calls.

> Note that `LinodeApiHighLevel` has been renamed to `LinodeApifacade`

```

LinodeApiFacade linodeApiFacade = new LinodeApiFacade("YOUR_API_KEY_GOES_HERE");

linodeApiFacade.createLinode(...);

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
		String rootPassword) throws ApiException

```

### Finding the IP address(es) of the newly created linode


```
// 'linodeId' is the ID of the newly created linode, the second
// parameter 'null' is the linode ipAddressId (which we don't know
// yet...

List<IPAddress> ipAddresses = linodeApi.getLinodeIpList(linodeId, null).getIpAddresses();

for (IPAddress ipAddress : ipAddresses) {
	System.out.println(ipAddress.getIpAddress() + " is public?: " + ipAddress.getIsPublic());
}

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
