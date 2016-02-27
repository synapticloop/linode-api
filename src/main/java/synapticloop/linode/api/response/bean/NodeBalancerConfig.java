package synapticloop.linode.api.response.bean;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.helper.ResponseHelper;

public class NodeBalancerConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(NodeBalancerConfig.class);

	private String stickiness = null;
	private String checkPath = null;
	private Integer port = null;
	private String checkBody = null;
	private String check = null;
	private Integer checkInterval = null;
	private String protocol = null;
	private Long configId = null;
	private String algorithm = null;
	private Integer checkTimeout = null;
	private Long nodeBalancerId = null;
	private Long checkAttempts = null;
	private boolean checkPassive = false;
	private String sslCommonName = null;
	private String cipherSuite = null;
	private String sslFingerprint = null;
	/**
	 *       {
         STICKINESS: "table",
         CHECK_PATH: "/",
         PORT: 80,
         CHECK_BODY: "",
         CHECK: "connection",
         CHECK_INTERVAL: 5,
         PROTOCOL: "http",
         CONFIGID: 38,
         ALGORITHM: "roundrobin",
         CHECK_TIMEOUT: 3,
         NODEBALANCERID: 714,
         CHECK_ATTEMPTS: 2,
         CHECK_PASSIVE: 1

         SSL_COMMONNAME: ""
			CIPHER_SUITE: ""
			CHECK: ""
			SSL_FINGERPRINT:""
      },

	 * @param jsonObject
	 */
	public NodeBalancerConfig(JSONObject jsonObject) {
		this.stickiness = jsonObject.getString("STICKINESS");
		jsonObject.remove("STICKINESS");
		this.checkPath = jsonObject.getString("CHECK_PATH");
		jsonObject.remove("CHECK_PATH");
		this.port = jsonObject.getInt("PORT");
		jsonObject.remove("PORT");
		this.checkBody = jsonObject.getString("CHECK_BODY");
		jsonObject.remove("CHECK_BODY");
		this.protocol = jsonObject.getString("PROTOCOL");
		jsonObject.remove("PROTOCOL");
		this.configId = jsonObject.getLong("CONFIGID");
		jsonObject.remove("CONFIGID");
		this.algorithm = jsonObject.getString("ALGORITHM");
		jsonObject.remove("ALGORITHM");
		this.checkTimeout = jsonObject.getInt("CHECK_TIMEOUT");
		jsonObject.remove("CHECK_TIMEOUT");
		this.nodeBalancerId = jsonObject.getLong("NODEBALANCERID");
		jsonObject.remove("NODEBALANCERID");
		this.checkAttempts = jsonObject.getLong("CHECK_ATTEMPTS");
		jsonObject.remove("CHECK_ATTEMPTS");
		this.checkPassive = (1 == jsonObject.getInt("CHECK_PASSIVE"));
		jsonObject.remove("CHECK_PASSIVE");
		this.sslCommonName = jsonObject.getString("SSL_COMMONNAME");
		jsonObject.remove("SSL_COMMONNAME");
		this.cipherSuite = jsonObject.getString("CIPHER_SUITE");
		jsonObject.remove("CIPHER_SUITE");
		this.checkInterval = jsonObject.getInt("CHECK_INTERVAL");
		jsonObject.remove("CHECK_INTERVAL");
		this.check = jsonObject.getString("CHECK");
		jsonObject.remove("CHECK");
		this.sslFingerprint = jsonObject.getString("SSL_FINGERPRINT");
		jsonObject.remove("SSL_FINGERPRINT");


		ResponseHelper.warnOnMissedKeys(LOGGER, jsonObject);
	}

	public String getStickiness() {
		return this.stickiness;
	}

	public String getCheckPath() {
		return this.checkPath;
	}

	public Integer getPort() {
		return this.port;
	}

	public String getCheckBody() {
		return this.checkBody;
	}

	public String getCheck() {
		return this.check;
	}

	public Integer getCheckInterval() {
		return this.checkInterval;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public Long getConfigId() {
		return this.configId;
	}

	public String getAlgorithm() {
		return this.algorithm;
	}

	public Integer getCheckTimeout() {
		return this.checkTimeout;
	}

	public Long getNodeBalancerId() {
		return this.nodeBalancerId;
	}

	public Long getCheckAttempts() {
		return this.checkAttempts;
	}

	public boolean getCheckPassive() {
		return this.checkPassive;
	}

	public String getSslCommonName() {
		return this.sslCommonName;
	}

	public String getCipherSuite() {
		return this.cipherSuite;
	}

	public String getSslFingerprint() {
		return this.sslFingerprint;
	}
}
