package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.linode.api.response.bean.Image;

public class ImageListResponse extends BaseResponse {
	private List<Image> images = new ArrayList<Image>();

	public ImageListResponse(JSONObject jsonObject) {
		super(jsonObject);
		JSONArray jsonArray = jsonObject.getJSONArray("DATA");
		for (Object object : jsonArray) {
			images.add(new Image((JSONObject)object));
		}
	}
}
