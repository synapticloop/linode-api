package synapticloop.linode.api.response;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import synapticloop.linode.exception.ApiException;


/**
 * This is a generated test class for the Nodebalancer api calls, this was 
 * automatically generated from the linode api documentation - which can be 
 * found here:
 * <a href="http://www.linode.com/api/nodebalancer">http://www.linode.com/api/nodebalancer</a>
 * 
 * @author synapticloop
 */

public class NodebalancerGeneratedResponseTest {

	@Test
	public void testNodebalancerConfig_configcreate() throws JSONException, ApiException {

		new NodebalancerConfigResponse(new JSONObject("{" + 
				"   ERRORARRAY: [ ]," + 
				"   DATA: {" + 
				"      ConfigID: 38" + 
				"   }," + 
				"   ACTION: \"nodebalancer.config.create\"" + 
				"}"));

	}


	@Test
	public void testNodebalancerConfig_configdelete() throws JSONException, ApiException {

		new NodebalancerConfigResponse(new JSONObject("{" + 
				"   ERRORARRAY: [ ]," + 
				"   DATA: {" + 
				"      ConfigID: 38" + 
				"   }," + 
				"   ACTION: \"nodebalancer.config.delete\"" + 
				"}"));

	}


//	@Test
	public void testNodebalancerConfigList_configlist() throws JSONException, ApiException {

		new NodebalancerConfigListResponse(new JSONObject("{" + 
				"   ERRORARRAY: [ ]," + 
				"   DATA: [" + 
				"      {" + 
				"         STICKINESS: \"table\"," + 
				"         CHECK_PATH: \"/\"," + 
				"         PORT: 80," + 
				"         CHECK_BODY: \"\"," + 
				"         CHECK: \"connection\"," + 
				"         CHECK_INTERVAL: 5," + 
				"         PROTOCOL: \"http\"," + 
				"         CONFIGID: 38," + 
				"         ALGORITHM: \"roundrobin\"," + 
				"         CHECK_TIMEOUT: 3," + 
				"         NODEBALANCERID: 714," + 
				"         CHECK_ATTEMPTS: 2," + 
				"         CHECK_PASSIVE: 1" + 
				"      }," + 
				"      {" + 
				"         STICKINESS: \"table\"," + 
				"         CHECK_PATH: \"/\"," + 
				"         PORT: 443," + 
				"         CHECK_BODY: \"\"," + 
				"         CHECK: \"connection\"," + 
				"         CHECK_INTERVAL: 5," + 
				"         PROTOCOL: \"https\"," + 
				"         CONFIGID: 39," + 
				"         ALGORITHM: \"roundrobin\"," + 
				"         CHECK_TIMEOUT: 3," + 
				"         NODEBALANCERID: 714," + 
				"         CHECK_ATTEMPTS: 2," + 
				"         CHECK_PASSIVE: 1," + 
				"         SSL_FINGERPRINT: \"00:11:22:33:44:55:66:77:88:99:AA:BB:CC:DD:EE:FF:00:11:22:33\"," + 
				"         SSL_COMMONNAME: \"www.example.com\"" + 
				"      }" + 
				"   ]," + 
				"   ACTION: \"nodebalancer.config.list\"" + 
				"}"));

	}


	@Test
	public void testNodebalancerConfig_configupdate() throws JSONException, ApiException {

		new NodebalancerConfigResponse(new JSONObject("{" + 
				"   ERRORARRAY: [ ]," + 
				"   DATA: {" + 
				"      ConfigID: 38" + 
				"   }," + 
				"   ACTION: \"nodebalancer.config.update\"" + 
				"}"));

	}


//	@Test
	public void testNodebalancer_create() throws JSONException, ApiException {

		new NodebalancerResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"nodebalancer.create\"," + 
				"   \"DATA\":[" + 
				"      {" + 
				"         \"NodeBalancerID\":75" + 
				"      }" + 
				"   ]" + 
				"}"));

	}


//	@Test
	public void testNodebalancer_delete() throws JSONException, ApiException {

		new NodebalancerResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"nodebalancer.delete\"," + 
				"   \"DATA\":[" + 
				"      {" + 
				"         \"NodeBalancerID\":75" + 
				"      }" + 
				"   ]" + 
				"}"));

	}


	@Test
	public void testNodebalancerList_list() throws JSONException, ApiException {

		new NodebalancerListResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"nodebalancer.list\"," + 
				"   \"DATA\":[" + 
				"      {" + 
				"         \"NODEBALANCERID\":53," + 
				"         \"LABEL\":\"awesomebal\"," + 
				"         \"DATACENTERID\":6," + 
				"         \"HOSTNAME\":\"nb-69-164-223-4.newark.nodebalancer.linode.com\"," + 
				"         \"ADDRESS4\":\"69.164.223.4\"," + 
				"         \"ADDRESS6\":\"2600:3c03:1::45a4:df04\"," + 
				"         \"CLIENTCONNTHROTTLE\":4203" + 
				"      }" + 
				"   ]" + 
				"}"));

	}


	@Test
	public void testNodebalancerNode_nodecreate() throws JSONException, ApiException {

		new NodebalancerNodeResponse(new JSONObject("{" + 
				"   ERRORARRAY: [ ]," + 
				"   DATA: {" + 
				"      NodeID: 47" + 
				"   }," + 
				"   ACTION: \"nodebalancer.node.create\"" + 
				"}"));

	}


	@Test
	public void testNodebalancerNode_nodedelete() throws JSONException, ApiException {

		new NodebalancerNodeResponse(new JSONObject("{" + 
				"   ERRORARRAY: [ ]," + 
				"   DATA: {" + 
				"      NodeID: 47" + 
				"   }," + 
				"   ACTION: \"nodebalancer.node.delete\"" + 
				"}"));

	}


	@Test
	public void testNodebalancerNodeList_nodelist() throws JSONException, ApiException {

		new NodebalancerNodeListResponse(new JSONObject("{" + 
				"   ERRORARRAY: [ ]," + 
				"   DATA: [" + 
				"      {" + 
				"         WEIGHT: 100," + 
				"         ADDRESS: \"192.168.42.42:80\"," + 
				"         LABEL: \"Node001\"," + 
				"         NODEID: 47," + 
				"         MODE: \"accept\"," + 
				"         CONFIGID: 39," + 
				"         STATUS: \"Unknown\"," + 
				"         NODEBALANCERID: 714" + 
				"      }" + 
				"   ]," + 
				"   ACTION: \"nodebalancer.node.List\"" + 
				"}"));

	}


	@Test
	public void testNodebalancerNode_nodeupdate() throws JSONException, ApiException {

		new NodebalancerNodeResponse(new JSONObject("{" + 
				"   ERRORARRAY: [ ]," + 
				"   DATA: {" + 
				"      NodeID: 47" + 
				"   }," + 
				"   ACTION: \"nodebalancer.node.update\"" + 
				"}"));

	}


//	@Test
	public void testNodebalancer_update() throws JSONException, ApiException {

		new NodebalancerResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"nodebalancer.update\"," + 
				"   \"DATA\":[" + 
				"      {" + 
				"         \"NodeBalancerID\":75" + 
				"      }" + 
				"   ]" + 
				"}"));

	}

};