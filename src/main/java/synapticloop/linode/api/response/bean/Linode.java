package synapticloop.linode.api.response.bean;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.exception.ApiException;

public class Linode {
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
	 * @param jsonObject
	 */
	public Linode(JSONObject jsonObject) throws ApiException {
		this.numTotalTransfer = jsonObject.getLong("TOTALXFER");
		jsonObject.remove("TOTALXFER");
		this.backupsEnabled = (1 == jsonObject.getInt("BACKUPSENABLED"));
		jsonObject.remove("BACKUPSENABLED");
		this.watchdog = (1 == jsonObject.getInt("WATCHDOG"));
		jsonObject.remove("WATCHDOG");
		this.lpmDisplayGroup = jsonObject.getString("LPM_DISPLAYGROUP");
		jsonObject.remove("LPM_DISPLAYGROUP");
		this.alertBWQuotaEnabled = (1 == jsonObject.getInt("ALERT_BWQUOTA_ENABLED"));
		jsonObject.remove("ALERT_BWQUOTA_ENABLED");
		this.status = jsonObject.getInt("STATUS");
		jsonObject.remove("STATUS");
		this.totalRam = jsonObject.getLong("TOTALRAM");
		jsonObject.remove("TOTALRAM");
		this.alertDiskIoThreshold = jsonObject.getLong("ALERT_DISKIO_THRESHOLD");
		jsonObject.remove("ALERT_DISKIO_THRESHOLD");
		this.backupWindow = (1 == jsonObject.getInt("BACKUPWINDOW"));
		jsonObject.remove("BACKUPWINDOW");
		this.alertBWOutEnabled = (1 == jsonObject.getInt("ALERT_BWOUT_ENABLED"));
		jsonObject.remove("ALERT_BWOUT_ENABLED");
		this.alertBWOutThreshold = jsonObject.getInt("ALERT_BWOUT_THRESHOLD");
		jsonObject.remove("ALERT_BWOUT_THRESHOLD");
		this.label = jsonObject.getString("LABEL");
		jsonObject.remove("LABEL");
		this.alertCpuEnabled = (1 == jsonObject.getInt("ALERT_CPU_ENABLED"));
		jsonObject.remove("ALERT_CPU_ENABLED");
		this.alertBWQuotaThreshold = jsonObject.getInt("ALERT_BWQUOTA_THRESHOLD");
		jsonObject.remove("ALERT_BWQUOTA_THRESHOLD");
		this.alertBWWinThreshold = jsonObject.getInt("ALERT_BWIN_THRESHOLD");
		jsonObject.remove("ALERT_BWIN_THRESHOLD");
		this.backupWeeklyDaily = jsonObject.getInt("BACKUPWEEKLYDAY");
		jsonObject.remove("BACKUPWEEKLYDAY");
		this.datacenterId = jsonObject.getLong("DATACENTERID");
		jsonObject.remove("DATACENTERID");
		this.alertCpuThreshold = jsonObject.getInt("ALERT_CPU_THRESHOLD");
		jsonObject.remove("ALERT_CPU_THRESHOLD");
		this.numTotalHardDisk = jsonObject.getInt("TOTALHD");
		jsonObject.remove("TOTALHD");
		this.alertDiskIOEnabled = (1 == jsonObject.getInt("ALERT_DISKIO_ENABLED"));
		jsonObject.remove("ALERT_DISKIO_ENABLED");
		this.alertBWinEnabled = (1 == jsonObject.getInt("ALERT_BWIN_ENABLED"));
		jsonObject.remove("ALERT_BWIN_ENABLED");
		this.linodeId = jsonObject.getLong("LINODEID");
		jsonObject.remove("LINODEID");
		this.createDate = ResponseHelper.convertDate(jsonObject.getString("CREATE_DT"));
		jsonObject.remove("CREATE_DT");
		this.planId = jsonObject.getLong("PLANID");
		jsonObject.remove("PLANID");
		this.distributionVendor = jsonObject.getString("DISTRIBUTIONVENDOR");
		jsonObject.remove("DISTRIBUTIONVENDOR");
		this.isXen = (1 == jsonObject.getInt("ISXEN"));
		jsonObject.remove("ISXEN");
		this.isKvm = (1 == jsonObject.getInt("ISKVM"));
		jsonObject.remove("ISKVM");

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
