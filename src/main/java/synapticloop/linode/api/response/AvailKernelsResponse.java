package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.linode.api.response.bean.Kernel;

public class AvailKernelsResponse extends BaseResponse {
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
	 * @param jsonObject
	 */
	public AvailKernelsResponse(JSONObject jsonObject) {
		super(jsonObject);
		JSONArray dataArray = jsonObject.getJSONArray("DATA");

		for (Object KernelObject : dataArray) {
			Kernel kernel = new Kernel((JSONObject)KernelObject);
			kernels.add(kernel);
			kernelIdLookup.put(kernel.getKernelId(), kernel);
		}
	}

	public List<Kernel> getKernels() {
		return(kernels);
	}

	public Kernel getKernelById(Long id) {
		return(kernelIdLookup.get(id));
	}
}