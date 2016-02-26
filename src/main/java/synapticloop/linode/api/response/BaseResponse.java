
package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class BaseResponse {
	private static final String JSON_KEY_ACTION = "ACTION";
	private static final String JSON_KEY_ERRORARRAY = "ERRORARRAY";

	protected List<String> errors = new ArrayList<String>();
	protected String action = null;

	protected BaseResponse(JSONObject jsonObject) {
		JSONArray errorArray = jsonObject.getJSONArray(JSON_KEY_ERRORARRAY);
		Iterator<Object> errorIterator = errorArray.iterator();
		while (errorIterator.hasNext()) {
			errors.add((String) errorIterator.next());
		}
		this.action = jsonObject.getString(JSON_KEY_ACTION);
	}

	public String getAction() {
		return(this.action);
	}

	public boolean hasErrors() {
		return(!this.errors.isEmpty());
	}

	public List<String> getErrors() {
		return(this.errors);
	}
}
