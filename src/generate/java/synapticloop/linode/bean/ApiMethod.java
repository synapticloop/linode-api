package synapticloop.linode.bean;

import java.util.ArrayList;
import java.util.HashMap;
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

	// this is used to ensure we have nice names to lookup
	private static Map<String, String> methodNameLookup = new HashMap<String, String>();
	static {
		methodNameLookup.put("resourcecreate", "ResourceCreate");
		methodNameLookup.put("delete", "Delete");
		methodNameLookup.put("list", "List");
		methodNameLookup.put("update", "Update");
		methodNameLookup.put("datacenters", "Datacenters");
		methodNameLookup.put("distributions", "Distributions");
		methodNameLookup.put("kernels", "Kernels");
		methodNameLookup.put("linodeplans", "LinodePlans");
		methodNameLookup.put("nodebalancers", "NodeBalancers");
		methodNameLookup.put("stackscripts", "Stackscripts");
		methodNameLookup.put("create", "Create");
		methodNameLookup.put("echo", "Echo");
		methodNameLookup.put("resourcedelete", "ResourceDelete");
		methodNameLookup.put("resourcecreate", "ResourceCreate");
		methodNameLookup.put("resourcelist", "ResourceList");
		methodNameLookup.put("resourceupdate", "ResourceUpdate");
		methodNameLookup.put("boot", "Boot");
		methodNameLookup.put("clone", "Clone");
		methodNameLookup.put("configdelete", "ConfigDelete");
		methodNameLookup.put("configcreate", "ConfigCreate");
		methodNameLookup.put("configlist", "ConfigList");
		methodNameLookup.put("configupdate", "ConfigUpdate");
		methodNameLookup.put("diskcreate", "DiskCreate");
		methodNameLookup.put("diskcreatefromdistribution", "DiskCreateFromDistribution");
		methodNameLookup.put("diskcreatefromimage", "DiskCreateFromImage");
		methodNameLookup.put("diskcreatefromstackscript", "DiskCreateFromStackscript");
		methodNameLookup.put("diskdelete", "DiskDelete");
		methodNameLookup.put("diskduplicate", "DiskDuplicate");
		methodNameLookup.put("diskimagize", "DiskImagize");
		methodNameLookup.put("disklist", "DiskList");
		methodNameLookup.put("diskresize", "DiskResize");
		methodNameLookup.put("diskupdate", "DiskUpdate");
		methodNameLookup.put("ipaddprivate", "IpAddressPrivate");
		methodNameLookup.put("ipaddpublic", "IpAddressPublic");
		methodNameLookup.put("iplist", "IpList");
		methodNameLookup.put("ipsetrdns", "IpSetRdns");
		methodNameLookup.put("ipswap", "IpSwap");
		methodNameLookup.put("joblist", "JobList");
		methodNameLookup.put("reboot", "Reboot");
		methodNameLookup.put("resize", "Resize");
		methodNameLookup.put("shutdown", "Shutdown");
		methodNameLookup.put("configcreate", "ConfigCreate");
		methodNameLookup.put("configdelete", "ConfigDelete");
		methodNameLookup.put("configlist", "ConfigList");
		methodNameLookup.put("configupdate", "ConfigUpdate");
		methodNameLookup.put("nodecreate", "NodeCreate");
		methodNameLookup.put("nodedelete", "NodeDelete");
		methodNameLookup.put("nodelist", "NodeList");
		methodNameLookup.put("nodeupdate", "NodeUpdate");
		methodNameLookup.put("spec", "Spec");
		methodNameLookup.put("getapikey", "GetApiKey");
		methodNameLookup.put("estimateinvoice", "EstimateInvoice");
		methodNameLookup.put("info", "Info");
	}

	private static Map<String, String> responseNameLookup = new HashMap<String, String>();
	static {
		responseNameLookup.put("delete", "");
		responseNameLookup.put("create", "");
		responseNameLookup.put("list", "List");
		responseNameLookup.put("update", "");

		responseNameLookup.put("resourcecreate", "Resource");
		responseNameLookup.put("resourcedelete", "Resource");
		responseNameLookup.put("resourceupdate", "Resource");
		
		responseNameLookup.put("boot", "Job");
		responseNameLookup.put("reboot", "Job");
		responseNameLookup.put("shutdown", "Job");
		responseNameLookup.put("clone", "");

		responseNameLookup.put("configcreate", "Config");
		responseNameLookup.put("configdelete", "Config");
		responseNameLookup.put("configupdate", "Config");

		responseNameLookup.put("diskcreate", "Disk");
		responseNameLookup.put("diskcreatefromdistribution", "Disk");
		responseNameLookup.put("diskcreatefromimage", "Disk");
		responseNameLookup.put("diskcreatefromstackscript", "Disk");
		responseNameLookup.put("diskduplicate", "Disk");
		responseNameLookup.put("diskresize", "Disk");
		responseNameLookup.put("diskdelete", "Disk");
		responseNameLookup.put("diskupdate", "Disk");

		responseNameLookup.put("diskimagize", "Image");

		responseNameLookup.put("ipaddprivate", "Ip");
		responseNameLookup.put("ipaddpublic", "Ip");

		responseNameLookup.put("nodecreate", "Node");
		responseNameLookup.put("nodedelete", "Node");
		responseNameLookup.put("nodeupdate", "Node");


	}

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
		if(responseNameLookup.containsKey(name)) {
			return(responseNameLookup.get(name));
		} else {
			return(getUpperName());
		}
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

	public String getUpperName() {
		if(methodNameLookup.containsKey(name)) {
			return(methodNameLookup.get(name));
		} else {
			throw new RuntimeException("Cannot lookup name of '" + name + "'.");
		}
	}

	public void addApiError(String apiError) {
		apiErrors.add(apiError);
	}

	public List<String> getApiErrors() { return this.apiErrors; }
}
