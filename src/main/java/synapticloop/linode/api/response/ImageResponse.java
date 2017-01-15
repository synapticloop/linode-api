package synapticloop.linode.api.response;

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
import synapticloop.linode.api.response.bean.Image;
import synapticloop.linode.exception.ApiException;

public class ImageResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageResponse.class);

	private Image image = null;

	public ImageResponse(JSONObject jsonObject) throws ApiException {
		super(jsonObject);
		if(!hasErrors()) {
		this.image = new Image(jsonObject.getJSONArray(JSON_KEY_DATA).getJSONObject(0));
		}
		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
		
	}

	public Date getCreateDate() {
		return this.image.getCreateDate();
	}

	public String getCreator() {
		return this.image.getCreator();
	}

	public String getDescription() {
		return this.image.getDescription();
	}

	public String getFileSystemType() {
		return this.image.getFileSystemType();
	}

	public Long getImageId() {
		return this.image.getImageId();
	}

	public boolean getIsPublic() {
		return this.image.getIsPublic();
	}

	public String getLabel() {
		return this.image.getLabel();
	}

	public Date getLastUsedDate() {
		return this.image.getLastUsedDate();
	}

	public Long getMinSize() {
		return this.image.getMinSize();
	}

	public String getStatus() {
		return this.image.getStatus();
	}

	public String getType() {
		return this.image.getType();
	}

}
