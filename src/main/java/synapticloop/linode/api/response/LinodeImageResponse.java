package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class LinodeImageResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeImageResponse.class);

	private Long jobId = null;
	private Long diskId = null;

	public LinodeImageResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject(JSON_KEY_DATA);
			this.jobId = dataObject.getLong("JobID");
			dataObject.remove("JobID");
			this.diskId = dataObject.getLong("DiskID");
			dataObject.remove("DiskID");
			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getJobId() {
		return this.jobId;
	}

	public Long getDiskId() {
		return this.diskId;
	}
}
