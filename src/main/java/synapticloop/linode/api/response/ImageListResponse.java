package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Image;

public class ImageListResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(Image.class);

	private List<Image> images = new ArrayList<Image>();

	public ImageListResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONArray jsonArray = jsonObject.getJSONArray(JSON_KEY_DATA);
			for (Object object : jsonArray) {
				images.add(new Image((JSONObject)object));
			}
		}
		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}
}
