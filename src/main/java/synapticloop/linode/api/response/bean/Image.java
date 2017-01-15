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

public class Image extends BaseJsonReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(Image.class);

	private Date createDate = null;
	private String creator = null;
	private String description = null;
	private String fileSystemType = null;
	private Long imageId = null;
	private boolean isPublic = false;
	private String label = null;
	private Date lastUsedDate = null;
	private Long minSize = null;
	private String status = null;
	private String type = null;

	public Image(JSONObject jsonObject) throws ApiException {
		this.createDate =readDate(jsonObject, JSON_KEY_CREATE_DT);
		this.creator = readString(jsonObject, JSON_KEY_CREATOR);
		this.description = readString(jsonObject, JSON_KEY_DESCRIPTION);
		this.fileSystemType = readString(jsonObject, JSON_KEY_FS_TYPE);
		this.imageId = readLong(jsonObject, JSON_KEY_IMAGEID);
		this.isPublic = (1 == readInt(jsonObject, JSON_KEY_ISPUBLIC));
		this.label = readString(jsonObject, JSON_KEY_LABEL_UPPER);
		this.lastUsedDate = readDate(jsonObject, JSON_KEY_LAST_USED_DT);
		this.minSize = readLong(jsonObject, JSON_KEY_MINSIZE);
		this.status = readString(jsonObject, JSON_KEY_STATUS);
		this.type = readString(jsonObject, JSON_KEY_TYPE);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreator() {
		return this.creator;
	}

	public String getDescription() {
		return this.description;
	}

	public String getFileSystemType() {
		return this.fileSystemType;
	}

	public Long getImageId() {
		return this.imageId;
	}

	public boolean getIsPublic() {
		return this.isPublic;
	}

	public String getLabel() {
		return this.label;
	}

	public Date getLastUsedDate() {
		return this.lastUsedDate;
	}

	public Long getMinSize() {
		return this.minSize;
	}

	public String getStatus() {
		return this.status;
	}

	public String getType() {
		return this.type;
	}
}
