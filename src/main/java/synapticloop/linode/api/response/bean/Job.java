package synapticloop.linode.api.response.bean;

/*
 * Copyright (c) 2016-2017 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.BaseJsonReader;
import synapticloop.linode.exception.ApiException;

public class Job extends BaseJsonReader {
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
		this.enteredDate = readDate(jsonObject, JSON_KEY_ENTERED_DT);
		this.action = readString(jsonObject, JSON_KEY_ACTION);
		this.label = readString(jsonObject, JSON_KEY_LABEL_UPPER);
		this.hostStartDate = readDate(jsonObject, JSON_KEY_HOST_START_DT);
		this.linodeId = readLong(jsonObject, JSON_KEY_LINODEID);
		this.HostFinishDate = readDate(jsonObject, JSON_KEY_HOST_FINISH_DT);

		// the duration may either be a long if it exists, or a string if empty...
		this.duration = readLongFromPossibleString(jsonObject, JSON_KEY_DURATION);

		this.hostMessage = readString(jsonObject, JSON_KEY_HOST_MESSAGE);
		this.jobId = readLong(jsonObject, JSON_KEY_JOBID);
		this.hostSuccess = readBooleanFromPossibleString(jsonObject, JSON_KEY_HOST_SUCCESS);

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
