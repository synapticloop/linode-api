package synapticloop.linode.api.response;

import org.json.JSONObject;

public class LinodeDiskResponse extends BaseResponse {
	private Long jobId = null;
	private Long imageId = null;

	public LinodeDiskResponse(JSONObject jsonObject) {
		super(jsonObject);
		this.jobId = jsonObject.getJSONObject("DATA").getLong("JobID");
		this.imageId = jsonObject.getJSONObject("DATA").getLong("ImageID");
	}

	public Long getJobId() {
		return this.jobId;
	}

	public Long getImageId() {
		return this.imageId;
	}
}
