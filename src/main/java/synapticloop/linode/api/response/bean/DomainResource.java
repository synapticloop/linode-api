package synapticloop.linode.api.response.bean;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class DomainResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(DomainResource.class);

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
		jsonObject.remove("PROTOCOL");
		this.numTtlSeconds = jsonObject.getLong("TTL_SEC");
		jsonObject.remove("TTL_SEC");
		this.type = jsonObject.getString("TYPE");
		jsonObject.remove("TYPE");
		this.target = jsonObject.getString("TARGET");
		jsonObject.remove("TARGET");
		this.weight = jsonObject.getInt("WEIGHT");
		jsonObject.remove("WEIGHT");
		this.resourceId = jsonObject.getLong("RESOURCEID");
		jsonObject.remove("RESOURCEID");
		this.port = jsonObject.getInt("PORT");
		jsonObject.remove("PORT");
		this.domainId = jsonObject.getLong("DOMAINID");
		jsonObject.remove("DOMAINID");
		this.name = jsonObject.getString("NAME");
		jsonObject.remove("NAME");
		
		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
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
