package synapticloop.linode.api.response.bean;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.exception.ApiException;

public class Disk {
	private static final Logger LOGGER = LoggerFactory.getLogger(Disk.class);

	private Date updateDate = null;
	private Long diskId = null;
	private String label = null;
	private String type = null;
	private Long linodeId = null;
	private boolean isReadOnly = false;
	private Integer status = null;
	private Date createDate = null;
	private Long size = null;
	/**
	 *       {
	 *          "UPDATE_DT":"2009-06-30 13:19:00.0",
	 *          "DISKID":55319,
	 *          "LABEL":"test label",
	 *          "TYPE":"ext3",
	 *          "LINODEID":8098,
	 *          "ISREADONLY":0,
	 *          "STATUS":1,
	 *          "CREATE_DT":"2008-04-04 10:08:06.0",
	 *          "SIZE":4096
	 *       },
	 * 
	 * @param jsonObject
	 * @throws ApiException 
	 * @throws JSONException 
	 */
	public Disk(JSONObject jsonObject) throws ApiException {
		this.updateDate = ResponseHelper.convertDate(jsonObject.getString("UPDATE_DT"));
		jsonObject.remove("UPDATE_DT");
		this.diskId = jsonObject.getLong("DISKID");
		jsonObject.remove("DISKID");
		this.label = jsonObject.getString("LABEL");
		jsonObject.remove("LABEL");
		this.type = jsonObject.getString("TYPE");
		jsonObject.remove("TYPE");
		this.linodeId = jsonObject.optLong("LinodeID", -1l);
		if(-1l == this.linodeId) {
			this.linodeId = jsonObject.optLong("LINODEID", -1l);
		}
		jsonObject.remove("LinodeID");
		jsonObject.remove("LINODEID");
		this.isReadOnly = (1 == jsonObject.getInt("ISREADONLY"));
		jsonObject.remove("ISREADONLY");
		this.status = jsonObject.getInt("STATUS");
		jsonObject.remove("STATUS");
		this.createDate = ResponseHelper.convertDate(jsonObject.getString("CREATE_DT"));
		jsonObject.remove("CREATE_DT");
		this.size = jsonObject.getLong("SIZE");
		jsonObject.remove("SIZE");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public Long getDiskId() {
		return this.diskId;
	}

	public String getLabel() {
		return this.label;
	}

	public String getType() {
		return this.type;
	}

	public Long getLinodeId() {
		return this.linodeId;
	}

	public boolean getIsReadOnly() {
		return this.isReadOnly;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public Long getSize() {
		return this.size;
	}

}
