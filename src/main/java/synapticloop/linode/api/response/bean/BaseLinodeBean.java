package synapticloop.linode.api.response.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import synapticloop.linode.exception.ApiException;

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

public class BaseLinodeBean {

	// "2014-10-08 09:39:07.0"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	// "2014-10-08 09:39:07"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT_SHORTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final String JSON_KEY_ABBREVIATION = "ABBR";
	public static final String JSON_KEY_COMMENTS = "Comments";
	public static final String JSON_KEY_CONFIG_ID = "ConfigID";
	public static final String JSON_KEY_CREATE_DT = "CREATE_DT";
	public static final String JSON_KEY_DATACENTREID = "DATACENTERID";
	public static final String JSON_KEY_DISK_LIST = "DiskList";
	public static final String JSON_KEY_DISKID = "DISKID";
	public static final String JSON_KEY_ERRORCODE = "ERRORCODE";
	public static final String JSON_KEY_ERRORMESSAGE = "ERRORMESSAGE";
	public static final String JSON_KEY_HELPER_DEPMOD = "helper_depmod";
	public static final String JSON_KEY_HELPER_DISABLE_UPDATE_DB = "helper_disableUpdateDB";
	public static final String JSON_KEY_HELPER_LIBTLS = "helper_libtls";
	public static final String JSON_KEY_HELPER_XEN = "helper_xen";
	public static final String JSON_KEY_ISREADONLY = "ISREADONLY";
	public static final String JSON_KEY_TYPE = "TYPE";
	public static final String JSON_KEY_KERNEL_ID = "KernelID";
	public static final String JSON_KEY_LABEL = "Label";
	public static final String JSON_KEY_LABEL_UPPER = "LABEL";
	public static final String JSON_KEY_LINODE_ID = "LinodeID";
	public static final String JSON_KEY_LINODEID = "LINODEID";
	public static final String JSON_KEY_LOCATION = "LOCATION";
	public static final String JSON_KEY_RAM_LIMIT = "RAMLimit";
	public static final String JSON_KEY_ROOT_DEVICE_CUSTOM = "RootDeviceCustom";
	public static final String JSON_KEY_ROOT_DEVICE_NUM = "RootDeviceNum";
	public static final String JSON_KEY_ROOT_DEVICE_RO = "RootDeviceRO";
	public static final String JSON_KEY_RUN_LEVEL = "RunLevel";
	public static final String JSON_KEY_SIZE = "SIZE";
	public static final String JSON_KEY_STATUS = "STATUS";
	public static final String JSON_KEY_UPDATE_DT = "UPDATE_DT";

	public static final String MINIMAGESIZE = "MINIMAGESIZE";

	public static final String REQUIRESPVOPSKERNEL = "REQUIRESPVOPSKERNEL";

	public static final String CREATE_DT = "CREATE_DT";

	public static final String DISTRIBUTIONID = "DISTRIBUTIONID";

	public static final String IS64BIT = "IS64BIT";

	/**
	 * Read and return an int from the json object and remove it
	 * 
	 * @param jsonObject the json object to read from 
	 * @param key the key to look up
	 * 
	 * @return the read int value for the key
	 */
	protected int readInt(JSONObject jsonObject, String key) {
		int retVal = jsonObject.getInt(key);
		jsonObject.remove(key);
		return(retVal);
	}

	protected String readString(JSONObject jsonObject, String key) {
		String retVal = jsonObject.getString(key);
		jsonObject.remove(key);
		return(retVal);
	}

	protected String readString(JSONObject jsonObject, String key, String defaultValue) {
		String retVal = jsonObject.optString(key, defaultValue);
		jsonObject.remove(key);
		return(retVal);
	}

	protected Boolean readBoolean(JSONObject jsonObject, String key) {
		Boolean retVal = jsonObject.getBoolean(key);
		jsonObject.remove(key);
		return(retVal);
	}

	protected Long readLong(JSONObject jsonObject, String key) {
		Long retVal = jsonObject.getLong(key);
		jsonObject.remove(key);
		return(retVal);
	}

	protected Long readLong(JSONObject jsonObject, String key, String altKey) {
		Long retVal = jsonObject.optLong(key, -1l);
		if(-1l == retVal) {
			retVal = jsonObject.optLong(altKey, -1l);
			jsonObject.remove(altKey);
		} else {
			jsonObject.remove(key);
		}
		return(retVal);
	}

	protected Date readDate(JSONObject jsonObject, String key) throws ApiException {
		String dateString = jsonObject.getString(key);
		Date retVal = null;
		if(null != dateString && dateString.trim().length() != 0) {
			try {
				if(dateString.length() == 19) {
					retVal = SIMPLE_DATE_FORMAT_SHORTER.parse(dateString);
				} else {
					retVal = SIMPLE_DATE_FORMAT.parse(dateString);
				}
			} catch (ParseException ex) {
				throw new ApiException("Could not parse the date '" + dateString + "', exception message was: " + ex.getMessage());
			}
		}

		jsonObject.remove(key);
		return(retVal);
	}
}
