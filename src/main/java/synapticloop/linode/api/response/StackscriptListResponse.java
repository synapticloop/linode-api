package synapticloop.linode.api.response;

import org.json.JSONObject;

public class StackscriptListResponse extends AvailStackscriptsResponse {

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
	public StackscriptListResponse(JSONObject jsonObject) {
		super(jsonObject);
	}

}
