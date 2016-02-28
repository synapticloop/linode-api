package synapticloop.linode.api.response.bean;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.exception.ApiException;

public class Image {
	private static final Logger LOGGER = LoggerFactory.getLogger(Image.class);

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

	public Image(JSONObject jsonObject) throws ApiException {
		this.createDate = ResponseHelper.convertDate(jsonObject.getString("CREATE_DT"));
		jsonObject.remove("CREATE_DT");
		this.creator = jsonObject.getString("CREATOR");
		jsonObject.remove("CREATOR");
		this.description = jsonObject.getString("DESCRIPTION");
		jsonObject.remove("DESCRIPTION");
		this.fileSystemType = jsonObject.getString("FS_TYPE");
		jsonObject.remove("FS_TYPE");
		this.imageId = jsonObject.getLong("IMAGEID");
		jsonObject.remove("IMAGEID");
		this.isPublic = (1 == jsonObject.getInt("ISPUBLIC"));
		jsonObject.remove("ISPUBLIC");
		this.label = jsonObject.getString("LABEL");
		jsonObject.remove("LABEL");
		this.lastUsedDate = ResponseHelper.convertDate(jsonObject.getString("LAST_USED_DT"));
		jsonObject.remove("LAST_USED_DT");
		this.minSize = jsonObject.getLong("MINSIZE");
		jsonObject.remove("MINSIZE");
		this.status = jsonObject.getString("STATUS");
		jsonObject.remove("STATUS");
		this.type = jsonObject.getString("TYPE");
		jsonObject.remove("TYPE");

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
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
