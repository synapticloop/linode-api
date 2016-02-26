package synapticloop.linode.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApiMethod {
	private String methodName = null;
	private String name = null;
	private String description = null;
	private List<ApiMethodParam> apiMethodParams = new ArrayList<ApiMethodParam>();
	private List<String> descriptions = new ArrayList<String>();
	private static final Map<String, String> CONSTANT_CACHE = new LinkedHashMap<String, String>();
	private String exampleResponse = null;
	private String[] exampleResponses = null;
	private List<String> apiErrors = new ArrayList<String>();

	/**
	 * This maps to one method call for an api
	 * 
	 * @param methodName the name of the method
	 */
	public ApiMethod(String methodName) {
		this.methodName = methodName;
		int indexOf = methodName.indexOf(".");
		String temp = methodName.substring(indexOf + 1);
		String[] splits = temp.split("\\.");
		boolean isFirst = true;
		if(splits.length == 0) {
			this.name = temp;
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			for (String string : splits) {
				if(isFirst) {
					stringBuilder.append(string);
				} else {
					stringBuilder.append(string.substring(0, 1).toUpperCase());
					stringBuilder.append(string.substring(1));
				}
			}
			this.name = stringBuilder.toString();
		}
	}

	public void addDescription(String description) {
		this.description = description;
		if(description != null && "<p></p>".equals(description)) {
			description = "";
			return;
		}

		// now go through and populate the descriptions array list
		String[] splits = description.split(" ");
		StringBuilder stringBuilder = new StringBuilder();
		int length = 0;
		for (String split : splits) {
			length += split.length();
			if(length > 80) {
				descriptions.add(stringBuilder.toString());
				stringBuilder.setLength(0);
				stringBuilder.trimToSize();
				length = 0;
			}
			stringBuilder.append(split);
			stringBuilder.append(" ");
		}
		// add the final line
		if(stringBuilder.length() != 0) {
			descriptions.add(stringBuilder.toString());
		}
	}

	public void addApiMethodParam(ApiMethodParam apiMethodParam) {
		apiMethodParams.add(apiMethodParam);
	}

	public List<ApiMethodParam> getRequiredApiMethodParams() {
		List<ApiMethodParam> arrayList = new ArrayList<ApiMethodParam>();
		for (ApiMethodParam apiMethodParam : apiMethodParams) {
			if(apiMethodParam.getRequired()) {
				arrayList.add(apiMethodParam);
			}
		}

		return(arrayList);
	}

	public void addConstant(String name) {
		CONSTANT_CACHE.put(getConstantName(name), name);
	}

	private String getConstantName(String name) {
		return("PARAM_CONSTANT_" + name.toUpperCase());
	}

	public static Map<String, String> getConstantCache() { return CONSTANT_CACHE; }
	public String getName() { return this.name; }
	public String getResponseName() {
		
		return this.name.substring(0,1).toUpperCase() + this.name.substring(1) + "Response"; 
	}
	public String getDescription() { return this.description; }
	public List<ApiMethodParam> getApiMethodParams() { return this.apiMethodParams; }
	public List<String> getDescriptions() { return this.descriptions;}
	public String getMethodName() { return this.methodName; }

	public void addExampleResponse(String exampleResponse) {
		this.exampleResponse = exampleResponse;
		this.exampleResponses = exampleResponse.split("\n");
	}

	public String getExampleResponse() { return this.exampleResponse; }
	public String[] getExampleResponses() {
		if(null == this.exampleResponses) {
			return(new String[] {});
		}

		return this.exampleResponses; 
	}

	public void addApiError(String apiError) {
		apiErrors.add(apiError);
	}

	public List<String> getApiErrors() { return this.apiErrors; }
}
