package synapticloop.linode.exception;

/**
 * An API Exception is a wrapper for any exception that may occur when attempting 
 * to interact with the Linode API
 * 
 * @author synapticloop
 */
public class ApiException extends Throwable {
	private static final long serialVersionUID = -4211535423309700542L;

	/**
	 * Create a new API exception
	 * 
	 * @param message the message for the exception
	 */
	public ApiException(String message) {
		super(message);
	}

	/**
	 * Create a new API exception
	 * 
	 * @param throwable the throwable cause
	 */
	public ApiException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Create a new API exception
	 * 
	 * @param message the message for the exception
	 * @param throwable the throwable cause
	 */
	public ApiException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
