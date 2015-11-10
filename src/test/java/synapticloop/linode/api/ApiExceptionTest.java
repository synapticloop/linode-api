package synapticloop.linode.api;

import static org.junit.Assert.*;

import org.junit.Test;

import synapticloop.linode.exception.ApiException;

public class ApiExceptionTest {
	private static final String THIS_IS_A_MESSAGE = "this is a message";
	private ApiException apiException;

	@Test
	public void testExceptionCreation() {
		apiException = new ApiException(THIS_IS_A_MESSAGE);
		assertEquals(THIS_IS_A_MESSAGE, apiException.getMessage());

		apiException = new ApiException(THIS_IS_A_MESSAGE, new NullPointerException());
		assertEquals(THIS_IS_A_MESSAGE, apiException.getMessage());
		assertTrue(apiException.getCause() instanceof NullPointerException);

		apiException = new ApiException(new NullPointerException());
		assertTrue(apiException.getCause() instanceof NullPointerException);
	}

}
