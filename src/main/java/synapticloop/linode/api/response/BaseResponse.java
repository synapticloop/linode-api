
package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.linode.api.response.bean.ApiError;

public abstract class BaseResponse {
	private static final String JSON_KEY_ACTION = "ACTION";
	private static final String JSON_KEY_ERRORARRAY = "ERRORARRAY";

	protected List<ApiError> errors = new ArrayList<ApiError>();
	protected String action = null;

	public BaseResponse(JSONObject jsonObject) {
		JSONArray errorArray = jsonObject.getJSONArray(JSON_KEY_ERRORARRAY);

		for (Object object : errorArray) {
			errors.add(new ApiError((JSONObject)object));
		}

		jsonObject.remove(JSON_KEY_ERRORARRAY);

		this.action = jsonObject.getString(JSON_KEY_ACTION);
		jsonObject.remove(JSON_KEY_ACTION);
	}

	public String getAction() {
		return(this.action);
	}

	public boolean hasErrors() {
		return(!this.errors.isEmpty());
	}

	public List<ApiError> getErrors() {
		return(this.errors);
	}

}
