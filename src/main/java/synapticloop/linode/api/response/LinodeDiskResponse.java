package synapticloop.linode.api.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class LinodeDiskResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeDiskResponse.class);

	private Long jobId = null;
	private Long diskId = null;

	public LinodeDiskResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONObject dataObject = jsonObject.getJSONObject("DATA");
			this.jobId = dataObject.getLong("JobID");
			dataObject.remove("JobID");
			this.diskId = dataObject.getLong("DiskID");
			dataObject.remove("DiskID");
			
			ResponseHelper.warnOnMissedKeys(LOGGER, dataObject);
		}

		jsonObject.remove("DATA");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getJobId() {
		return this.jobId;
	}

	public Long getDiskId() {
		return this.diskId;
	}
}
