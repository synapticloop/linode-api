package synapticloop.linode.api.response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.IPAddress;

public class LinodeIpSwapResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeIpSwapResponse.class);

	private IPAddress ipAddressFrom = null;
	private IPAddress ipAddressTo = null;
	/**
	 *    "DATA": [
      {
         "LINODEID": 8099,
         "IPADDRESS": "75.128.96.54",
         "IPADDRESSID": 5384
      },
      {
         "IPADDRESS": "75.127.96.245",
         "LINODEID": 8098,
         "IPADDRESSID": 5575
      }
   ],

	 * @param jsonObject
	 */
	public LinodeIpSwapResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray("DATA");
			switch (dataArray.length()) {
			case 2:
				ipAddressTo = new IPAddress(dataArray.getJSONObject(1));
			case 1:
				ipAddressFrom = new IPAddress(dataArray.getJSONObject(0));
				break;
			default:
				break;
			}
		}

		jsonObject.remove("DATA");
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public IPAddress getIpAddressFrom() {
		return this.ipAddressFrom;
	}

	public IPAddress getIpAddressTo() {
		return this.ipAddressTo;
	}
}
