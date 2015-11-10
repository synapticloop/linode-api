package synapticloop.linode.exception;

/**
 * An API Exception is a wrapper for any exception that may occur when attempting 
 * to interact with the Linode API
 * 
 * @author synapticloop
 */
public class ApiException extends Exception {
	private static final long serialVersionUID = -4211535423309700542L;

	/**
	 * Create a new API exception with a message
	 * 
	 * @param message the message for the exception
	 */
	public ApiException(String message) {
		super(message);
	}

	/**
	 * Create a new API exception with a root cause exception
	 * 
	 * @param ex the root cause of the exception
	 */
	public ApiException(Exception ex) {
		super(ex);
	}

	/**
	 * Create a new API exception with a message and a root cause exception
	 * 
	 * @param message the message for the exception
	 * @param ex the root cause of the exception
	 */
	public ApiException(String message, Exception ex) {
		super(message, ex);
	}
}
