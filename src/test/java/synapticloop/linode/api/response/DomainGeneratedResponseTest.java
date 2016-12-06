package synapticloop.linode.api.response;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import synapticloop.linode.exception.ApiException;


/**
 * This is a generated test class for the Domain api calls, this was 
 * automatically generated from the linode api documentation - which can be 
 * found here:
 * <a href="http://www.linode.com/api/dns">http://www.linode.com/api/dns</a>
 * 
 * @author synapticloop
 */

public class DomainGeneratedResponseTest {

	@Test
	public void testDomain_create() throws JSONException, ApiException {

		new DomainResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"domain.create\"," + 
				"   \"DATA\":{" + 
				"      \"DOMAINID\":5123" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testDomain_delete() throws JSONException, ApiException {

		new DomainResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"domain.delete\"," + 
				"   \"DATA\":{" + 
				"      \"DOMAINID\":5123" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testDomainList_list() throws JSONException, ApiException {

		new DomainListResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"domain.list\"," + 
				"   \"DATA\":[" + 
				"      {" + 
				"         \"DOMAINID\":5093," + 
				"         \"DESCRIPTION\":\"\"," + 
				"         \"TYPE\":\"master\"," + 
				"         \"STATUS\":1," + 
				"         \"SOA_EMAIL\":\"dns@example.com\"," + 
				"         \"DOMAIN\":\"linode.com\"," + 
				"         \"RETRY_SEC\":0," + 
				"         \"MASTER_IPS\":\"\"," + 
				"         \"EXPIRE_SEC\":0," + 
				"         \"REFRESH_SEC\":0," + 
				"         \"TTL_SEC\":0" + 
				"      }," + 
				"      {" + 
				"         \"DOMAINID\":5125," + 
				"         \"DESCRIPTION\":\"\"," + 
				"         \"TYPE\":\"slave\"," + 
				"         \"STATUS\":1," + 
				"         \"SOA_EMAIL\":\"\"," + 
				"         \"DOMAIN\":\"nodefs.com\"," + 
				"         \"RETRY_SEC\":0," + 
				"         \"MASTER_IPS\":\"1.3.5.7;2.4.6.8;\"," + 
				"         \"EXPIRE_SEC\":0," + 
				"         \"REFRESH_SEC\":0," + 
				"         \"TTL_SEC\":0" + 
				"      }" + 
				"   ]" + 
				"}"));

	}


	@Test
	public void testDomainResource_resourcecreate() throws JSONException, ApiException {

		new DomainResourceResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"domain.resource.create\"," + 
				"   \"DATA\":{" + 
				"      \"ResourceId\":28537" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testDomainResource_resourcedelete() throws JSONException, ApiException {

		new DomainResourceResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"domain.resource.delete\"," + 
				"   \"DATA\":{" + 
				"      \"ResourceId\":28537" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testDomainResourceList_resourcelist() throws JSONException, ApiException {

		new DomainResourceListResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"domain.resource.list\"," + 
				"   \"DATA\":[" + 
				"      {" + 
				"         \"PROTOCOL\":\"\"," + 
				"         \"TTL_SEC\":0," + 
				"         \"PRIORITY\":0," + 
				"         \"TYPE\":\"A\"," + 
				"         \"TARGET\":\"75.127.96.245\"," + 
				"         \"WEIGHT\":0," + 
				"         \"RESOURCEID\":28536," + 
				"         \"PORT\":0," + 
				"         \"DOMAINID\":5093," + 
				"         \"NAME\":\"www\"" + 
				"      }," + 
				"      {" + 
				"         \"PROTOCOL\":\"\"," + 
				"         \"TTL_SEC\":0," + 
				"         \"PRIORITY\":0," + 
				"         \"TYPE\":\"A\"," + 
				"         \"TARGET\":\"75.127.96.245\"," + 
				"         \"WEIGHT\":0," + 
				"         \"RESOURCEID\":28537," + 
				"         \"PORT\":0," + 
				"         \"DOMAINID\":5093," + 
				"         \"NAME\":\"mail\"" + 
				"      }" + 
				"   ]" + 
				"}"));

	}


	@Test
	public void testDomainResource_resourceupdate() throws JSONException, ApiException {

		new DomainResourceResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"domain.resource.update\"," + 
				"   \"DATA\":{" + 
				"      \"ResourceId\":28537" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testDomain_update() throws JSONException, ApiException {

		new DomainResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"domain.update\"," + 
				"   \"DATA\":{" + 
				"      \"DOMAINID\":5123" + 
				"   }" + 
				"}"));

	}

};