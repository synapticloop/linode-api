package synapticloop.linode.api.response.bean;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class Kernel {
	private static final Logger LOGGER = LoggerFactory.getLogger(Kernel.class);

	private String label = null;
	private boolean isXen = false;
	private boolean isKvm = false;
	private boolean isPVOps = false;
	private Long kernelId = null;

	/**
	 *       {
	 *          "LABEL":"2.6.32.16-linode28",
	 *          "ISXEN":1,
	 *          "ISKVM":0,
	 *          "ISPVOPS":1,
	 *          "KERNELID":123
	 *       },
	 * 
	 */

	public Kernel(JSONObject jsonObject) {
		this.label = jsonObject.getString("LABEL");
		jsonObject.remove("LABEL");

		this.isXen = (1 == jsonObject.getInt("ISXEN"));
		jsonObject.remove("ISXEN");

		this.isKvm = (1 == jsonObject.getInt("ISKVM"));
		jsonObject.remove("ISKVM");

		this.isPVOps = (1 == jsonObject.getInt("ISPVOPS"));
		jsonObject.remove("ISPVOPS");

		this.kernelId = jsonObject.getLong("KERNELID");
		jsonObject.remove("KERNELID");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public String getLabel() {
		return this.label;
	}

	public boolean getIsXen() {
		return this.isXen;
	}

	public boolean getIsKvm() {
		return this.isKvm;
	}

	public boolean getIsPVOps() {
		return this.isPVOps;
	}

	public Long getKernelId() {
		return this.kernelId;
	}
}
