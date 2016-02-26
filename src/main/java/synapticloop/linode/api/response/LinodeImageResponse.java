package synapticloop.linode.api.response;

import org.json.JSONObject;

public class LinodeImageResponse extends BaseResponse {
	private Long jobId = null;
	private Long diskId = null;

	public LinodeImageResponse(JSONObject jsonObject) {
		super(jsonObject);
		this.jobId = jsonObject.getJSONObject("DATA").getLong("JobID");
		this.diskId = jsonObject.getJSONObject("DATA").getLong("DiskID");
	}

	public Long getJobId() {
		return this.jobId;
	}

	public Long getDiskId() {
		return this.diskId;
	}
}
