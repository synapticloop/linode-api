package synapticloop.linode.api.response.bean;

import java.util.Date;

import org.json.JSONObject;

import synapticloop.linode.api.helper.ResponseHelper;

public class Disk {
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
	 */
	public Disk(JSONObject jsonObject) {
		this.updateDate = ResponseHelper.convertDate(jsonObject.getString("UPDATE_DT"));
		this.diskId = jsonObject.getLong("DISKID");
		this.label = jsonObject.getString("LABEL");
		this.type = jsonObject.getString("TYPE");
		this.linodeId = jsonObject.getLong("LinodeID");
		this.isReadOnly = (1 == jsonObject.getInt("ISREADONLY"));
		this.status = jsonObject.getInt("STATUS");
		this.createDate = ResponseHelper.convertDate(jsonObject.getString("CREATE_DT"));
		this.size = jsonObject.getLong("SIZE");
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
