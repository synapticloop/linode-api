package synapticloop.linode.api.response.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class Config {
	private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

	private boolean helperDisableUpdateDb = false;
	private boolean rootDeviceRO = false;
	private String rootDeviceCustom = null;
	private String label = null;
	private List<Long> diskIds = new ArrayList<Long>();
	private Long linodeId = null;
	private String comments = null;
	private Long configId = null;
	private boolean helperXen = false;
	private String runLevel = null;
	private boolean helperDepMod = false;
	private Long kernelID = null;
	private Integer numRootDevice = null;
	private boolean helperLibTls = false;
	private Long ramLimit = null;

	/**
	 *       {
	 *          "helper_disableUpdateDB":1,
	 *          "RootDeviceRO":true,
	 *          "RootDeviceCustom":"",
	 *          "Label":"My configuration profile",
	 *          "DiskList":"55319,55590,,55591,55592,,,,",
	 *          "LinodeID":8098,
	 *          "Comments":"",
	 *          "ConfigID":31058,
	 *          "helper_xen":1,
	 *          "RunLevel":"default",
	 *          "helper_depmod":1,
	 *          "KernelID":85,
	 *          "RootDeviceNum":1,
	 *          "helper_libtls":false,
	 *          "RAMLimit":0
	 *       },
	 * 
	 * @param jsonObject the json object
	 */
	public Config(JSONObject jsonObject) {
		this.helperDepMod = (1 == jsonObject.getInt("helper_disableUpdateDB"));
		jsonObject.remove("helper_disableUpdateDB");
		this.rootDeviceRO = jsonObject.getBoolean("RootDeviceRO");
		jsonObject.remove("RootDeviceRO");
		this.rootDeviceCustom = jsonObject.getString("RootDeviceCustom");
		jsonObject.remove("RootDeviceCustom");
		this.label = jsonObject.getString("Label");
		jsonObject.remove("Label");

		String[] diskIdList = jsonObject.optString("DiskList", "").split(",");
		for (String diskId : diskIdList) {
			diskIds.add(Long.valueOf(diskId));
		}
		jsonObject.remove("DiskList");

		this.linodeId = jsonObject.getLong("LinodeID");
		jsonObject.remove("LinodeID");
		this.comments = jsonObject.getString("Comments");
		jsonObject.remove("Comments");
		this.configId = jsonObject.getLong("ConfigID");
		jsonObject.remove("ConfigID");
		this.helperXen = (1 == jsonObject.getInt("helper_xen"));
		jsonObject.remove("helper_xen");
		this.runLevel = jsonObject.getString("RunLevel");
		jsonObject.remove("RunLevel");
		this.helperDepMod = (1 == jsonObject.getInt("helper_depmod"));
		jsonObject.remove("helper_depmod");
		this.kernelID = jsonObject.getLong("KernelID");
		jsonObject.remove("KernelID");
		this.numRootDevice = jsonObject.getInt("RootDeviceNum");
		jsonObject.remove("RootDeviceNum");
		this.helperLibTls = (1 == jsonObject.getInt("helper_libtls"));
		jsonObject.remove("helper_libtls");
		this.ramLimit = jsonObject.getLong("RAMLimit");
		jsonObject.remove("RAMLimit");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public boolean getHelperDisableUpdateDb() {
		return this.helperDisableUpdateDb;
	}

	public boolean getRootDeviceRO() {
		return this.rootDeviceRO;
	}

	public String getRootDeviceCustom() {
		return this.rootDeviceCustom;
	}

	public String getLabel() {
		return this.label;
	}

	public List<Long> getDiskIds() {
		return this.diskIds;
	}

	public Long getLinodeId() {
		return this.linodeId;
	}

	public String getComments() {
		return this.comments;
	}

	public Long getConfigId() {
		return this.configId;
	}

	public boolean getHelperXen() {
		return this.helperXen;
	}

	public String getRunLevel() {
		return this.runLevel;
	}

	public boolean getHelperDepMod() {
		return this.helperDepMod;
	}

	public Long getKernelID() {
		return this.kernelID;
	}

	public Integer getNumRootDevice() {
		return this.numRootDevice;
	}

	public boolean getHelperLibTls() {
		return this.helperLibTls;
	}

	public Long getRamLimit() {
		return this.ramLimit;
	}
}
