package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.linode.api.response.bean.Disk;

public class LinodeDiskListResponse extends AvailStackscriptsResponse {
	private List<Disk> disks = new ArrayList<Disk>();
	/**
	 * 
	 * @param jsonObject
	 */
	public LinodeDiskListResponse(JSONObject jsonObject) {
		super(jsonObject);
		JSONArray dataArray = jsonObject.getJSONArray("DATA");
		for (Object object : dataArray) {
			disks.add(new Disk((JSONObject)object));
		}
	}

	public List<Disk> getDisks() {
		return this.disks;
	}

}
