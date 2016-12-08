package synapticloop.linode.api.response;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import synapticloop.linode.exception.ApiException;

public abstract class BaseJsonReader {

	// "2014-10-08 09:39:07.0"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	// "2014-10-08 09:39:07"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT_SHORTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final String JSON_KEY_ABBREVIATION = "ABBR";
	public static final String JSON_KEY_ACTION = "ACTION";
	public static final String JSON_KEY_ADDRESS = "ADDRESS";
	public static final String JSON_KEY_ADDRESS4 = "ADDRESS4";
	public static final String JSON_KEY_ADDRESS6 = "ADDRESS6";
	public static final String JSON_KEY_ALERT_BWIN_ENABLED = "ALERT_BWIN_ENABLED";
	public static final String JSON_KEY_ALERT_BWIN_THRESHOLD = "ALERT_BWIN_THRESHOLD";
	public static final String JSON_KEY_ALERT_BWOUT_ENABLED = "ALERT_BWOUT_ENABLED";
	public static final String JSON_KEY_ALERT_BWOUT_THRESHOLD = "ALERT_BWOUT_THRESHOLD";
	public static final String JSON_KEY_ALERT_BWQUOTA_ENABLED = "ALERT_BWQUOTA_ENABLED";
	public static final String JSON_KEY_ALERT_BWQUOTA_THRESHOLD = "ALERT_BWQUOTA_THRESHOLD";
	public static final String JSON_KEY_ALERT_CPU_ENABLED = "ALERT_CPU_ENABLED";
	public static final String JSON_KEY_ALERT_CPU_THRESHOLD = "ALERT_CPU_THRESHOLD";
	public static final String JSON_KEY_ALERT_DISKIO_ENABLED = "ALERT_DISKIO_ENABLED";
	public static final String JSON_KEY_ALERT_DISKIO_THRESHOLD = "ALERT_DISKIO_THRESHOLD";
	public static final String JSON_KEY_ALGORITHM = "ALGORITHM";
	public static final String JSON_KEY_BACKUPSENABLED = "BACKUPSENABLED";
	public static final String JSON_KEY_BACKUPWEEKLYDAY = "BACKUPWEEKLYDAY";
	public static final String JSON_KEY_BACKUPWINDOW = "BACKUPWINDOW";
	public static final String JSON_KEY_CHECK = "CHECK";
	public static final String JSON_KEY_CHECK_ATTEMPTS = "CHECK_ATTEMPTS";
	public static final String JSON_KEY_CHECK_BODY = "CHECK_BODY";
	public static final String JSON_KEY_CHECK_INTERVAL = "CHECK_INTERVAL";
	public static final String JSON_KEY_CHECK_PASSIVE = "CHECK_PASSIVE";
	public static final String JSON_KEY_CHECK_PATH = "CHECK_PATH";
	public static final String JSON_KEY_CHECK_TIMEOUT = "CHECK_TIMEOUT";
	public static final String JSON_KEY_CIPHER_SUITE = "CIPHER_SUITE";
	public static final String JSON_KEY_CLIENTCONNTHROTTLE = "CLIENTCONNTHROTTLE";
	public static final String JSON_KEY_COMMENTS = "Comments";
	public static final String JSON_KEY_CONFIG_ID = "ConfigID";
	public static final String JSON_KEY_CONFIGID = "CONFIGID";
	public static final String JSON_KEY_CONNECTIONS = "CONNECTIONS";
	public static final String JSON_KEY_CORES = "CORES";
	public static final String JSON_KEY_CREATE_DT = "CREATE_DT";
	public static final String JSON_KEY_CREATOR = "CREATOR";
	public static final String JSON_KEY_DATACENTERID = "DATACENTERID";
	public static final String JSON_KEY_DEPLOYMENTSACTIVE = "DEPLOYMENTSACTIVE";
	public static final String JSON_KEY_DESCRIPTION = "DESCRIPTION";
	public static final String JSON_KEY_DISK = "DISK";
	public static final String JSON_KEY_DISK_LIST = "DiskList";
	public static final String JSON_KEY_DISKID = "DISKID";
	public static final String JSON_KEY_DISTRIBUTIONID = "DISTRIBUTIONID";
	public static final String JSON_KEY_DISTRIBUTIONIDLIST = "DISTRIBUTIONIDLIST";
	public static final String JSON_KEY_DISTRIBUTIONVENDOR = "DISTRIBUTIONVENDOR";
	public static final String JSON_KEY_DOMAIN = "DOMAIN";
	public static final String JSON_KEY_DOMAINID = "DOMAINID";
	public static final String JSON_KEY_DURATION = "DURATION";
	public static final String JSON_KEY_ENTERED_DT = "ENTERED_DT";
	public static final String JSON_KEY_ERRORCODE = "ERRORCODE";
	public static final String JSON_KEY_ERRORMESSAGE = "ERRORMESSAGE";
	public static final String JSON_KEY_EXPIRE_SEC = "EXPIRE_SEC";
	public static final String JSON_KEY_FS_TYPE = "FS_TYPE";
	public static final String JSON_KEY_HELPER_DEPMOD = "helper_depmod";
	public static final String JSON_KEY_HELPER_DISABLE_UPDATE_DB = "helper_disableUpdateDB";
	public static final String JSON_KEY_HELPER_LIBTLS = "helper_libtls";
	public static final String JSON_KEY_HELPER_XEN = "helper_xen";
	public static final String JSON_KEY_HOST_FINISH_DT = "HOST_FINISH_DT";
	public static final String JSON_KEY_HOST_MESSAGE = "HOST_MESSAGE";
	public static final String JSON_KEY_HOST_START_DT = "HOST_START_DT";
	public static final String JSON_KEY_HOST_SUCCESS = "HOST_SUCCESS";
	public static final String JSON_KEY_HOSTNAME = "HOSTNAME";
	public static final String JSON_KEY_HOURLY = "HOURLY";
	public static final String JSON_KEY_IMAGEID = "IMAGEID";
	public static final String JSON_KEY_IPADDRESS = "IPADDRESS";
	public static final String JSON_KEY_IPADDRESSID = "IPADDRESSID";
	public static final String JSON_KEY_IS64BIT = "IS64BIT";
	public static final String JSON_KEY_ISKVM = "ISKVM";
	public static final String JSON_KEY_ISPUBLIC = "ISPUBLIC";
	public static final String JSON_KEY_ISPVOPS = "ISPVOPS";
	public static final String JSON_KEY_ISREADONLY = "ISREADONLY";
	public static final String JSON_KEY_ISXEN = "ISXEN";
	public static final String JSON_KEY_JOBID = "JOBID";
	public static final String JSON_KEY_KERNEL_ID = "KernelID";
	public static final String JSON_KEY_KERNELID = "KERNELID";
	public static final String JSON_KEY_LABEL = "Label";
	public static final String JSON_KEY_LABEL_UPPER = "LABEL";
	public static final String JSON_KEY_LAST_USED_DT = "LAST_USED_DT";
	public static final String JSON_KEY_LATESTREV = "LATESTREV";
	public static final String JSON_KEY_LINODE_ID = "LinodeID";
	public static final String JSON_KEY_LINODEID = "LINODEID";
	public static final String JSON_KEY_LOCATION = "LOCATION";
	public static final String JSON_KEY_LPM_DISPLAYGROUP = "LPM_DISPLAYGROUP";
	public static final String JSON_KEY_MASTER_IPS = "MASTER_IPS";
	public static final String JSON_KEY_MINIMAGESIZE = "MINIMAGESIZE";
	public static final String JSON_KEY_MINSIZE = "MINSIZE";
	public static final String JSON_KEY_MODE = "MODE";
	public static final String JSON_KEY_MONTHLY = "MONTHLY";
	public static final String JSON_KEY_NAME = "NAME";
	public static final String JSON_KEY_NODEBALANCERID = "NODEBALANCERID";
	public static final String JSON_KEY_NODEID = "NODEID";
	public static final String JSON_KEY_NTSTOTAL = "DEPLOYMENTSTOTAL";
	public static final String JSON_KEY_PLANID = "PLANID";
	public static final String JSON_KEY_PORT = "PORT";
	public static final String JSON_KEY_PRICE = "PRICE";
	public static final String JSON_KEY_PROTOCOL = "PROTOCOL";
	public static final String JSON_KEY_RAM = "RAM";
	public static final String JSON_KEY_RAM_LIMIT = "RAMLimit";
	public static final String JSON_KEY_RDNS_NAME = "RDNS_NAME";
	public static final String JSON_KEY_REFRESH_SEC = "REFRESH_SEC";
	public static final String JSON_KEY_REQUIRED = "REQUIRED";
	public static final String JSON_KEY_REQUIRESPVOPSKERNEL = "REQUIRESPVOPSKERNEL";
	public static final String JSON_KEY_RESOURCEID = "RESOURCEID";
	public static final String JSON_KEY_RETRY_SEC = "RETRY_SEC";
	public static final String JSON_KEY_REV_DT = "REV_DT";
	public static final String JSON_KEY_REV_NOTE = "REV_NOTE";
	public static final String JSON_KEY_ROOT_DEVICE_CUSTOM = "RootDeviceCustom";
	public static final String JSON_KEY_ROOT_DEVICE_NUM = "RootDeviceNum";
	public static final String JSON_KEY_ROOT_DEVICE_RO = "RootDeviceRO";
	public static final String JSON_KEY_RUN_LEVEL = "RunLevel";
	public static final String JSON_KEY_SCRIPT = "SCRIPT";
	public static final String JSON_KEY_SIZE = "SIZE";
	public static final String JSON_KEY_SOA_EMAIL = "SOA_EMAIL";
	public static final String JSON_KEY_SSL_COMMONNAME = "SSL_COMMONNAME";
	public static final String JSON_KEY_SSL_FINGERPRINT = "SSL_FINGERPRINT";
	public static final String JSON_KEY_STACKSCRIPTID = "STACKSCRIPTID";
	public static final String JSON_KEY_STATUS = "STATUS";
	public static final String JSON_KEY_STICKINESS = "STICKINESS";
	public static final String JSON_KEY_TARGET = "TARGET";
	public static final String JSON_KEY_THROWS = "THROWS";
	public static final String JSON_KEY_TOTALHD = "TOTALHD";
	public static final String JSON_KEY_TOTALRAM = "TOTALRAM";
	public static final String JSON_KEY_TOTALXFER = "TOTALXFER";
	public static final String JSON_KEY_TTL_SEC = "TTL_SEC";
	public static final String JSON_KEY_TYPE = "TYPE";
	public static final String JSON_KEY_UPDATE_DT = "UPDATE_DT";
	public static final String JSON_KEY_USERID = "USERID";
	public static final String JSON_KEY_WATCHDOG = "WATCHDOG";
	public static final String JSON_KEY_WEIGHT = "WEIGHT";
	public static final String JSON_KEY_XFER = "XFER";


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

