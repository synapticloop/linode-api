package synapticloop.linode.api.response.bean;

import org.json.JSONObject;

public class DomainResource {
	private String protocol = null;
	private Long numTtlSeconds = null;
	private Integer numPriority = null;
	private String type = null;
	private String target = null;
	private Integer weight = null;
	private Long resourceId = null;
	private Integer port = null;
	private Long domainId = null;
	private String name = null;

	
	/**
	 *       {
	 *          "PROTOCOL":"",
	 *          "TTL_SEC":0,
	 *          "PRIORITY":0,
	 *          "TYPE":"A",
	 *          "TARGET":"75.127.96.245",
	 *          "WEIGHT":0,
	 *          "RESOURCEID":28536,
	 *          "PORT":0,
	 *          "DOMAINID":5093,
	 *          "NAME":"www"
	 *       },
	 * 
	 * @param jsonObject
	 */
	public DomainResource(JSONObject jsonObject) {
		this.protocol = jsonObject.getString("PROTOCOL");
		this.numTtlSeconds = jsonObject.getLong("TTL_SEC");
		this.type = jsonObject.getString("TYPE");
		this.target = jsonObject.getString("TARGET");
		this.weight = jsonObject.getInt("WEIGHT");
		this.resourceId = jsonObject.getLong("RESOURCEID");
		this.port = jsonObject.getInt("PORT");
		this.domainId = jsonObject.getLong("DOMAINID");
		this.name = jsonObject.getString("NAME");
	}


	public String getProtocol() {
		return this.protocol;
	}


	public Long getNumTtlSeconds() {
		return this.numTtlSeconds;
	}


	public Integer getNumPriority() {
		return this.numPriority;
	}


	public String getType() {
		return this.type;
	}


	public String getTarget() {
		return this.target;
	}


	public Integer getWeight() {
		return this.weight;
	}


	public Long getResourceId() {
		return this.resourceId;
	}


	public Integer getPort() {
		return this.port;
	}


	public Long getDomainId() {
		return this.domainId;
	}


	public String getName() {
		return this.name;
	}
}
