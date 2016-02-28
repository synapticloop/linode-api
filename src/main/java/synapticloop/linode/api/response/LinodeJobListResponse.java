package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Job;

public class LinodeJobListResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeJobListResponse.class);

	private List<Job> jobs = new ArrayList<Job>();

	public LinodeJobListResponse(JSONObject jsonObject) {
		super(jsonObject);
		
		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray("DATA");
			for (Object object : dataArray) {
				jobs.add(new Job((JSONObject)object));
			}
		}

		jsonObject.remove("DATA");
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<Job> getJobs() {
		return this.jobs;
	}

}
