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

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.exception.ApiException;

public class Linode extends BaseLinodeBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(Linode.class);

	private Long numTotalTransfer = null;
	private boolean backupsEnabled = false;
	private boolean watchdog = false;
	private String lpmDisplayGroup = null;
	private boolean alertBWQuotaEnabled = false;
	private Integer status = null;
	private Long totalRam = null;
	private Long alertDiskIoThreshold = null;
	private boolean backupWindow = false;
	private boolean alertBWOutEnabled = false;
	private Integer alertBWOutThreshold = null;
	private String label = null;
	private boolean alertCpuEnabled = false;
	private Integer alertBWQuotaThreshold = null;
	private Integer alertBWWinThreshold = null;
	private Integer backupWeeklyDaily = null;
	private Long datacenterId = null;
	private Integer alertCpuThreshold = null;
	private Integer numTotalHardDisk = null;
	private boolean alertDiskIOEnabled = false;
	private boolean alertBWinEnabled = false;
	private Long linodeId = null;
	private Date createDate = null;
	private Long planId = null;
	private String distributionVendor = null;
	private boolean isXen = false;
	private boolean isKvm = false;
	/**
      {
         "TOTALXFER":2000,
         "BACKUPSENABLED":1,
         "WATCHDOG":1,
         "LPM_DISPLAYGROUP":"",
         "ALERT_BWQUOTA_ENABLED":1,
         "STATUS":2,
         "TOTALRAM":1024,
         "ALERT_DISKIO_THRESHOLD":1000,
         "BACKUPWINDOW":1,
         "ALERT_BWOUT_ENABLED":1,
         "ALERT_BWOUT_THRESHOLD":5,
         "LABEL":"api-node3",
         "ALERT_CPU_ENABLED":1,
         "ALERT_BWQUOTA_THRESHOLD":80,
         "ALERT_BWIN_THRESHOLD":5,
         "BACKUPWEEKLYDAY":0,
         "DATACENTERID":5,
         "ALERT_CPU_THRESHOLD":90,
         "TOTALHD":40960,
         "ALERT_DISKIO_ENABLED":1,
         "ALERT_BWIN_ENABLED":1,
         "LINODEID":8098,
         "CREATE_DT":"2015-09-22 11:33:06.0",
         "PLANID":1,
         "DISTRIBUTIONVENDOR": "Debian",
         "ISXEN":0,
         "ISKVM":1
      }	 * 
	 * @param jsonObject the json object to extract the data from
	 * @throws ApiException if there was an error converting the date
	 */
	public Linode(JSONObject jsonObject) throws ApiException {
		this.numTotalTransfer = readLong(jsonObject, JSON_KEY_TOTALXFER);
		this.backupsEnabled = (1 == readInt(jsonObject, JSON_KEY_BACKUPSENABLED));
		this.watchdog = (1 == readInt(jsonObject, JSON_KEY_WATCHDOG));
		this.lpmDisplayGroup = readString(jsonObject, JSON_KEY_LPM_DISPLAYGROUP);
		this.alertBWQuotaEnabled = (1 == readInt(jsonObject, JSON_KEY_ALERT_BWQUOTA_ENABLED));
		this.status = readInt(jsonObject, JSON_KEY_STATUS);
		this.totalRam = readLong(jsonObject, JSON_KEY_TOTALRAM);
		this.alertDiskIoThreshold = readLong(jsonObject, JSON_KEY_ALERT_DISKIO_THRESHOLD);
		this.backupWindow = (1 == readInt(jsonObject, JSON_KEY_BACKUPWINDOW));
		this.alertBWOutEnabled = (1 == readInt(jsonObject, JSON_KEY_ALERT_BWOUT_ENABLED));
		this.alertBWOutThreshold = readInt(jsonObject, JSON_KEY_ALERT_BWOUT_THRESHOLD);
		this.label = readString(jsonObject, JSON_KEY_LABEL_UPPER);
		this.alertCpuEnabled = (1 == readInt(jsonObject, JSON_KEY_ALERT_CPU_ENABLED));
		this.alertBWQuotaThreshold = readInt(jsonObject, JSON_KEY_ALERT_BWQUOTA_THRESHOLD);
		this.alertBWWinThreshold = readInt(jsonObject, JSON_KEY_ALERT_BWIN_THRESHOLD);
		this.backupWeeklyDaily = readInt(jsonObject, JSON_KEY_BACKUPWEEKLYDAY);
		this.datacenterId = readLong(jsonObject, JSON_KEY_DATACENTERID);
		this.alertCpuThreshold = readInt(jsonObject, JSON_KEY_ALERT_CPU_THRESHOLD);
		this.numTotalHardDisk = readInt(jsonObject, JSON_KEY_TOTALHD);
		this.alertDiskIOEnabled = (1 == readInt(jsonObject, JSON_KEY_ALERT_DISKIO_ENABLED));
		this.alertBWinEnabled = (1 == readInt(jsonObject, JSON_KEY_ALERT_BWIN_ENABLED));
		this.linodeId = readLong(jsonObject, JSON_KEY_LINODEID);
		this.createDate = readDate(jsonObject, JSON_KEY_CREATE_DT);
		this.planId = readLong(jsonObject, JSON_KEY_PLANID);
		this.distributionVendor = readString(jsonObject, JSON_KEY_DISTRIBUTIONVENDOR);
		this.isXen = (1 == readInt(jsonObject, JSON_KEY_ISXEN));
		this.isKvm = (1 == readInt(jsonObject, JSON_KEY_ISKVM));

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Long getNumTotalTransfer() {
		return this.numTotalTransfer;
	}

	public boolean getBackupsEnabled() {
		return this.backupsEnabled;
	}

	public boolean getWatchdog() {
		return this.watchdog;
	}

	public String getLpmDisplayGroup() {
		return this.lpmDisplayGroup;
	}

	public boolean getAlertBWQuotaEnabled() {
		return this.alertBWQuotaEnabled;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Long getTotalRam() {
		return this.totalRam;
	}

	public Long getAlertDiskIoThreshold() {
		return this.alertDiskIoThreshold;
	}

	public boolean getBackupWindow() {
		return this.backupWindow;
	}

	public boolean getAlertBWOutEnabled() {
		return this.alertBWOutEnabled;
	}

	public Integer getAlertBWOutThreshold() {
		return this.alertBWOutThreshold;
	}

	public String getLabel() {
		return this.label;
	}

	public boolean getAlertCpuEnabled() {
		return this.alertCpuEnabled;
	}

	public Integer getAlertBWQuotaThreshold() {
		return this.alertBWQuotaThreshold;
	}

	public Integer getAlertBWWinThreshold() {
		return this.alertBWWinThreshold;
	}

	public Integer getBackupWeeklyDaily() {
		return this.backupWeeklyDaily;
	}

	public Long getDatacenterId() {
		return this.datacenterId;
	}

	public Integer getAlertCpuThreshold() {
		return this.alertCpuThreshold;
	}

	public Integer getNumTotalHardDisk() {
		return this.numTotalHardDisk;
	}

	public boolean getAlertDiskIOEnabled() {
		return this.alertDiskIOEnabled;
	}

	public boolean getAlertBWinEnabled() {
		return this.alertBWinEnabled;
	}

	public Long getLinodeId() {
		return this.linodeId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public Long getPlanId() {
		return this.planId;
	}

	public String getDistributionVendor() {
		return this.distributionVendor;
	}

	public boolean getIsXen() {
		return this.isXen;
	}

	public boolean getIsKvm() {
		return this.isKvm;
	}

}
