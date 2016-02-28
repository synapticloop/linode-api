package synapticloop.linode;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.api.response.TestEchoResponse;
import synapticloop.linode.exception.ApiException;

public class LinodeApiTestEchoTest {
	private LinodeApi linodeApi;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"));
	}

	@Test
	public void testEcho() throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		// echo parameters are returned in upper case
		parameters.put("HELLO", "world");
		parameters.put("THIS", "that");
		TestEchoResponse testEchoResponse = linodeApi.getTestEcho(parameters);
		assertFalse(testEchoResponse.hasErrors());
		Map<String, String> responses = testEchoResponse.getResponses();
		assertEquals("that", responses.get("THIS"));
		assertEquals("world", responses.get("HELLO"));
	}
}
