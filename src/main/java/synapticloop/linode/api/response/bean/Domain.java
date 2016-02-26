package synapticloop.linode.api.response.bean;

import org.json.JSONObject;

public class Domain {
	private Long domainId = null;
	private String description = null;
	private String type = null;
	private Integer status = null;
	private String soaEmail = null;
	private String domain = null;
	private Long numRetrySeconds = null;
	private String masterIps = null;
	private Long numExpireSeconds = null;
	private Long numRefreshSeconds = null;
	private Long numTtlSeconds = null;

	
	/**
	 *       {
	 *          "DOMAINID":5093,
	 *          "DESCRIPTION":"",
	 *          "TYPE":"master",
	 *          "STATUS":1,
	 *          "SOA_EMAIL":"dns@example.com",
	 *          "DOMAIN":"linode.com",
	 *          "RETRY_SEC":0,
	 *          "MASTER_IPS":"",
	 *          "EXPIRE_SEC":0,
	 *          "REFRESH_SEC":0,
	 *          "TTL_SEC":0
	 * 
	 * @param jsonObject
	 */
	public Domain(JSONObject jsonObject) {
		this.domainId = jsonObject.getLong("DOMAINID");
		this.description = jsonObject.getString("DESCRIPTION");
		this.status = jsonObject.getInt("STATUS");
		this.soaEmail = jsonObject.getString("SOA_EMAIL");
		this.domain = jsonObject.getString("DOMAIN");
		this.numRetrySeconds = jsonObject.getLong("RETRY_SEC");
		this.masterIps = jsonObject.getString("MASTER_IPS");
		this.numExpireSeconds = jsonObject.getLong("EXPIRE_SEC");
		this.numRefreshSeconds = jsonObject.getLong("REFRESH_SEC");
		this.numTtlSeconds = jsonObject.getLong("TTL_SEC");
	}


	public Long getDomainId() {
		return this.domainId;
	}


	public String getDescription() {
		return this.description;
	}


	public String getType() {
		return this.type;
	}


	public Integer getStatus() {
		return this.status;
	}


	public String getSoaEmail() {
		return this.soaEmail;
	}


	public String getDomain() {
		return this.domain;
	}


	public Long getNumRetrySeconds() {
		return this.numRetrySeconds;
	}


	public String getMasterIps() {
		return this.masterIps;
	}


	public Long getNumExpireSeconds() {
		return this.numExpireSeconds;
	}


	public Long getNumRefreshSeconds() {
		return this.numRefreshSeconds;
	}


	public Long getNumTtlSeconds() {
		return this.numTtlSeconds;
	}
}
