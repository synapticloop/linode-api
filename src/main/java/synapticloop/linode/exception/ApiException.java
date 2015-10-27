package synapticloop.linode.exception;

public class ApiException extends Throwable {
	private static final long serialVersionUID = -4211535423309700542L;

	public ApiException(String message) {
		super(message);
	}

	public ApiException(Throwable throwable) {
		super(throwable);
	}

	public ApiException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
