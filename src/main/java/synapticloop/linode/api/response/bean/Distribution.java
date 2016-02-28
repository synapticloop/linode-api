package synapticloop.linode.api.response.bean;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.exception.ApiException;

public class Distribution {
	private static final Logger LOGGER = LoggerFactory.getLogger(Distribution.class);

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
	 * @param jsonObject the json object to extract the data from
	 * @throws ApiException if there was an error converting the date 
	 */
	public Distribution(JSONObject jsonObject) throws ApiException {
		this.is64Bit = (1 == jsonObject.getInt("IS64BIT"));
		jsonObject.remove("IS64BIT");

		this.label = jsonObject.getString("LABEL");
		jsonObject.remove("LABEL");

		this.distributionId = jsonObject.getLong("DISTRIBUTIONID");
		jsonObject.remove("DISTRIBUTIONID");

		this.createDate = ResponseHelper.convertDate(jsonObject.getString("CREATE_DT"));
		jsonObject.remove("CREATE_DT");

		this.requiresVOpsKernel = (1 == jsonObject.getInt("REQUIRESPVOPSKERNEL"));
		jsonObject.remove("REQUIRESPVOPSKERNEL");

		this.minimumImageSize = jsonObject.getLong("MINIMAGESIZE");
		jsonObject.remove("MINIMAGESIZE");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
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
