package synapticloop.linode.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Api {
	public List<ApiMethod> apiMethods = new ArrayList<ApiMethod>();
	private String location = null;
	private String name = null;
	private final Map<String, String> CONSTANT_CACHE = new LinkedHashMap<String, String>();


	public Api(String location, String name) {
		this.location = location;
		this.name = name;
	}

	public void addApiMethod(ApiMethod apiMethod) {
		apiMethods.add(apiMethod);
	}

	public String getClassName() { return(name.substring(0,1).toUpperCase() + name.substring(1)); }

	public void addConstant(String name) {
		CONSTANT_CACHE.put(getConstantName(name), name);
	}

	private String getConstantName(String name) {
		return("PARAM_CONSTANT_" + name.toUpperCase());
	}

	public Map<String, String> getConstantCache() { return CONSTANT_CACHE; }
	public List<ApiMethod> getApiMethods() { return this.apiMethods; }
	public String getName() { return this.name; }
	public String getLocation() { return this.location; }
}
