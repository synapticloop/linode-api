package synapticloop.linode.bean;

import java.util.HashMap;
import java.util.Map;

import synapticloop.linode.logger.SimpleLogger;

public class ApiMethodParam {
	private static final Map<String, String> TYPE_LOOKUP = new HashMap<String, String>();
	static {
		TYPE_LOOKUP.put("string", "String");
		TYPE_LOOKUP.put("numeric", "Long");
		TYPE_LOOKUP.put("boolean", "Boolean");
	}
	private String description = null;
	private String parameterName = null;
	private String parameterInformation = null;
	private String type = null;
	private boolean required = false;

	public void addDescription(String description) {
		this.description = description;
	}

	public void addParameterName(String parameterName) {
		if(parameterName.contains("_")) {
			this.parameterName = parameterName;
		} else {
			this.parameterName = parameterName.substring(0,1).toLowerCase() + parameterName.substring(1);
		}
	}

	public void addParameterInformation(String parameterInformation) {
		this.parameterInformation = parameterInformation;

		// now we need to split the parameter description into its components

		String[] splits = parameterInformation.substring(3).split(" ", 2);

		this.type = TYPE_LOOKUP.get(splits[0].trim());
		if(null == this.type) {
			SimpleLogger.log("UNKNOWN TYPE >>> " + splits[0].trim() + " we cannot map this to a java type - update ApiMethodParam.TYPE_LOOKUP");
		}

		this.required = splits[1].trim().contains("(required)");
	}

	public String getDescription() {
		if(null == this.description) {
			return("");
		}

		return(this.description);
	}

	public String getParameterName() { return this.parameterName; }
	public String getParameterDescription() { return this.parameterInformation; }
	public String getType() { return this.type; }
	public boolean getRequired() { return this.required; }
	public String getParameterNameConstant() { return("PARAM_CONSTANT_" + this.parameterName.toUpperCase()); }
}
