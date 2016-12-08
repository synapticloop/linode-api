package synapticloop.linode.api.response.bean;

/*
 * Copyright (c) 2016 Synapticloop.
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.exception.ApiException;

public class Stackscript extends BaseLinodeBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(Stackscript.class);

	private String script = null;
	private String description = null;
	private List<Long> distributionIds = new ArrayList<Long>();
	private String label = null;
	private Integer numDeploymentsTotal = null;
	private Integer numLatestRevision = null;
	private Date createDate = null;
	private Integer numDeploymentsActive = null;
	private Long stackscriptId = null;
	private String revisionNote = null;
	private Date revisionDate = null;
	private boolean isPublic = false;
	private Long userId = null;

	/**
	 *       {
	 *          SCRIPT: "#!/bin/bash
	 * 
	 *          #this is the content of the first StackScript",
	 *          DESCRIPTION: "",
	 *          DISTRIBUTIONIDLIST: "77,78",
	 *          LABEL: "StackScript001",
	 *          DEPLOYMENTSTOTAL: 1,
	 *          LATESTREV: 1,
	 *          CREATE_DT: "2012-05-22 16:35:42.0",
	 *          DEPLOYMENTSACTIVE: 1,
	 *          STACKSCRIPTID: 1,
	 *          REV_NOTE: "Initial import",
	 *          REV_DT: "2012-05-22 16:35:42.0",
	 *          ISPUBLIC: 1,
	 *          USERID: 91886
	 *       },
	 * @param jsonObject the json object to extract the data from
	 * @throws ApiException if there was an error converting the date
	 */
	public Stackscript(JSONObject jsonObject) throws ApiException {
		this.script = readString(jsonObject, JSON_KEY_SCRIPT);
		this.description = readString(jsonObject, JSON_KEY_DESCRIPTION);

		String[] distributionIdList = readString(jsonObject, JSON_KEY_DISTRIBUTIONIDLIST, "").split(",");
		for (String distributionId : distributionIdList) {
			distributionIds.add(Long.valueOf(distributionId));
		}

		this.label = readString(jsonObject, JSON_KEY_LABEL_UPPER);
		this.numDeploymentsTotal = readInt(jsonObject, JSON_KEY_NTSTOTAL);
		this.numLatestRevision = readInt(jsonObject, JSON_KEY_LATESTREV);
		this.createDate = readDate(jsonObject, JSON_KEY_CREATE_DT);
		this.numDeploymentsActive = readInt(jsonObject, JSON_KEY_DEPLOYMENTSACTIVE);
		this.stackscriptId = readLong(jsonObject, JSON_KEY_STACKSCRIPTID);
		this.revisionNote = readString(jsonObject, JSON_KEY_REV_NOTE);
		this.revisionDate = readDate(jsonObject, JSON_KEY_REV_DT);
		this.isPublic = (1 == readInt(jsonObject, JSON_KEY_ISPUBLIC));
		this.userId = readLong(jsonObject, JSON_KEY_USERID);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public String getScript() {
		return this.script;
	}

	public String getDescription() {
		return this.description;
	}

	public List<Long> getDistributionIds() {
		return this.distributionIds;
	}

	public String getLabel() {
		return this.label;
	}

	public Integer getNumDeploymentsTotal() {
		return this.numDeploymentsTotal;
	}

	public Integer getNumLatestRevision() {
		return this.numLatestRevision;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public Integer getNumDeploymentsActive() {
		return this.numDeploymentsActive;
	}

	public Long getStackscriptId() {
		return this.stackscriptId;
	}

	public String getRevisionNote() {
		return this.revisionNote;
	}

	public Date getRevisionDate() {
		return this.revisionDate;
	}

	public boolean getIsPublic() {
		return this.isPublic;
	}

	public Long getUserId() {
		return this.userId;
	}
}