	/**
	 * Read and return an int from the json object and remove it, if it doesn't
	 * exist - return the default value
	 * 
	 * @param jsonObject the json object to read from
	 * @param defaultValue the default value if the int doesn't exist 
	 * @param key the key to look up
	 * 
	 * @return the read int value for the key
	 */
	protected int readInt(JSONObject jsonObject, String key, int defaultValue) {
		int retVal = jsonObject.optInt(key, defaultValue);
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

	/**
	 * Read a boolean from the json object referenced by the key.  In some 
	 * instances the key will return an empty string which is converted to a 
	 * Boolean value of true, otherwise if it is a long, the value will be 
	 * converted to a boolean: 1 = true, 0 = false.  Finally remove the key from 
	 * the JSON Object
	 * 
	 * @param jsonObject The json object that contains the key
	 * @param key the key to look up
	 * 
	 * @return the boolean value, 1 = true, 0 = false if it is a long, else true
	 * 		if an empty string
	 */
	protected Boolean readBooleanFromPossibleString(JSONObject jsonObject, String key) {
		Boolean retVal = true;
		Object hostSuccessObject = jsonObject.get(key);
		if(hostSuccessObject instanceof Long) {
			retVal = jsonObject.getLong(key) == 1;
		}
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

	/**
	 * Read a long from the json object referenced by the key.  In some instances
	 * the key will return an empty string which is converted to a Long of value 
	 * 0l, otherwise if it is a long, the value will be returned.  Finally remove
	 * the key from the JSON Object
	 * 
	 * @param jsonObject The json object that contains the key
	 * @param key the key to look up
	 * 
	 * @return the long value, 0l if it is a string, else the actual value
	 */
	protected Long readLongFromPossibleString(JSONObject jsonObject, String key) {
		Long retVal = 0l;
		Object object = jsonObject.get(key);
		if(object instanceof Long) {
			retVal = jsonObject.getLong(key);
		}
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

	protected Double readDouble(JSONObject jsonObject, String key) {
		Double retVal = jsonObject.getDouble(key);
		jsonObject.remove(key);
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
