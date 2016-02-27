package synapticloop.linode;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.api.response.StackscriptListResponse;
import synapticloop.linode.api.response.StackscriptResponse;
import synapticloop.linode.api.response.bean.Distribution;
import synapticloop.linode.api.response.bean.Stackscript;
import synapticloop.linode.exception.ApiException;

public class LinodeApiStackscriptTest {
	private static final String SIMPLE_SCRIPT = "#!/bin/bash\necho\"hello world\"";

	private LinodeApi linodeApi;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));
	}

	@Test
	public void testStackscriptCreate() throws ApiException {
		// get the first distribution
		Distribution distribution = linodeApi.getAvailDistributions().getDistributions().get(0);

		StackscriptResponse stackscriptCreateResponse = linodeApi.getStackscriptCreate("LINODE-API-TEST", Long.toString(distribution.getDistributionId()), SIMPLE_SCRIPT);
		Long stackscriptId = stackscriptCreateResponse.getStackscriptId();
		assertFalse(stackscriptCreateResponse.hasErrors());

		StackscriptListResponse stackscriptListResponse = linodeApi.getStackscriptList(stackscriptId);
		assertFalse(stackscriptListResponse.hasErrors());
		assertEquals(1, stackscriptListResponse.getStackscripts().size());
		assertEquals(SIMPLE_SCRIPT, stackscriptListResponse.getStackscripts().get(0).getScript());

		StackscriptResponse stackscriptDeleteResponse = linodeApi.getStackscriptDelete(stackscriptId);
		assertFalse(stackscriptDeleteResponse.hasErrors());

		assertEquals(stackscriptId, stackscriptDeleteResponse.getStackscriptId());
	}

	@Test
	public void testStackscriptCreatePrivate() throws ApiException {
		// get the first distribution
		Distribution distribution = linodeApi.getAvailDistributions().getDistributions().get(0);

		StackscriptResponse stackscriptCreateResponse = linodeApi.getStackscriptCreate("LINODE-API-TEST", 
				"A simple script",
				Long.toString(distribution.getDistributionId()), 
				false,
				"revision note",
				SIMPLE_SCRIPT);
		Long stackscriptId = stackscriptCreateResponse.getStackscriptId();
		assertFalse(stackscriptCreateResponse.hasErrors());

		StackscriptListResponse stackscriptListResponse = linodeApi.getStackscriptList(stackscriptId);
		assertFalse(stackscriptListResponse.hasErrors());
		assertEquals(1, stackscriptListResponse.getStackscripts().size());
		Stackscript stackscript = stackscriptListResponse.getStackscripts().get(0);
		assertFalse(stackscript.getIsPublic());
		assertEquals(SIMPLE_SCRIPT, stackscript.getScript());

		StackscriptResponse stackscriptDeleteResponse = linodeApi.getStackscriptDelete(stackscriptId);
		assertFalse(stackscriptDeleteResponse.hasErrors());

		assertEquals(stackscriptId, stackscriptDeleteResponse.getStackscriptId());
	}

}
