package synapticloop.linode.api.response.bean;

import java.util.Date;

import org.json.JSONObject;

import synapticloop.linode.api.response.BaseResponse;

public class Image {
	private Date createDate = null;
	private String creator = null;
	private String description = null;
	private String fileSystemType = null;
	private Long imageId = null;
	private boolean isPublic = false;
	private String label = null;
	private Date lastUsedDate = null;
	private Long minSize = null;
	private String status = null;
	private String type = null;

	public Image(JSONObject jsonObject) {
		this.createDate = BaseResponse.convertDate(jsonObject.getString("CREATE_DT"));
		this.creator = jsonObject.getString("CREATOR");
		this.description = jsonObject.getString("DESCRIPTION");
		this.fileSystemType = jsonObject.getString("FS_TYPE");
		this.imageId = jsonObject.getLong("IMAGEID");
		this.isPublic = "1".equals(jsonObject.getString("ISPUBLIC"));
		this.label = jsonObject.getString("LABEL");
		this.lastUsedDate = BaseResponse.convertDate(jsonObject.getString("LAST_USED_DT"));
		this.minSize = jsonObject.getLong("MINSIZE");
		this.status = jsonObject.getString("STATUS");
		this.type = jsonObject.getString("TYPE");
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreator() {
		return this.creator;
	}

	public String getDescription() {
		return this.description;
	}

	public String getFileSystemType() {
		return this.fileSystemType;
	}

	public Long getImageId() {
		return this.imageId;
	}

	public boolean getIsPublic() {
		return this.isPublic;
	}

	public String getLabel() {
		return this.label;
	}

	public Date getLastUsedDate() {
		return this.lastUsedDate;
	}

	public Long getMinSize() {
		return this.minSize;
	}

	public String getStatus() {
		return this.status;
	}

	public String getType() {
		return this.type;
	}
}
