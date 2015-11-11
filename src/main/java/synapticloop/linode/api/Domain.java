package synapticloop.linode.api;

// - - - - thoughtfully generated by synapticloop linode-api - - - - 
//     with the use of synapticloop templar templating language
//               (java-create-api.templar)

import java.util.HashMap;
import java.util.Map;

import synapticloop.linode.LinodeRequest;
import synapticloop.linode.exception.ApiException;

/**
 * This is the interaction class for the Domain api calls, this was automatically
 * generated from the linode api documentation - which can be found here:
 * <a href="http://www.linode.com/api/dns">http://www.linode.com/api/dns</a>
 * 
 * @author synapticloop
 */

public class Domain extends ApiBase {
	private static final String PARAM_CONSTANT_DOMAIN = "Domain";
	private static final String PARAM_CONSTANT_DESCRIPTION = "Description";
	private static final String PARAM_CONSTANT_TYPE = "Type";
	private static final String PARAM_CONSTANT_SOA_EMAIL = "SOA_Email";
	private static final String PARAM_CONSTANT_REFRESH_SEC = "Refresh_sec";
	private static final String PARAM_CONSTANT_RETRY_SEC = "Retry_sec";
	private static final String PARAM_CONSTANT_EXPIRE_SEC = "Expire_sec";
	private static final String PARAM_CONSTANT_TTL_SEC = "TTL_sec";
	private static final String PARAM_CONSTANT_LPM_DISPLAYGROUP = "lpm_displayGroup";
	private static final String PARAM_CONSTANT_STATUS = "status";
	private static final String PARAM_CONSTANT_MASTER_IPS = "master_ips";
	private static final String PARAM_CONSTANT_AXFR_IPS = "axfr_ips";
	private static final String PARAM_CONSTANT_DOMAINID = "DomainID";
	private static final String PARAM_CONSTANT_NAME = "Name";
	private static final String PARAM_CONSTANT_TARGET = "Target";
	private static final String PARAM_CONSTANT_PRIORITY = "Priority";
	private static final String PARAM_CONSTANT_WEIGHT = "Weight";
	private static final String PARAM_CONSTANT_PORT = "Port";
	private static final String PARAM_CONSTANT_PROTOCOL = "Protocol";
	private static final String PARAM_CONSTANT_RESOURCEID = "ResourceID";

	/**
	 * Private constructor to deter instantiation
	 */
	private Domain() {}

