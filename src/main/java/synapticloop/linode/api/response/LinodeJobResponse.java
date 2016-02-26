package synapticloop.linode.api.response;

import org.json.JSONObject;

public class LinodeJobResponse extends BaseResponse {
	private Long jobId = null;

	public LinodeJobResponse(JSONObject jsonObject) {
		super(jsonObject);
		this.jobId = jsonObject.getJSONObject("DATA").getLong("JobID");
	}

	public Long getJobId() {
		return this.jobId;
	}
}
