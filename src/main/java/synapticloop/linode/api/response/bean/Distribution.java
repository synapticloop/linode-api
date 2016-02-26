package synapticloop.linode.api.response.bean;

import java.util.Date;

import org.json.JSONObject;

import synapticloop.linode.api.response.BaseResponse;

public class Distribution {
	private boolean is64Bit = false;
	private String label = null;
	private Long minimumImageSize = null;
	private Long distributionId = null;
	private Date createDate = null;
	private boolean requiresVOpsKernel = false;

	/**
	 *       {
	 *          "IS64BIT":0,
	 *          "LABEL":"Debian 4.0",
	 *          "MINIMAGESIZE":200,
	 *          "DISTRIBUTIONID":28,
	 *          "CREATE_DT":"2007-04-18 00:00:00.0",
	 *          "REQUIRESPVOPSKERNEL":0
	 *       },
	 * 
	 * @param jsonObject
	 */
	public Distribution(JSONObject jsonObject) {
		this.is64Bit = (1 == jsonObject.getInt("IS64BIT"));
		this.label = jsonObject.getString("LABEL");
		this.distributionId = jsonObject.getLong("DISTRIBUTIONID");
		this.createDate = BaseResponse.convertDate(jsonObject.getString("CREATE_DT"));
		this.requiresVOpsKernel = (1 == jsonObject.getInt("REQUIRESPVOPSKERNEL"));
	}

	public boolean getIs64Bit() {
		return this.is64Bit;
	}

	public String getLabel() {
		return this.label;
	}

	public Long getMinimumImageSize() {
		return this.minimumImageSize;
	}

	public Long getDistributionId() {
		return this.distributionId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public boolean getRequiresVOpsKernel() {
		return this.requiresVOpsKernel;
	}
}