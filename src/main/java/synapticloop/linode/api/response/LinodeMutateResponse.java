package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class LinodeMutateResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeMutateResponse.class);

	private Long linodeId = null;

	public LinodeMutateResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			this.linodeId = dataObject.getLong("LinodeID");
			dataObject.remove("LinodeID");

			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getLinodeId() {
		return this.linodeId;
	}
}
