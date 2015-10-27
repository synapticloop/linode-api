package synapticloop.linode;

import java.util.Map;

/**
 * Linode API request
 * 
 * @author synapticloop
 * 
 */
public class LinodeRequest {
	private String action  = null;
	private Map<String, String> parameters = null;

	public LinodeRequest(String action, Map<String, String> parameters) {
		this.action = action;
		this.parameters = parameters;
	}

	public String getAction() { return this.action; }
	public Map<String, String> getParameters() { return this.parameters; }
}
