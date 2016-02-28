package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Disk;

public class LinodeDiskListResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeDiskListResponse.class);

	private List<Disk> disks = new ArrayList<Disk>();

	/**
	 * 
	 * @param jsonObject
	 */
	public LinodeDiskListResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray(JSON_KEY_DATA);
			for (Object object : dataArray) {
				disks.add(new Disk((JSONObject)object));
			}
		}

		jsonObject.remove(JSON_KEY_DATA);
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<Disk> getDisks() {
		return this.disks;
	}

}
