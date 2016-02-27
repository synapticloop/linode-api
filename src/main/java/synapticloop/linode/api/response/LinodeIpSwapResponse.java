package synapticloop.linode.api.response;

import org.json.JSONObject;

public class LinodeIpSwapResponse extends BaseResponse {

	public LinodeIpSwapResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			// @TODO - do something
		}
	}

}
