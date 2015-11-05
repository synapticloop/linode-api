# linode api 

Based on the idea of [https://github.com/theo/linode-api](https://github.com/theo/linode-api) - at time of writing, last published 11 October 2009.

The api calls are automatically generated from the [https://www.linode.com/api](https://www.linode.com/api) pages so that they are kept in sync.

# Usage

## Requirements

1. A linode Account - sign up/sign in for one here: [www.linode.com](https://www.linode.com/?r=42653249231ed8ab1ffd936e604749386c3f5160)
2. A linode API Access key - once you are logged in, go here to create one [https://manager.linode.com/profile/api](https://manager.linode.com/profile/api)
3. that's it!

## Code

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

# Dependency Management

> Note that the latest version can be found [https://bintray.com/synapticloop/maven/linode-api/](https://bintray.com/synapticloop/maven/linode-api/)

Include the dependency

## maven

this comes from the jcenter bintray, to set up your repository:

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

And now for the dependency

    <dependency>
      <groupId>synapticloop</groupId>
      <artifactId>linode-api</artifactId>
      <version>v1.0.2</version>
      <type>jar</type>
    </dependency>
 
 
## gradle

Repository

    repositories {
        maven {
            url  "http://jcenter.bintray.com" 
        }
    }
 
 or just
 
    repositories {
      jcenter()
    }

and then include the dependency:

    runtime(group: 'synapticloop', name: 'linode-api', version: 'v1.0.2', ext: 'jar')

    compile(group: 'synapticloop', name: 'linode-api', version: 'v1.0.2', ext: 'jar')
 
or 

    runtime 'synapticloop:linode-api:v1.0.2'

    compile 'synapticloop:linode-api:v1.0.2'
    
## Other

You may either download the files from [https://bintray.com/synapticloop/maven/linode-api/](https://bintray.com/synapticloop/maven/linode-api/) or from [https://github.com/synapticloop/linode-api/releases](https://github.com/synapticloop/linode-api/releases)

You will also need the dependencies:

 - `org.json` `json` `20090211`
 - `org.apache.httpcomponents` `httpclient` `4.3.4`

which can be found by searching here: [http://mvnrepository.com/](http://mvnrepository.com/)
