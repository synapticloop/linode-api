package synapticloop.linode.bean;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodeMapper {
	private static final Map<String, String> ERROR_CODE_LOOKUP = new HashMap<String, String>();

	static {
		ERROR_CODE_LOOKUP.put("OK", "ErrorCode: 0, ErrorMessage: ok");

		ERROR_CODE_LOOKUP.put("UNKNOWN_1", "ErrorCode: 1, ErrorMessage: Bad request");
		ERROR_CODE_LOOKUP.put("UNKNOWN_2", "ErrorCode: 2, ErrorMessage: No action was requested");
		ERROR_CODE_LOOKUP.put("UNKNOWN_3", "ErrorCode: 3, ErrorMessage: The requested class does not exist");

		ERROR_CODE_LOOKUP.put("AUTHFAIL", "ErrorCode: 4, ErrorMessage: Authentication failed");
		ERROR_CODE_LOOKUP.put("NOACCESS", "ErrorCode: 4, ErrorMessage: Authentication failed");
		ERROR_CODE_LOOKUP.put("PASSWORDEXPIRED", "ErrorCode: 4, ErrorMessage: Authentication failed");
		ERROR_CODE_LOOKUP.put("NEEDTOKEN", "ErrorCode: 4, ErrorMessage: Authentication failed");

		ERROR_CODE_LOOKUP.put("NOTFOUND", "ErrorCode: 5, ErrorMessage: Object not found");

		ERROR_CODE_LOOKUP.put("UNKNOWN_6", "ErrorCode: 6, ErrorMessage: A required property is missing for this action");
		ERROR_CODE_LOOKUP.put("UNKNOWN_7", "ErrorCode: 7, ErrorMessage: Property is invalid");

		ERROR_CODE_LOOKUP.put("VALIDATION", "ErrorCode: 8, ErrorMessage: A data validation error has occurred");

		ERROR_CODE_LOOKUP.put("UNKNOWN_9", "ErrorCode: 9, ErrorMessage: Method Not Implemented");
		ERROR_CODE_LOOKUP.put("UNKNOWN_10", "ErrorCode: 10, ErrorMessage: Too many batched requests");
		ERROR_CODE_LOOKUP.put("UNKNOWN_11", "ErrorCode: 11, ErrorMessage: RequestArray isn't valid JSON or WDDX");
		ERROR_CODE_LOOKUP.put("UNKNOWN_12", "ErrorCode: 12, ErrorMessage: Batch approaching timeout. Stopping here.");
		ERROR_CODE_LOOKUP.put("UNKNOWN_13", "ErrorCode: 13, ErrorMessage: Permission denied");

		ERROR_CODE_LOOKUP.put("ACCOUNTLIMIT", "ErrorCode: 14, ErrorMessage: API rate limit exceeded");
		ERROR_CODE_LOOKUP.put("KEYLIMIT", "ErrorCode: 14, ErrorMessage: API rate limit exceeded");

		ERROR_CODE_LOOKUP.put("CCFAILED", "ErrorCode: 30, ErrorMessage: Charging the credit card failed");

		ERROR_CODE_LOOKUP.put("UNKNOWN_31", "ErrorCode: 31, ErrorMessage: Credit card is expired");

		ERROR_CODE_LOOKUP.put("LINODELIMITER", "ErrorCode: 40, ErrorMessage: Limit of Linodes added per hour reached");
		ERROR_CODE_LOOKUP.put("LINODENOTEMPTY", "ErrorCode: 41, ErrorMessage: Linode must have no disks before delete");
	}

	public static boolean hasErrorCode(String errorCodeName) {
		return(ERROR_CODE_LOOKUP.containsKey(errorCodeName));
	}

	public static String getErrorCodeDescription(String errorCodeName) {
		return(ERROR_CODE_LOOKUP.get(errorCodeName));
	}
}
