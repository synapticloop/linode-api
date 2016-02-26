package synapticloop.linode.api.response;

import java.util.Date;

import org.json.JSONObject;

import synapticloop.linode.api.response.bean.Image;

public class ImageListResponse extends BaseResponse {
	private Image image = null;

	public ImageListResponse(JSONObject jsonObject) {
		super(jsonObject);
		
		this.image = new Image(jsonObject.getJSONObject("DATA"));
	}

	public Date getCreateDate() {
		return this.image.getCreateDate();
	}

	public String getCreator() {
		return this.image.getCreator();
	}

	public String getDescription() {
		return this.image.getDescription();
	}

	public String getFileSystemType() {
		return this.image.getFileSystemType();
	}

	public Long getImageId() {
		return this.image.getImageId();
	}

	public boolean getIsPublic() {
		return this.image.getIsPublic();
	}

	public String getLabel() {
		return this.image.getLabel();
	}

	public Date getLastUsedDate() {
		return this.image.getLastUsedDate();
	}

	public Long getMinSize() {
		return this.image.getMinSize();
	}

	public String getStatus() {
		return this.image.getStatus();
	}

	public String getType() {
		return this.image.getType();
	}

}
