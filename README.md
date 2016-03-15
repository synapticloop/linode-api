[![Build Status](https://travis-ci.org/synapticloop/linode-api.svg?branch=master)](https://travis-ci.org/synapticloop/linode-api) [![Download](https://api.bintray.com/packages/synapticloop/maven/linode-api/images/download.svg)](https://bintray.com/synapticloop/maven/linode-api/_latestVersion) [![GitHub Release](https://img.shields.io/github/release/synapticloop/linode-api.svg)](https://github.com/synapticloop/linode-api/releases)

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

This is designed to get a lindode up and running as quickly as possible.  This provides a very small subset of available calls.

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

# Building the Package

## *NIX/Mac OS X

From the root of the project, simply run

`./gradlew build`


## Windows

`./gradlew.bat build`


This will compile and assemble the artefacts into the `build/libs/` directory.

Note that this may also run tests (if applicable see the Testing notes)

# Running the Tests

## *NIX/Mac OS X

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`gradlew --info test`

## Windows

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`./gradlew.bat --info test`


The `--info` switch will also output logging for the tests


**WARNING:** These tests make calls against resources (either API calls to a service provider, or consumption of resources from a service provider) which may contribute to your limits, which may lead to a cost.

# Logging

slf4j is the logging framework used for this project.  In order to use a logging framework with this project, sample configurations are below:

## Log4j

A sample `log4j2.xml` is below:

```
<Configuration status="WARN">
	<Appenders>
		<Console name="Console" target="SYSTEM_OUT">
			<PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
		</Console>
	</Appenders>
	<Loggers>
		<Root level="trace">
			<AppenderRef ref="Console"/>
		</Root>
	</Loggers>
</Configuration>
```

# Dependency Management

> Note that the latest version can be found [https://bintray.com/synapticloop/maven/linode-api/view](https://bintray.com/synapticloop/maven/linode-api/view)

## maven setup

this comes from the jcenter bintray, to set up your repository:

```
<?xml version="1.0" encoding="UTF-8" ?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd' xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
  <profiles>
    <profile>
      <repositories>
        <repository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray</name>
          <url>http://jcenter.bintray.com</url>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray-plugins</name>
          <url>http://jcenter.bintray.com</url>
        </pluginRepository>
      </pluginRepositories>
      <id>bintray</id>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>bintray</activeProfile>
  </activeProfiles>
</settings>
```

And now for the dependency

```
<dependency>
	<groupId>synapticloop</groupId>
	<artifactId>linode-api</artifactId>
	<version>v2.0.0</version>
	<type>jar</type>
</dependency>
```


## gradle setup

Repository

```
repositories {
	maven {
		url  "http://jcenter.bintray.com" 
	}
}
```

or just

```
repositories {
	jcenter()
}
```

and then include the dependency:

```
dependencies {
	runtime(group: 'synapticloop', name: 'linode-api', version: 'v2.0.0', ext: 'jar')

	compile(group: 'synapticloop', name: 'linode-api', version: 'v2.0.0', ext: 'jar')
}
```

or, more simply for versions of gradle greater than 2.4

```
dependencies {
	runtime 'synapticloop:linode-api:v2.0.0'

	compile 'synapticloop:linode-api:v2.0.0'
}
```

## Other packages


You may either download the files from [https://bintray.com/synapticloop/maven/linode-api/](https://bintray.com/synapticloop/maven/linode-api/) or from [https://github.com/synapticloop/linode-api/releases](https://github.com/synapticloop/linode-api/releases)

You will also need the dependencies:

### runtime dependencies

  - org.json, json, 20160212: (It may be available on: [bintray](https://bintray.com/org.json/maven/json/20160212/view#files/org.json/json/20160212) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160212|jar) [mvn repository](http://mvnrepository.com/artifact/org.json/json/20160212) )
  - org.apache.httpcomponents, httpclient, 4.3.4: (It may be available on: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.3.4/view#files/org.apache.httpcomponents/httpclient/4.3.4) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.3.4|jar) [mvn repository](http://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.3.4) )
  - org.slf4j, slf4j-api, 1.7.13: (It may be available on: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.13/view#files/org.slf4j/slf4j-api/1.7.13) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.13|jar) [mvn repository](http://mvnrepository.com/artifact/org.slf4j/slf4j-api/1.7.13) )


### compile dependencies

  - org.json, json, 20160212: (It may be available on: [bintray](https://bintray.com/org.json/maven/json/20160212/view#files/org.json/json/20160212) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160212|jar) [mvn repository](http://mvnrepository.com/artifact/org.json/json/20160212) )
  - org.apache.httpcomponents, httpclient, 4.3.4: (It may be available on: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.3.4/view#files/org.apache.httpcomponents/httpclient/4.3.4) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.3.4|jar) [mvn repository](http://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.3.4) )
  - org.slf4j, slf4j-api, 1.7.13: (It may be available on: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.13/view#files/org.slf4j/slf4j-api/1.7.13) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.13|jar) [mvn repository](http://mvnrepository.com/artifact/org.slf4j/slf4j-api/1.7.13) )



**NOTE:** You may need to download any dependencies of the above dependencies in turn

# License

```
The MIT License (MIT)

Copyright (c) 2016 synapticloop

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```


--

> `This README.md file was hand-crafted with care utilising synapticloop` [`templar`](https://github.com/synapticloop/templar/) `->`[`documentr`](https://github.com/synapticloop/documentr/)

--

