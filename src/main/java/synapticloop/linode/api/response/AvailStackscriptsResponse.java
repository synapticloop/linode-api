package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;
import synapticloop.linode.api.response.bean.Stackscript;

public class AvailStackscriptsResponse extends BaseResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(AvailStackscriptsResponse.class);

	private List<Stackscript> stackscripts = new ArrayList<Stackscript>();
	/**
	 *       {
	 *          SCRIPT: "#!/bin/bash
	 * 
	 *          #this is the content of the first StackScript",
	 *          DESCRIPTION: "",
	 *          DISTRIBUTIONIDLIST: "77,78",
	 *          LABEL: "StackScript001",
	 *          DEPLOYMENTSTOTAL: 1,
	 *          LATESTREV: 1,
	 *          CREATE_DT: "2012-05-22 16:35:42.0",
	 *          DEPLOYMENTSACTIVE: 1,
	 *          STACKSCRIPTID: 1,
	 *          REV_NOTE: "Initial import",
	 *          REV_DT: "2012-05-22 16:35:42.0",
	 *          ISPUBLIC: 1,
	 *          USERID: 91886
	 *       },
	 * 
	 * @param jsonObject
	 */
	public AvailStackscriptsResponse(JSONObject jsonObject) {
		super(jsonObject);

		if(!hasErrors()) {
			JSONArray dataArray = jsonObject.getJSONArray("DATA");
			for (Object object : dataArray) {
				stackscripts.add(new Stackscript((JSONObject)object));
			}
		}

		jsonObject.remove("DATA");
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public List<Stackscript> getStackscripts() {
		return this.stackscripts;
	}
}
