package synapticloop.linode.api.response.bean;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.exception.ApiException;

public class Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(Job.class);

	private Date enteredDate = null;
	private String action = null;
	private String label = null;
	private Date hostStartDate = null;
	private Long linodeId = null;
	private Date HostFinishDate = null;
	private Long duration = null;
	private String hostMessage = null;
	private Long jobId = null;
	private Boolean hostSuccess = null;

	/**
      {
         "ENTERED_DT":"2009-06-29 18:43:25.0",
         "ACTION":"fs.create",
         "LABEL":"Create Filesystem - test label",
         "HOST_START_DT":"2009-07-27 15:32:40.0",
         "LINODEID":8098,
         "HOST_FINISH_DT":"2009-07-27 15:32:40.0",
         "DURATION":0,
         "HOST_MESSAGE":"",
         "JOBID":1207,
         "HOST_SUCCESS":1
      },
	 * 
	 * @param jsonObject the json object to extract the data from
	 * @throws ApiException if there was an error converting the date
	 */
	public Job(JSONObject jsonObject) throws ApiException {
		this.enteredDate = ResponseHelper.convertDate(jsonObject.getString("ENTERED_DT"));
		jsonObject.remove("ENTERED_DT");
		this.action = jsonObject.getString("ACTION");
		jsonObject.remove("ACTION");
		this.label = jsonObject.getString("LABEL");
		jsonObject.remove("LABEL");
		this.hostStartDate = ResponseHelper.convertDate(jsonObject.getString("HOST_START_DT"));
		jsonObject.remove("HOST_START_DT");
		this.linodeId = jsonObject.getLong("LINODEID");
		jsonObject.remove("LINODEID");
		this.HostFinishDate = ResponseHelper.convertDate(jsonObject.getString("HOST_FINISH_DT"));
		jsonObject.remove("HOST_FINISH_DT");

		// the duration may either be a long if it exists, or a string if empty...
		Object object = jsonObject.get("DURATION");
		if(object instanceof String) {
			this.duration = 0l;
		} else {
			this.duration = jsonObject.getLong("DURATION");
		}
		jsonObject.remove("DURATION");

		this.hostMessage = jsonObject.getString("HOST_MESSAGE");
		jsonObject.remove("HOST_MESSAGE");
		this.jobId = jsonObject.getLong("JOBID");
		jsonObject.remove("JOBID");

		Object hostSuccessObject = jsonObject.get("HOST_SUCCESS");
		if(hostSuccessObject instanceof String) {
			this.hostSuccess = true;
		} else {
			this.hostSuccess = jsonObject.getLong("HOST_SUCCESS") == 1;
		}
		jsonObject.remove("HOST_SUCCESS");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Date getEnteredDate() { return this.enteredDate; }

	public String getAction() { return this.action; }

	public String getLabel() { return this.label; }

	public Date getHostStartDate() { return this.hostStartDate; }

	public Long getLinodeId() { return this.linodeId; }

	public Date getHostFinishDate() { return this.HostFinishDate; }

	public Long getDuration() { return this.duration; }

	public String getHostMessage() { return this.hostMessage; }

	public Long getJobId() { return this.jobId; }

	public Boolean getHostSuccess() { return this.hostSuccess; }

}