	/**
	 * <p>Create a domain record.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.create",
	 *    "DATA":{
	 *       "DomainID":5123
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - VALIDATION
	 *
	 * @param domain  <strong>(REQUIRED)</strong>   The zone's name
	 * @param type  <strong>(REQUIRED)</strong>   master or slave
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public static LinodeRequest create(String domain, String type) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAIN, domain, false);
		addParameterSafely(parameters, PARAM_CONSTANT_TYPE, type, false);
		return(new LinodeRequest("domain.create", parameters));
	}

	/**
	 * <p>Create a domain record.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.create",
	 *    "DATA":{
	 *       "DomainID":5123
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - VALIDATION
	 *
	 * @param domain  <strong>(REQUIRED)</strong> The zone's name
	 * @param description  <em>(OPTIONAL)</em> Currently undisplayed.
	 * @param type  <strong>(REQUIRED)</strong> master or slave
	 * @param SOA_Email  <em>(OPTIONAL)</em> Required when type=master
	 * @param Refresh_sec  <em>(OPTIONAL)</em> 
	 * @param Retry_sec  <em>(OPTIONAL)</em> 
	 * @param Expire_sec  <em>(OPTIONAL)</em> 
	 * @param TTL_sec  <em>(OPTIONAL)</em> 
	 * @param lpm_displayGroup  <em>(OPTIONAL)</em> Display group in the Domain list inside the Linode DNS Manager
	 * @param status  <em>(OPTIONAL)</em> 0, 1, or 2 (disabled, active, edit mode)
	 * @param master_ips  <em>(OPTIONAL)</em> When type=slave, the zone's master DNS servers list, semicolon separated 
	 * @param axfr_ips  <em>(OPTIONAL)</em> IP addresses allowed to AXFR the entire zone, semicolon separated
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public static LinodeRequest create(String domain, String description, String type, String SOA_Email, Long Refresh_sec, Long Retry_sec, Long Expire_sec, Long TTL_sec, String lpm_displayGroup, Long status, String master_ips, String axfr_ips) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAIN, domain, false);
		addParameterSafely(parameters, PARAM_CONSTANT_DESCRIPTION, description, true);
		addParameterSafely(parameters, PARAM_CONSTANT_TYPE, type, false);
		addParameterSafely(parameters, PARAM_CONSTANT_SOA_EMAIL, SOA_Email, true);
		addParameterSafely(parameters, PARAM_CONSTANT_REFRESH_SEC, Refresh_sec, true);
		addParameterSafely(parameters, PARAM_CONSTANT_RETRY_SEC, Retry_sec, true);
		addParameterSafely(parameters, PARAM_CONSTANT_EXPIRE_SEC, Expire_sec, true);
		addParameterSafely(parameters, PARAM_CONSTANT_TTL_SEC, TTL_sec, true);
		addParameterSafely(parameters, PARAM_CONSTANT_LPM_DISPLAYGROUP, lpm_displayGroup, true);
		addParameterSafely(parameters, PARAM_CONSTANT_STATUS, status, true);
		addParameterSafely(parameters, PARAM_CONSTANT_MASTER_IPS, master_ips, true);
		addParameterSafely(parameters, PARAM_CONSTANT_AXFR_IPS, axfr_ips, true);
		return(new LinodeRequest("domain.create", parameters));
	}

	/**
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.delete",
	 *    "DATA":{
	 *       "DomainID":5123
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - NOTFOUND
	 *
	 * @param domainID  <strong>(REQUIRED)</strong>   
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public static LinodeRequest delete(Long domainID) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAINID, domainID, false);
		return(new LinodeRequest("domain.delete", parameters));
	}

	/**
	 * <p>Lists domains you have access to.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.list",
	 *    "DATA":[
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
	 *       },
	 *       {
	 *          "DOMAINID":5125,
	 *          "DESCRIPTION":"",
	 *          "TYPE":"slave",
	 *          "STATUS":1,
	 *          "SOA_EMAIL":"",
	 *          "DOMAIN":"nodefs.com",
	 *          "RETRY_SEC":0,
	 *          "MASTER_IPS":"1.3.5.7;2.4.6.8;",
	 *          "EXPIRE_SEC":0,
	 *          "REFRESH_SEC":0,
	 *          "TTL_SEC":0
	 *       }
	 *    ]
	 * }
	 * </pre>
	 *
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public static LinodeRequest list() throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		return(new LinodeRequest("domain.list", parameters));
	}

	/**
	 * <p>Lists domains you have access to.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.list",
	 *    "DATA":[
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
	 *       },
	 *       {
	 *          "DOMAINID":5125,
	 *          "DESCRIPTION":"",
	 *          "TYPE":"slave",
	 *          "STATUS":1,
	 *          "SOA_EMAIL":"",
	 *          "DOMAIN":"nodefs.com",
	 *          "RETRY_SEC":0,
	 *          "MASTER_IPS":"1.3.5.7;2.4.6.8;",
	 *          "EXPIRE_SEC":0,
	 *          "REFRESH_SEC":0,
	 *          "TTL_SEC":0
	 *       }
	 *    ]
	 * }
	 * </pre>
	 *
	 * @param domainID  <em>(OPTIONAL)</em> Limits the list to the specified DomainID
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public static LinodeRequest list(Long domainID) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAINID, domainID, true);
		return(new LinodeRequest("domain.list", parameters));
	}

	/**
	 * <p>Create a domain record.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.resource.create",
	 *    "DATA":{
	 *       "ResourceID":28537
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - VALIDATION
	 *
	 * @param domainID  <strong>(REQUIRED)</strong>   
	 * @param type  <strong>(REQUIRED)</strong>   One of: NS, MX, A, AAAA, CNAME, TXT, or SRV
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public static LinodeRequest resourcecreate(Long domainID, String type) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAINID, domainID, false);
		addParameterSafely(parameters, PARAM_CONSTANT_TYPE, type, false);
		return(new LinodeRequest("domain.resource.create", parameters));
	}

	/**
	 * <p>Create a domain record.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.resource.create",
	 *    "DATA":{
	 *       "ResourceID":28537
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - VALIDATION
	 *
	 * @param domainID  <strong>(REQUIRED)</strong> 
	 * @param type  <strong>(REQUIRED)</strong> One of: NS, MX, A, AAAA, CNAME, TXT, or SRV
	 * @param name  <em>(OPTIONAL)</em> The hostname or FQDN. When Type=MX the subdomain to delegate to the Target MX server.
	 * @param target  <em>(OPTIONAL)</em> When Type=MX the hostname. When Type=CNAME the target of the alias. When Type=TXT the value of the record. When Type=A or AAAA the token of '[remote_addr]' will be substituted with the IP address of the request.
	 * @param priority  <em>(OPTIONAL)</em> Priority for MX and SRV records, 0-255
	 * @param weight  <em>(OPTIONAL)</em> 
	 * @param port  <em>(OPTIONAL)</em> 
	 * @param protocol  <em>(OPTIONAL)</em> The protocol to append to an SRV record. Ignored on other record types.
	 * @param TTL_sec  <em>(OPTIONAL)</em> TTL. Leave as 0 to accept our default.
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public static LinodeRequest resourcecreate(Long domainID, String type, String name, String target, Long priority, Long weight, Long port, String protocol, Long TTL_sec) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAINID, domainID, false);
		addParameterSafely(parameters, PARAM_CONSTANT_TYPE, type, false);
		addParameterSafely(parameters, PARAM_CONSTANT_NAME, name, true);
		addParameterSafely(parameters, PARAM_CONSTANT_TARGET, target, true);
		addParameterSafely(parameters, PARAM_CONSTANT_PRIORITY, priority, true);
		addParameterSafely(parameters, PARAM_CONSTANT_WEIGHT, weight, true);
		addParameterSafely(parameters, PARAM_CONSTANT_PORT, port, true);
		addParameterSafely(parameters, PARAM_CONSTANT_PROTOCOL, protocol, true);
		addParameterSafely(parameters, PARAM_CONSTANT_TTL_SEC, TTL_sec, true);
		return(new LinodeRequest("domain.resource.create", parameters));
	}

	/**
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.resource.delete",
	 *    "DATA":{
	 *       "ResourceID":28537
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - NOTFOUND
	 *
	 * @param domainID  <strong>(REQUIRED)</strong>   
	 * @param resourceID  <strong>(REQUIRED)</strong>   
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public static LinodeRequest resourcedelete(Long domainID, Long resourceID) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAINID, domainID, false);
		addParameterSafely(parameters, PARAM_CONSTANT_RESOURCEID, resourceID, false);
		return(new LinodeRequest("domain.resource.delete", parameters));
	}

	/**
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.resource.list",
	 *    "DATA":[
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
	 *       {
	 *          "PROTOCOL":"",
	 *          "TTL_SEC":0,
	 *          "PRIORITY":0,
	 *          "TYPE":"A",
	 *          "TARGET":"75.127.96.245",
	 *          "WEIGHT":0,
	 *          "RESOURCEID":28537,
	 *          "PORT":0,
	 *          "DOMAINID":5093,
	 *          "NAME":"mail"
	 *       }
	 *    ]
	 * }
	 * </pre>
	 *
	 * @param domainID  <strong>(REQUIRED)</strong>   
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public static LinodeRequest resourcelist(Long domainID) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAINID, domainID, false);
		return(new LinodeRequest("domain.resource.list", parameters));
	}

	/**
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.resource.list",
	 *    "DATA":[
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
	 *       {
	 *          "PROTOCOL":"",
	 *          "TTL_SEC":0,
	 *          "PRIORITY":0,
	 *          "TYPE":"A",
	 *          "TARGET":"75.127.96.245",
	 *          "WEIGHT":0,
	 *          "RESOURCEID":28537,
	 *          "PORT":0,
	 *          "DOMAINID":5093,
	 *          "NAME":"mail"
	 *       }
	 *    ]
	 * }
	 * </pre>
	 *
	 * @param domainID  <strong>(REQUIRED)</strong> 
	 * @param resourceID  <em>(OPTIONAL)</em> 
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public static LinodeRequest resourcelist(Long domainID, Long resourceID) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAINID, domainID, false);
		addParameterSafely(parameters, PARAM_CONSTANT_RESOURCEID, resourceID, true);
		return(new LinodeRequest("domain.resource.list", parameters));
	}

	/**
	 * <p>Update a domain record.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.resource.update",
	 *    "DATA":{
	 *       "ResourceID":28537
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - NOTFOUND
	 *   - VALIDATION
	 *
	 * @param resourceID  <strong>(REQUIRED)</strong>   
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public static LinodeRequest resourceupdate(Long resourceID) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_RESOURCEID, resourceID, false);
		return(new LinodeRequest("domain.resource.update", parameters));
	}

	/**
	 * <p>Update a domain record.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.resource.update",
	 *    "DATA":{
	 *       "ResourceID":28537
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - NOTFOUND
	 *   - VALIDATION
	 *
	 * @param domainID  <em>(OPTIONAL)</em> 
	 * @param resourceID  <strong>(REQUIRED)</strong> 
	 * @param name  <em>(OPTIONAL)</em> The hostname or FQDN. When Type=MX the subdomain to delegate to the Target MX server.
	 * @param target  <em>(OPTIONAL)</em> When Type=MX the hostname. When Type=CNAME the target of the alias. When Type=TXT the value of the record. When Type=A or AAAA the token of '[remote_addr]' will be substituted with the IP address of the request.
	 * @param priority  <em>(OPTIONAL)</em> Priority for MX and SRV records, 0-255
	 * @param weight  <em>(OPTIONAL)</em> 
	 * @param port  <em>(OPTIONAL)</em> 
	 * @param protocol  <em>(OPTIONAL)</em> The protocol to append to an SRV record. Ignored on other record types.
	 * @param TTL_sec  <em>(OPTIONAL)</em> TTL. Leave as 0 to accept our default.
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public static LinodeRequest resourceupdate(Long domainID, Long resourceID, String name, String target, Long priority, Long weight, Long port, String protocol, Long TTL_sec) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAINID, domainID, true);
		addParameterSafely(parameters, PARAM_CONSTANT_RESOURCEID, resourceID, false);
		addParameterSafely(parameters, PARAM_CONSTANT_NAME, name, true);
		addParameterSafely(parameters, PARAM_CONSTANT_TARGET, target, true);
		addParameterSafely(parameters, PARAM_CONSTANT_PRIORITY, priority, true);
		addParameterSafely(parameters, PARAM_CONSTANT_WEIGHT, weight, true);
		addParameterSafely(parameters, PARAM_CONSTANT_PORT, port, true);
		addParameterSafely(parameters, PARAM_CONSTANT_PROTOCOL, protocol, true);
		addParameterSafely(parameters, PARAM_CONSTANT_TTL_SEC, TTL_sec, true);
		return(new LinodeRequest("domain.resource.update", parameters));
	}

	/**
	 * <p>Update a domain record.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.update",
	 *    "DATA":{
	 *       "DomainID":5123
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - NOTFOUND
	 *   - VALIDATION
	 *
	 * @param domainID  <strong>(REQUIRED)</strong>   
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public static LinodeRequest update(Long domainID) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAINID, domainID, false);
		return(new LinodeRequest("domain.update", parameters));
	}

	/**
	 * <p>Update a domain record.</p> 
	 * 
	 * Example response:
	 * 
	 * <pre>
	 * {
	 *    "ERRORARRAY":[],
	 *    "ACTION":"domain.update",
	 *    "DATA":{
	 *       "DomainID":5123
	 *    }
	 * }
	 * </pre>
	 * 
	 * Possible return error codes:
	 * 
	 *   - NOTFOUND
	 *   - VALIDATION
	 *
	 * @param domainID  <strong>(REQUIRED)</strong> 
	 * @param domain  <em>(OPTIONAL)</em> The zone's name
	 * @param description  <em>(OPTIONAL)</em> Currently undisplayed.
	 * @param type  <em>(OPTIONAL)</em> master or slave
	 * @param SOA_Email  <em>(OPTIONAL)</em> Required when type=master
	 * @param Refresh_sec  <em>(OPTIONAL)</em> 
	 * @param Retry_sec  <em>(OPTIONAL)</em> 
	 * @param Expire_sec  <em>(OPTIONAL)</em> 
	 * @param TTL_sec  <em>(OPTIONAL)</em> 
	 * @param lpm_displayGroup  <em>(OPTIONAL)</em> Display group in the Domain list inside the Linode DNS Manager
	 * @param status  <em>(OPTIONAL)</em> 0, 1, or 2 (disabled, active, edit mode)
	 * @param master_ips  <em>(OPTIONAL)</em> When type=slave, the zone's master DNS servers list, semicolon separated 
	 * @param axfr_ips  <em>(OPTIONAL)</em> IP addresses allowed to AXFR the entire zone, semicolon separated
	 *
	 * @return the linode request object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public static LinodeRequest update(Long domainID, String domain, String description, String type, String SOA_Email, Long Refresh_sec, Long Retry_sec, Long Expire_sec, Long TTL_sec, String lpm_displayGroup, Long status, String master_ips, String axfr_ips) throws ApiException {
		Map<String, String> parameters = new HashMap<String, String>();
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAINID, domainID, false);
		addParameterSafely(parameters, PARAM_CONSTANT_DOMAIN, domain, true);
		addParameterSafely(parameters, PARAM_CONSTANT_DESCRIPTION, description, true);
		addParameterSafely(parameters, PARAM_CONSTANT_TYPE, type, true);
		addParameterSafely(parameters, PARAM_CONSTANT_SOA_EMAIL, SOA_Email, true);
		addParameterSafely(parameters, PARAM_CONSTANT_REFRESH_SEC, Refresh_sec, true);
		addParameterSafely(parameters, PARAM_CONSTANT_RETRY_SEC, Retry_sec, true);
		addParameterSafely(parameters, PARAM_CONSTANT_EXPIRE_SEC, Expire_sec, true);
		addParameterSafely(parameters, PARAM_CONSTANT_TTL_SEC, TTL_sec, true);
		addParameterSafely(parameters, PARAM_CONSTANT_LPM_DISPLAYGROUP, lpm_displayGroup, true);
		addParameterSafely(parameters, PARAM_CONSTANT_STATUS, status, true);
		addParameterSafely(parameters, PARAM_CONSTANT_MASTER_IPS, master_ips, true);
		addParameterSafely(parameters, PARAM_CONSTANT_AXFR_IPS, axfr_ips, true);
		return(new LinodeRequest("domain.update", parameters));
	}

};