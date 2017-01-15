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

public class Disk extends BaseJsonReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(Disk.class);

	private Date updateDate = null;
	private Long diskId = null;
	private String label = null;
	private String type = null;
	private Long linodeId = null;
	private boolean isReadOnly = false;
	private Integer status = null;
	private Date createDate = null;
	private Long size = null;
	/**
	 *       {
	 *          "UPDATE_DT":"2009-06-30 13:19:00.0",
	 *          "DISKID":55319,
	 *          "LABEL":"test label",
	 *          "TYPE":"ext3",
	 *          "LINODEID":8098,
	 *          "ISREADONLY":0,
	 *          "STATUS":1,
	 *          "CREATE_DT":"2008-04-04 10:08:06.0",
	 *          "SIZE":4096
	 *       },
	 * 
	 * @param jsonObject the json object to extract the data from
	 * @throws ApiException if there was an error converting the date
	 */
	public Disk(JSONObject jsonObject) throws ApiException {
		this.updateDate = readDate(jsonObject, JSON_KEY_UPDATE_DT);
		this.diskId = readLong(jsonObject, JSON_KEY_DISKID);
		this.label = readString(jsonObject, JSON_KEY_LABEL_UPPER);
		this.type = readString(jsonObject, JSON_KEY_TYPE);
		this.linodeId = readLong(jsonObject, JSON_KEY_LINODE_ID, JSON_KEY_LINODEID);
		this.isReadOnly = (1 == readInt(jsonObject, JSON_KEY_ISREADONLY));
		this.status = readInt(jsonObject, JSON_KEY_STATUS);
		this.createDate = readDate(jsonObject, JSON_KEY_CREATE_DT);
		this.size = readLong(jsonObject, JSON_KEY_SIZE);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public Long getDiskId() {
		return this.diskId;
	}

	public String getLabel() {
		return this.label;
	}

	public String getType() {
		return this.type;
	}

	public Long getLinodeId() {
		return this.linodeId;
	}

	public boolean getIsReadOnly() {
		return this.isReadOnly;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public Long getSize() {
		return this.size;
	}

}
