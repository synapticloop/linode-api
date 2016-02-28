package synapticloop.linode.api.response;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Image;

public class ImageResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageResponse.class);

	private Image image = null;

	public ImageResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
		this.image = new Image(jsonObject.getJSONArray(JSON_KEY_DATA).getJSONObject(0));
		}
		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
		
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
