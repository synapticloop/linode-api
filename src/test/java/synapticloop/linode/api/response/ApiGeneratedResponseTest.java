package synapticloop.linode.api.response;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import synapticloop.linode.exception.ApiException;


/**
 * This is a generated test class for the Api api calls, this was 
 * automatically generated from the linode api documentation - which can be 
 * found here:
 * <a href="http://www.linode.com/api/utility">http://www.linode.com/api/utility</a>
 * 
 * @author synapticloop
 */

public class ApiGeneratedResponseTest {

	@Test
	public void testApiSpec_spec() throws JSONException, ApiException {

		new ApiSpecResponse(new JSONObject("{" + 
				" \"ERRORARRAY\":[]," + 
				" \"ACTION\":\"account.info\"," + 
				" \"DATA\":{" + 
				"  \"METHODS\":{" + 
				"  \"VERSION\": 3.3, " + 
				"   \"account.info\":{" + 
				"    \"DESCRIPTION\":\"Shows information about your account such as" + 
				"     the date your account was opened as well as your network" + 
				"     utilization for the current month in gigabytes.\"," + 
				"     \"PARAMETERS\":{}," + 
				"     \"THROWS\":\"\"" + 
				"    }," + 
				"    \"api.spec\":{" + 
				"    \"DESCRIPTION\" : \"Returns a data structure of the entire" + 
				"     Linode API specification.\"," + 
				"     \"PARAMETERS\":{}," + 
				"     \"THROWS\":\"\"" + 
				"   }" + 
				"  }" + 
				" }" + 
				"}"));

	}

};