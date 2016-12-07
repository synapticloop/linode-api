package synapticloop.linode.api.helper;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.exception.ApiException;

public class ResponseHelperTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseHelperTest.class);


	@Test
	public void testMissedKeys() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("one", "two");
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	@Test 
	public void testDateParsing() throws ApiException {
		assertNotNull(ResponseHelper.convertDate("2012-10-10 10:10:10"));
		assertNotNull(ResponseHelper.convertDate("2012-10-10 10:10:10.1"));
		try {
			assertNull(ResponseHelper.convertDate("blah-di-blah"));
		} catch(ApiException ex) {
			// do nothing
		}
		assertNull(ResponseHelper.convertDate("     "));
		assertNull(ResponseHelper.convertDate(""));
		assertNull(ResponseHelper.convertDate(null));
	}
}
