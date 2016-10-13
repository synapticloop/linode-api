<a name="documentr_top"></a>[![Build Status](https://travis-ci.org/synapticloop/linode-api.svg?branch=master)](https://travis-ci.org/synapticloop/linode-api) [![Download](https://api.bintray.com/packages/synapticloop/maven/linode-api/images/download.svg)](https://bintray.com/synapticloop/maven/linode-api/_latestVersion) [![GitHub Release](https://img.shields.io/github/release/synapticloop/linode-api.svg)](https://github.com/synapticloop/linode-api/releases) 

 - [linode-api](#documentr_heading_0)
 - [Usage](#documentr_heading_1)
   - [Requirements](#documentr_heading_2)
   - [Creating a Linode](#documentr_heading_3)
 - [Creating A Node Balancer](#documentr_heading_7)
   - [Calling the API (deprecated)](#documentr_heading_8)
 - [Building the Package](#documentr_heading_12)
   - [*NIX/Mac OS X](#documentr_heading_13)
   - [Windows](#documentr_heading_14)
 - [Running the Tests](#documentr_heading_15)
   - [*NIX/Mac OS X](#documentr_heading_16)
   - [Windows](#documentr_heading_17)
 - [Logging - slf4j](#documentr_heading_18)
   - [Log4j](#documentr_heading_19)
 - [Artefact Publishing - Github](#documentr_heading_24)
 - [Artefact Publishing - Bintray](#documentr_heading_25)
   - [maven setup](#documentr_heading_26)
   - [gradle setup](#documentr_heading_27)
   - [Dependencies - Gradle](#documentr_heading_28)
   - [Dependencies - Maven](#documentr_heading_29)
   - [Dependencies - Downloads](#documentr_heading_30)
 - [License](#documentr_heading_38)




> **This project requires JVM version of at least 1.7**






<a name="documentr_heading_0"></a>

# linode-api <sup><sup>[top](#documentr_top)</sup></sup>



> An api for linode



This supercedes version 1 - which is now considered deprecated.

Major differences:

 - All errors returned in the JSON payload now throw an `ApiException` with the JSON error array as the message.
 - All responses are now fully parsed into beans for easy access.
 - High Level API convenience methods for easy creation of linodes
 - Caching of Availability calls
 - Swapped to slf4j over `java.util.logging`
 - Package refactoring


> The api calls are automatically generated from the [https://www.linode.com/api](https://www.linode.com/api) pages so that they are kept in sync.



<a name="documentr_heading_1"></a>

# Usage <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_2"></a>

## Requirements <sup><sup>[top](#documentr_top)</sup></sup>

1. A linode Account - sign up/sign in for one here: [www.linode.com](https://www.linode.com/?r=42653249231ed8ab1ffd936e604749386c3f5160)
2. A linode API Access key - once you are logged in, go here to create one [https://manager.linode.com/profile/api](https://manager.linode.com/profile/api)
3. that's it!



<a name="documentr_heading_3"></a>

## Creating a Linode <sup><sup>[top](#documentr_top)</sup></sup>

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




<a name="documentr_heading_7"></a>

# Creating A Node Balancer <sup><sup>[top](#documentr_top)</sup></sup>



```
// create a node balancer - note that node balancer names __MUST__ be unique
NodebalancerResponse nodebalancerCreateResponse = linodeApi.getNodebalancerCreate(TestHelper.getDatacenterId(), 
		"LINODE-API-TEST-" + System.currentTimeMillis(), 
		0l);

// create a node balance config - this is all defaults - port 80
Long nodebalancerConfigId = linodeApi.getNodebalancerConfigCreate(nodebalancerId).getConfigId();

Long linodeIdOne = linodeApiHighLevel.createLinode(DatacenterSlug.DALLAS_TX_USA, 
		PlanSlug.LINODE_2048,
		DistributionSlug.UBUNTU_16_04_LTS,
		KernelSlug.KERNEL_LATEST_64_BIT_4_7_0_X86_64_LINODE72_,
		"NODE-1", 
		"^&*678yuiYUI");

Long linodeIdTwo = linodeApiHighLevel.createLinode(DatacenterSlug.DALLAS_TX_USA, 
		PlanSlug.LINODE_2048,
		DistributionSlug.UBUNTU_16_04_LTS,
		KernelSlug.KERNEL_LATEST_64_BIT_4_7_0_X86_64_LINODE72_,
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








<a name="documentr_heading_8"></a>

## Calling the API (deprecated) <sup><sup>[top](#documentr_top)</sup></sup>

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



<a name="documentr_heading_12"></a>

# Building the Package <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_13"></a>

## *NIX/Mac OS X <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`./gradlew build`




<a name="documentr_heading_14"></a>

## Windows <sup><sup>[top](#documentr_top)</sup></sup>

`./gradlew.bat build`


This will compile and assemble the artefacts into the `build/libs/` directory.

Note that this may also run tests (if applicable see the Testing notes)



<a name="documentr_heading_15"></a>

# Running the Tests <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_16"></a>

## *NIX/Mac OS X <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`gradlew --info test`



<a name="documentr_heading_17"></a>

## Windows <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`./gradlew.bat --info test`


The `--info` switch will also output logging for the tests


**WARNING:** These tests make calls against resources (either API calls to a service provider, or consumption of resources from a service provider) which may contribute to your limits, which may lead to a cost.



<a name="documentr_heading_18"></a>

# Logging - slf4j <sup><sup>[top](#documentr_top)</sup></sup>

slf4j is the logging framework used for this project.  In order to set up a logging framework with this project, sample configurations are below:



<a name="documentr_heading_19"></a>

## Log4j <sup><sup>[top](#documentr_top)</sup></sup>


You will need to include dependencies for this - note that the versions may need to be updated.

### Maven



```
<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-slf4j-impl</artifactId>
	<version>2.5</version>
	<scope>runtime</scope>
</dependency>

<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-core</artifactId>
	<version>2.5</version>
	<scope>runtime</scope>
</dependency>

```



### Gradle &lt; 2.1



```
dependencies {
	...
	runtime(group: 'org.apache.logging.log4j', name: 'log4j-slf4j-impl', version: '2.5', ext: 'jar')
	runtime(group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.5', ext: 'jar')
	...
}
```


### Gradle &gt;= 2.1



```
dependencies {
	...
	runtime 'org.apache.logging.log4j:log4j-slf4j-impl:2.5'
	runtime 'org.apache.logging.log4j:log4j-core:2.5'
	...
}
```




### Setting up the logging:

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





<a name="documentr_heading_24"></a>

# Artefact Publishing - Github <sup><sup>[top](#documentr_top)</sup></sup>

This project publishes artefacts to [GitHub](https://github.com/)

> Note that the latest version can be found [https://github.com/synapticloop/linode-api/releases](https://github.com/synapticloop/linode-api/releases)

As such, this is not a repository, but a location to download files from.



<a name="documentr_heading_25"></a>

# Artefact Publishing - Bintray <sup><sup>[top](#documentr_top)</sup></sup>

This project publishes artefacts to [bintray](https://bintray.com/)

> Note that the latest version can be found [https://bintray.com/synapticloop/maven/linode-api/view](https://bintray.com/synapticloop/maven/linode-api/view)



<a name="documentr_heading_26"></a>

## maven setup <sup><sup>[top](#documentr_top)</sup></sup>

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





<a name="documentr_heading_27"></a>

## gradle setup <sup><sup>[top](#documentr_top)</sup></sup>

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





<a name="documentr_heading_28"></a>

## Dependencies - Gradle <sup><sup>[top](#documentr_top)</sup></sup>



```
dependencies {
	runtime(group: 'synapticloop', name: 'linode-api', version: '2.2.0', ext: 'jar')

	compile(group: 'synapticloop', name: 'linode-api', version: '2.2.0', ext: 'jar')
}
```



or, more simply for versions of gradle greater than 2.1



```
dependencies {
	runtime 'synapticloop:linode-api:2.2.0'

	compile 'synapticloop:linode-api:2.2.0'
}
```





<a name="documentr_heading_29"></a>

## Dependencies - Maven <sup><sup>[top](#documentr_top)</sup></sup>



```
<dependency>
	<groupId>synapticloop</groupId>
	<artifactId>linode-api</artifactId>
	<version>2.2.0</version>
	<type>jar</type>
</dependency>
```





<a name="documentr_heading_30"></a>

## Dependencies - Downloads <sup><sup>[top](#documentr_top)</sup></sup>


You will also need to download the following dependencies:



### cobertura dependencies

  - net.sourceforge.cobertura:cobertura:2.0.3: (It may be available on one of: [bintray](https://bintray.com/net.sourceforge.cobertura/maven/cobertura/2.0.3/view#files/net.sourceforge.cobertura/cobertura/2.0.3) [mvn central](http://search.maven.org/#artifactdetails|net.sourceforge.cobertura|cobertura|2.0.3|jar))


### compile dependencies

  - org.json:json:20160212: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160212/view#files/org.json/json/20160212) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160212|jar))
  - org.apache.httpcomponents:httpclient:4.5.2: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.2/view#files/org.apache.httpcomponents/httpclient/4.5.2) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.2|jar))
  - org.slf4j:slf4j-api:1.7.21: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.21/view#files/org.slf4j/slf4j-api/1.7.21) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.21|jar))


### generateCompile dependencies

  - synapticloop:templar:1.2.1: (It may be available on one of: [bintray](https://bintray.com/synapticloop/maven/templar/1.2.1/view#files/synapticloop/templar/1.2.1) [mvn central](http://search.maven.org/#artifactdetails|synapticloop|templar|1.2.1|jar))
  - org.jsoup:jsoup:1.9.2: (It may be available on one of: [bintray](https://bintray.com/org.jsoup/maven/jsoup/1.9.2/view#files/org.jsoup/jsoup/1.9.2) [mvn central](http://search.maven.org/#artifactdetails|org.jsoup|jsoup|1.9.2|jar))


### generateRuntime dependencies

  - synapticloop:templar:1.2.1: (It may be available on one of: [bintray](https://bintray.com/synapticloop/maven/templar/1.2.1/view#files/synapticloop/templar/1.2.1) [mvn central](http://search.maven.org/#artifactdetails|synapticloop|templar|1.2.1|jar))
  - org.jsoup:jsoup:1.9.2: (It may be available on one of: [bintray](https://bintray.com/org.jsoup/maven/jsoup/1.9.2/view#files/org.jsoup/jsoup/1.9.2) [mvn central](http://search.maven.org/#artifactdetails|org.jsoup|jsoup|1.9.2|jar))


### runtime dependencies

  - org.json:json:20160212: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160212/view#files/org.json/json/20160212) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160212|jar))
  - org.apache.httpcomponents:httpclient:4.5.2: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.2/view#files/org.apache.httpcomponents/httpclient/4.5.2) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.2|jar))
  - org.slf4j:slf4j-api:1.7.21: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.21/view#files/org.slf4j/slf4j-api/1.7.21) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.21|jar))


### testCompile dependencies

  - junit:junit:4.12: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - org.apache.logging.log4j:log4j-slf4j-impl:2.6.1: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.6.1/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.6.1) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.6.1|jar))
  - org.apache.logging.log4j:log4j-core:2.6.1: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.6.1/view#files/org.apache.logging.log4j/log4j-core/2.6.1) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.6.1|jar))


### testRuntime dependencies

  - junit:junit:4.12: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - org.apache.logging.log4j:log4j-slf4j-impl:2.6.1: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.6.1/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.6.1) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.6.1|jar))
  - org.apache.logging.log4j:log4j-core:2.6.1: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.6.1/view#files/org.apache.logging.log4j/log4j-core/2.6.1) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.6.1|jar))

**NOTE:** You may need to download any dependencies of the above dependencies in turn (i.e. the transitive dependencies)



<a name="documentr_heading_38"></a>

# License <sup><sup>[top](#documentr_top)</sup></sup>



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

> `This README.md file was hand-crafted with care utilising synapticloop`[`templar`](https://github.com/synapticloop/templar/)`->`[`documentr`](https://github.com/synapticloop/documentr/)

--
