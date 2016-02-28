package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Kernel;

public class AvailKernelsResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(AvailKernelsResponse.class);

	private List<Kernel> kernels = new ArrayList<Kernel>();
	private Map<Long, Kernel> kernelIdLookup = new HashMap<Long, Kernel>();

	/**
	 * 
	 * The json should have been formed by the following:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"avail.kernels",
	 *    "DATA":[
	 *       {
	 *          "LABEL":"2.6.32.16-linode28",
	 *          "ISXEN":1,
	 *          "ISKVM":0,
	 *          "ISPVOPS":1,
	 *          "KERNELID":123
	 *       }
	 *    ]
	 * }
	 * </pre>
	 * 
	 * @param jsonObject the json object to parse
	 */
	public AvailKernelsResponse(JSONObject jsonObject) {
		super(jsonObject);
		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray(JSON_KEY_DATA);

			for (Object KernelObject : dataArray) {
				Kernel kernel = new Kernel((JSONObject)KernelObject);
				kernels.add(kernel);
				kernelIdLookup.put(kernel.getKernelId(), kernel);
			}
		}
		jsonObject.remove(JSON_KEY_DATA);

		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<Kernel> getKernels() {
		return(kernels);
	}

	public Kernel getKernelById(Long id) {
		return(kernelIdLookup.get(id));
	}
}
