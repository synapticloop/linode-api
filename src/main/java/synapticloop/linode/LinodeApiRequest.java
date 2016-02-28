package synapticloop.linode;

import java.util.HashMap;
import java.util.Map;

/**
 * Linode API request, this encapsulates the action and the parameters for the action
 * 
 * @author synapticloop
 * 
 */
public class LinodeApiRequest {
	private String action  = null;
	private Map<String, String> parameters = null;

	/**
	 * Create a new linode request
	 * 
	 * @param action the action to perform
	 * 
	 * @param parameters a map of parameters to pass through
	 */
	public LinodeApiRequest(String action, Map<String, String> parameters) {
		this.action = action;
		if(null != parameters) {
			this.parameters = parameters;
		} else {
			this.parameters = new HashMap<String, String>();
		}
		
	}

	/**
	 * Return the action of this request
	 * 
	 * @return the requested action
	 */
	public String getAction() { return this.action; }

	/**
	 * Return the parameters
	 * 
	 * @return the map of the parameters
	 */
	public Map<String, String> getParameters() { return this.parameters; }
}
