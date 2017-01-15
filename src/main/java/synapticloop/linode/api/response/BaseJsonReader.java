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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.linode.exception.ApiException;

public abstract class BaseJsonReader {

	// "2014-10-08 09:39:07.0"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	// "2014-10-08 09:39:07"
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT_SHORTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	protected static final String JSON_KEY_ABBREVIATION = "ABBR";
	protected static final String JSON_KEY_ACTION = "ACTION";
	protected static final String JSON_KEY_ADDRESS = "ADDRESS";
	protected static final String JSON_KEY_ADDRESS4 = "ADDRESS4";
	protected static final String JSON_KEY_ADDRESS6 = "ADDRESS6";
	protected static final String JSON_KEY_ALERT_BWIN_ENABLED = "ALERT_BWIN_ENABLED";
	protected static final String JSON_KEY_ALERT_BWIN_THRESHOLD = "ALERT_BWIN_THRESHOLD";
	protected static final String JSON_KEY_ALERT_BWOUT_ENABLED = "ALERT_BWOUT_ENABLED";
	protected static final String JSON_KEY_ALERT_BWOUT_THRESHOLD = "ALERT_BWOUT_THRESHOLD";
	protected static final String JSON_KEY_ALERT_BWQUOTA_ENABLED = "ALERT_BWQUOTA_ENABLED";
	protected static final String JSON_KEY_ALERT_BWQUOTA_THRESHOLD = "ALERT_BWQUOTA_THRESHOLD";
	protected static final String JSON_KEY_ALERT_CPU_ENABLED = "ALERT_CPU_ENABLED";
	protected static final String JSON_KEY_ALERT_CPU_THRESHOLD = "ALERT_CPU_THRESHOLD";
	protected static final String JSON_KEY_ALERT_DISKIO_ENABLED = "ALERT_DISKIO_ENABLED";
	protected static final String JSON_KEY_ALERT_DISKIO_THRESHOLD = "ALERT_DISKIO_THRESHOLD";
	protected static final String JSON_KEY_ALGORITHM = "ALGORITHM";
	protected static final String JSON_KEY_BACKUPSENABLED = "BACKUPSENABLED";
	protected static final String JSON_KEY_BACKUPWEEKLYDAY = "BACKUPWEEKLYDAY";
	protected static final String JSON_KEY_BACKUPWINDOW = "BACKUPWINDOW";
	protected static final String JSON_KEY_CHECK = "CHECK";
	protected static final String JSON_KEY_CHECK_ATTEMPTS = "CHECK_ATTEMPTS";
	protected static final String JSON_KEY_CHECK_BODY = "CHECK_BODY";
	protected static final String JSON_KEY_CHECK_INTERVAL = "CHECK_INTERVAL";
	protected static final String JSON_KEY_CHECK_PASSIVE = "CHECK_PASSIVE";
	protected static final String JSON_KEY_CHECK_PATH = "CHECK_PATH";
	protected static final String JSON_KEY_CHECK_TIMEOUT = "CHECK_TIMEOUT";
	protected static final String JSON_KEY_CIPHER_SUITE = "CIPHER_SUITE";
	protected static final String JSON_KEY_CLIENTCONNTHROTTLE = "CLIENTCONNTHROTTLE";
	protected static final String JSON_KEY_COMMENTS = "Comments";
	protected static final String JSON_KEY_CONFIG_ID = "ConfigID";
	protected static final String JSON_KEY_CONFIGID = "CONFIGID";
	protected static final String JSON_KEY_CONNECTIONS = "CONNECTIONS";
	protected static final String JSON_KEY_CORES = "CORES";
	protected static final String JSON_KEY_CREATE_DT = "CREATE_DT";
	protected static final String JSON_KEY_CREATOR = "CREATOR";
	protected static final String JSON_KEY_DATACENTERID = "DATACENTERID";
	protected static final String JSON_KEY_DEPLOYMENTSACTIVE = "DEPLOYMENTSACTIVE";
	protected static final String JSON_KEY_DESCRIPTION = "DESCRIPTION";
	protected static final String JSON_KEY_DISK = "DISK";
	protected static final String JSON_KEY_DISK_LIST = "DiskList";
	protected static final String JSON_KEY_DISKID = "DISKID";
	protected static final String JSON_KEY_DISTRIBUTIONID = "DISTRIBUTIONID";
	protected static final String JSON_KEY_DISTRIBUTIONIDLIST = "DISTRIBUTIONIDLIST";
	protected static final String JSON_KEY_DISTRIBUTIONVENDOR = "DISTRIBUTIONVENDOR";
	protected static final String JSON_KEY_DOMAIN = "DOMAIN";
	protected static final String JSON_KEY_DOMAINID = "DOMAINID";
	protected static final String JSON_KEY_DURATION = "DURATION";
	protected static final String JSON_KEY_ENTERED_DT = "ENTERED_DT";
	protected static final String JSON_KEY_ERRORCODE = "ERRORCODE";
	protected static final String JSON_KEY_ERRORMESSAGE = "ERRORMESSAGE";
	protected static final String JSON_KEY_EXPIRE_SEC = "EXPIRE_SEC";
	protected static final String JSON_KEY_FS_TYPE = "FS_TYPE";
	protected static final String JSON_KEY_HELPER_DEPMOD = "helper_depmod";
	protected static final String JSON_KEY_HELPER_DISABLE_UPDATE_DB = "helper_disableUpdateDB";
	protected static final String JSON_KEY_HELPER_LIBTLS = "helper_libtls";
	protected static final String JSON_KEY_HELPER_XEN = "helper_xen";
	protected static final String JSON_KEY_HOST_FINISH_DT = "HOST_FINISH_DT";
	protected static final String JSON_KEY_HOST_MESSAGE = "HOST_MESSAGE";
	protected static final String JSON_KEY_HOST_START_DT = "HOST_START_DT";
	protected static final String JSON_KEY_HOST_SUCCESS = "HOST_SUCCESS";
	protected static final String JSON_KEY_HOSTNAME = "HOSTNAME";
	protected static final String JSON_KEY_HOURLY = "HOURLY";
	protected static final String JSON_KEY_IMAGEID = "IMAGEID";
	protected static final String JSON_KEY_IPADDRESS = "IPADDRESS";
	protected static final String JSON_KEY_IPADDRESSID = "IPADDRESSID";
	protected static final String JSON_KEY_IS64BIT = "IS64BIT";
	protected static final String JSON_KEY_ISKVM = "ISKVM";
	protected static final String JSON_KEY_ISPUBLIC = "ISPUBLIC";
	protected static final String JSON_KEY_ISPVOPS = "ISPVOPS";
	protected static final String JSON_KEY_ISREADONLY = "ISREADONLY";
	protected static final String JSON_KEY_ISXEN = "ISXEN";
	protected static final String JSON_KEY_JOBID = "JOBID";
	protected static final String JSON_KEY_KERNEL_ID = "KernelID";
	protected static final String JSON_KEY_KERNELID = "KERNELID";
	protected static final String JSON_KEY_LABEL = "Label";
	protected static final String JSON_KEY_LABEL_UPPER = "LABEL";
	protected static final String JSON_KEY_LAST_USED_DT = "LAST_USED_DT";
	protected static final String JSON_KEY_LATESTREV = "LATESTREV";
	protected static final String JSON_KEY_LINODE_ID = "LinodeID";
	protected static final String JSON_KEY_LINODEID = "LINODEID";
	protected static final String JSON_KEY_LOCATION = "LOCATION";
	protected static final String JSON_KEY_LPM_DISPLAYGROUP = "LPM_DISPLAYGROUP";
	protected static final String JSON_KEY_MASTER_IPS = "MASTER_IPS";
	protected static final String JSON_KEY_MINIMAGESIZE = "MINIMAGESIZE";
	protected static final String JSON_KEY_MINSIZE = "MINSIZE";
	protected static final String JSON_KEY_MODE = "MODE";
	protected static final String JSON_KEY_MONTHLY = "MONTHLY";
	protected static final String JSON_KEY_NAME = "NAME";
	protected static final String JSON_KEY_NODEBALANCERID = "NODEBALANCERID";
	protected static final String JSON_KEY_NODEID = "NODEID";
	protected static final String JSON_KEY_NTSTOTAL = "DEPLOYMENTSTOTAL";
	protected static final String JSON_KEY_PLANID = "PLANID";
	protected static final String JSON_KEY_PORT = "PORT";
	protected static final String JSON_KEY_PRICE = "PRICE";
	protected static final String JSON_KEY_PROTOCOL = "PROTOCOL";
	protected static final String JSON_KEY_RAM = "RAM";
	protected static final String JSON_KEY_RAM_LIMIT = "RAMLimit";
	protected static final String JSON_KEY_RDNS_NAME = "RDNS_NAME";
	protected static final String JSON_KEY_REFRESH_SEC = "REFRESH_SEC";
	protected static final String JSON_KEY_REQUIRED = "REQUIRED";
	protected static final String JSON_KEY_REQUIRESPVOPSKERNEL = "REQUIRESPVOPSKERNEL";
	protected static final String JSON_KEY_RESOURCEID = "RESOURCEID";
	protected static final String JSON_KEY_RETRY_SEC = "RETRY_SEC";
	protected static final String JSON_KEY_REV_DT = "REV_DT";
	protected static final String JSON_KEY_REV_NOTE = "REV_NOTE";
	protected static final String JSON_KEY_ROOT_DEVICE_CUSTOM = "RootDeviceCustom";
	protected static final String JSON_KEY_ROOT_DEVICE_NUM = "RootDeviceNum";
	protected static final String JSON_KEY_ROOT_DEVICE_RO = "RootDeviceRO";
	protected static final String JSON_KEY_RUN_LEVEL = "RunLevel";
	protected static final String JSON_KEY_SCRIPT = "SCRIPT";
	protected static final String JSON_KEY_SIZE = "SIZE";
	protected static final String JSON_KEY_SOA_EMAIL = "SOA_EMAIL";
	protected static final String JSON_KEY_SSL_COMMONNAME = "SSL_COMMONNAME";
	protected static final String JSON_KEY_SSL_FINGERPRINT = "SSL_FINGERPRINT";
	protected static final String JSON_KEY_STACKSCRIPTID = "STACKSCRIPTID";
	protected static final String JSON_KEY_STATUS = "STATUS";
	protected static final String JSON_KEY_STICKINESS = "STICKINESS";
	protected static final String JSON_KEY_TARGET = "TARGET";
	protected static final String JSON_KEY_THROWS = "THROWS";
	protected static final String JSON_KEY_TOTALHD = "TOTALHD";
	protected static final String JSON_KEY_TOTALRAM = "TOTALRAM";
	protected static final String JSON_KEY_TOTALXFER = "TOTALXFER";
	protected static final String JSON_KEY_TTL_SEC = "TTL_SEC";
	protected static final String JSON_KEY_TYPE = "TYPE";
	protected static final String JSON_KEY_UPDATE_DT = "UPDATE_DT";
	protected static final String JSON_KEY_USERID = "USERID";
	protected static final String JSON_KEY_WATCHDOG = "WATCHDOG";
	protected static final String JSON_KEY_WEIGHT = "WEIGHT";
	protected static final String JSON_KEY_XFER = "XFER";


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

	
	/**
	 * Return the value of the key from the JSONObject as a string.  If the value 
	 * is not a string, get the object and .toString() it
	 *  
	 * @param jsonObject The jsonObject to lookup from
	 * @param key the key to lookup
	 * 
	 * @return the value of 'key' as a string
	 */
	protected String readString(JSONObject jsonObject, String key) {
		String retVal = null;

		try {
			retVal = jsonObject.getString(key);
		} catch(JSONException ex) {
			retVal = jsonObject.get(key).toString();
		}

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
