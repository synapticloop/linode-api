package synapticloop.linode.api.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

public class AvailStackscriptsResponse extends BaseResponse {
	private String script = null;
	private String description = null;
	private List<Long> distributionIds = new ArrayList<Long>();
	private Long numDeploymentsTotal = null;
	private Long latestRevision = null;
	private Date createDate = null;
	private Long numDeploymentsActive = null;
	private Long stackscriptId = null;
	private String revisionNote = null;
	private Date revisionDate = null;
	private boolean isPublic = false;
	private Long userId = null;

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
		
		this.script = jsonObject.getString("SCRIPT");
		this.description = jsonObject.getString("DESCRIPTION");
		String[] distributionIdArray = jsonObject.optString("DISTRIBUTIONIDLIST", "").split(",");
		for (String distributionId : distributionIdArray) {
			distributionIds.add(Long.valueOf(distributionId));
		}
	}

	public String getScript() {
		return this.script;
	}

	public String getDescription() {
		return this.description;
	}

	public List<Long> getDistributionIds() {
		return this.distributionIds;
	}

	public Long getNumDeploymentsTotal() {
		return this.numDeploymentsTotal;
	}

	public Long getLatestRevision() {
		return this.latestRevision;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public Long getNumDeploymentsActive() {
		return this.numDeploymentsActive;
	}

	public Long getStackscriptId() {
		return this.stackscriptId;
	}

	public String getRevisionNote() {
		return this.revisionNote;
	}

	public Date getRevisionDate() {
		return this.revisionDate;
	}

	public boolean getIsPublic() {
		return this.isPublic;
	}

	public Long getUserId() {
		return this.userId;
	}

}
