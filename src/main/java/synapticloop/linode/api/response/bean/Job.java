package synapticloop.linode.api.response.bean;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(Job.class);

	private Date enteredDate = null;
	private String action = null;
	private String label = null;
	private Date hostStartDate = null;
	private Long linodeId = null;
	private Date HostFinishDate = null;
	private String duration = null;
	private String hostMessage = null;
	private Long jobId = null;
	private String hostSuccess = null;

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
	 * @param jsonObject
	 */
	public Job(JSONObject jsonObject) {
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
		this.duration = jsonObject.getString("DURATION");
		jsonObject.remove("DURATION");
		this.hostMessage = jsonObject.getString("HOST_MESSAGE");
		jsonObject.remove("HOST_MESSAGE");
		this.jobId = jsonObject.getLong("JOBID");
		jsonObject.remove("JOBID");
		this.hostSuccess = jsonObject.getString("HOST_SUCCESS");
		jsonObject.remove("HOST_SUCCESS");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Date getEnteredDate() {
		return this.enteredDate;
	}

	public String getAction() {
		return this.action;
	}

	public String getLabel() {
		return this.label;
	}

	public Date getHostStartDate() {
		return this.hostStartDate;
	}

	public Long getLinodeId() {
		return this.linodeId;
	}

	public Date getHostFinishDate() {
		return this.HostFinishDate;
	}

	public String getDuration() {
		return this.duration;
	}

	public String getHostMessage() {
		return this.hostMessage;
	}

	public Long getJobId() {
		return this.jobId;
	}

	public String getHostSuccess() {
		return this.hostSuccess;
	}

}
