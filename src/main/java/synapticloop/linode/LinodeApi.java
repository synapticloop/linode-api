package synapticloop.linode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.linode.api.request.AccountRequest;
import synapticloop.linode.api.request.ApiRequest;
import synapticloop.linode.api.request.AvailRequest;
import synapticloop.linode.api.request.DomainRequest;
import synapticloop.linode.api.request.ImageRequest;
import synapticloop.linode.api.request.LinodeRequest;
import synapticloop.linode.api.request.NodebalancerRequest;
import synapticloop.linode.api.request.StackscriptRequest;
import synapticloop.linode.api.request.TestRequest;
import synapticloop.linode.api.request.UserRequest;
import synapticloop.linode.api.response.AccountEstimateInvoiceResponse;
import synapticloop.linode.api.response.AccountInfoResponse;
import synapticloop.linode.api.response.ApiSpecResponse;
import synapticloop.linode.api.response.AvailDatacentersResponse;
import synapticloop.linode.api.response.AvailDistributionsResponse;
import synapticloop.linode.api.response.AvailKernelsResponse;
import synapticloop.linode.api.response.AvailLinodePlansResponse;
import synapticloop.linode.api.response.AvailNodeBalancersResponse;
import synapticloop.linode.api.response.AvailStackscriptsResponse;
import synapticloop.linode.api.response.DomainListResponse;
import synapticloop.linode.api.response.DomainResourceListResponse;
import synapticloop.linode.api.response.DomainResourceResponse;
import synapticloop.linode.api.response.DomainResponse;
import synapticloop.linode.api.response.ImageListResponse;
import synapticloop.linode.api.response.ImageResponse;
import synapticloop.linode.api.response.LinodeConfigListResponse;
import synapticloop.linode.api.response.LinodeConfigResponse;
import synapticloop.linode.api.response.LinodeDiskListResponse;
import synapticloop.linode.api.response.LinodeDiskResponse;
import synapticloop.linode.api.response.LinodeImageResponse;
import synapticloop.linode.api.response.LinodeIpListResponse;
import synapticloop.linode.api.response.LinodeIpResponse;
import synapticloop.linode.api.response.LinodeIpSetRdnsResponse;
import synapticloop.linode.api.response.LinodeIpSwapResponse;
import synapticloop.linode.api.response.LinodeJobListResponse;
import synapticloop.linode.api.response.LinodeJobResponse;
import synapticloop.linode.api.response.LinodeListResponse;
import synapticloop.linode.api.response.LinodeResizeResponse;
import synapticloop.linode.api.response.LinodeResponse;
import synapticloop.linode.api.response.NodebalancerConfigListResponse;
import synapticloop.linode.api.response.NodebalancerConfigResponse;
import synapticloop.linode.api.response.NodebalancerListResponse;
import synapticloop.linode.api.response.NodebalancerNodeListResponse;
import synapticloop.linode.api.response.NodebalancerNodeResponse;
import synapticloop.linode.api.response.NodebalancerResponse;
import synapticloop.linode.api.response.StackscriptListResponse;
import synapticloop.linode.api.response.StackscriptResponse;
import synapticloop.linode.api.response.TestEchoResponse;
import synapticloop.linode.api.response.UserGetApiKeyResponse;
import synapticloop.linode.exception.ApiException;

/**
 * The main class for interacting with the Linode api.
 * 
 * @author synapticloop
 */
public class LinodeApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinodeApi.class);

	private static final String API_ENDPOINT = "https://api.linode.com/";

	private static final String PARAMETER_API_KEY = "api_key";
	private static final String PARAMETER_API_ACTION = "api_action";
	private static final String PARAMETER_API_REQUEST_ARRAY = "api_requestArray";

	private static final String PARAMETER_BATCH = "batch";

	private CloseableHttpClient closeableHttpClient = null;

	private String apiKey = null;

	private boolean debug = false;

	/**
	 * Create a Linode client with the specified API key
	 * 
	 * @param apiKey the user's API key
	 */
	public LinodeApi(String apiKey) {
		this(apiKey, false);
	}

	/**
	 * Create a Linode client with specified API key and debug setting
	 * 
	 * @param apiKey the user's API key
	 * @param debug true to enable request debugging, false otherwise
	 */
	public LinodeApi(String apiKey, boolean debug) {
		this.apiKey = apiKey;

		this.closeableHttpClient = HttpClients.createDefault();
		this.debug = debug;
	}

	/**
	 * Execute a single request to the linode api
	 * 
	 * @param linodeApiRequest the request to be made
	 * 
	 * @return the response of the request
	 * 
	 * @throws ApiException if there was a problem with the request
	 */
	public LinodeApiResponse execute(LinodeApiRequest linodeApiRequest) throws ApiException {
		try {
			HttpPost httpPost = new HttpPost(API_ENDPOINT);

			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair(PARAMETER_API_KEY, this.apiKey));
			postParameters.add(new BasicNameValuePair(PARAMETER_API_ACTION, linodeApiRequest.getAction()));

			// now add the parameters
			Map<String, String> parameters = linodeApiRequest.getParameters();
			for (String parameterKey : parameters.keySet()) {
				postParameters.add(new BasicNameValuePair(parameterKey, parameters.get(parameterKey)));
			}

			String response = callApi(linodeApiRequest.getAction(), httpPost, postParameters);

			JSONObject jsonResponseObject = new JSONObject(response);
			JSONArray errorArray = jsonResponseObject.getJSONArray("ERRORARRAY");
			if(errorArray.length() > 0) {
				throw new ApiException("Errors occurred in the call, message was: " + errorArray.toString());
			}

			return(new LinodeApiResponse(jsonResponseObject));
		} catch (JSONException ex) {
			throw new ApiException(ex);
		}
	}

	/**
	 * Execute a batch request to the linode api.
	 * 
	 * @param linodeApiRequests The list of requests to execute
	 * 
	 * @return the list of responses to the requests
	 * 
	 * @throws ApiException if there was an error with the request
	 */
	public List<LinodeApiResponse> execute(List<LinodeApiRequest> linodeApiRequests) throws ApiException {
		List<LinodeApiResponse> linodeApiResponses = new ArrayList<LinodeApiResponse>();
		try {
			HttpPost httpPost = new HttpPost(API_ENDPOINT);

			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair(PARAMETER_API_KEY, this.apiKey));
			postParameters.add(new BasicNameValuePair(PARAMETER_API_ACTION, PARAMETER_BATCH));

			// now add the parameters
			JSONArray apiRequestArray = new JSONArray();
			for (LinodeApiRequest linodeApiRequest : linodeApiRequests) {
				JSONObject jsonObject = new JSONObject();

				Map<String, String> parameters = linodeApiRequest.getParameters();
				jsonObject.put(PARAMETER_API_ACTION, linodeApiRequest.getAction());
				for (String parameterKey : parameters.keySet()) {
					jsonObject.append(parameterKey, parameters.get(parameterKey));
				}
				apiRequestArray.put(jsonObject);
			}

			postParameters.add(new BasicNameValuePair(PARAMETER_API_REQUEST_ARRAY, apiRequestArray.toString()));
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters));

			String response = callApi(PARAMETER_BATCH, httpPost, postParameters);

			// as we are doing batch mode - the return will be an array
			JSONArray jsonArray = new JSONArray(response);
			for(int i = 0; i < jsonArray.length(); i ++) {
				linodeApiResponses.add(new LinodeApiResponse(jsonArray.getJSONObject(i)));
			}
		} catch (JSONException | IOException ex) {
			throw new ApiException(ex);
		}

		return(linodeApiResponses);
	}


	/**
	 * Call the api
	 * 
	 * @param action the action that was being called
	 * @param httpPost the http post object
	 * @param postParameters the post parameters for the httpPost
	 * 
	 * @return The response
	 * 
	 * @throws ApiException If there was an error with the call
	 */
	private String callApi(String action, HttpPost httpPost, ArrayList<NameValuePair> postParameters) throws ApiException {
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();

			throwIfUnsuccessful(action, statusCode);

			String response = null;
			try {
				response = EntityUtils.toString(httpResponse.getEntity());
			} catch (ParseException | IOException ex) {
				throw new ApiException(ex);
			}

			if (debug) {
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("Response for end point '{}', with action '{}': {}", API_ENDPOINT, action, response);
				}
			}
			return(response);

		} catch (IOException ex) {
			throw(new ApiException(ex));
		}
	}

	/**
	 * Throw an ApiException if the status code was not OK (200)
	 * 
	 * @param action The action that was done
	 * @param statusCode The response status code
	 * 
	 * @throws ApiException if the response code was not OK (200)
	 */
	private void throwIfUnsuccessful(String action, int statusCode) throws ApiException {
		if (statusCode != HttpStatus.SC_OK) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Non-200 HTTP Status code returned: ");
			stringBuilder.append(statusCode);
			stringBuilder.append(" for action '");
			stringBuilder.append(action);
			stringBuilder.append("'.");
			throw new ApiException(stringBuilder.toString());
		}
	}

	/**
	 * <p>Deletes a gold-master image</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param imageID <strong>(REQUIRED)</strong>   The ID of the gold-master image to delete
	 *
	 * @return the parsed ImageResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public ImageResponse getImageDelete(Long imageID) throws ApiException {
		return(new ImageResponse(execute(ImageRequest.delete(imageID)).getJSON()));
	}

	/**
	 * <p>Lists available gold-master images</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 *
	 * @return the parsed ImageListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public ImageListResponse getImageList() throws ApiException {
		return(new ImageListResponse(execute(ImageRequest.list()).getJSON()));
	}

	/**
	 * <p>Lists available gold-master images</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 *
	 * @param pending  <em>(OPTIONAL)</em> Show images currently being created.
	 * @param imageID  <em>(OPTIONAL)</em> Request information for a specific gold-master image
	 *
	 * @return the parsed ImageListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public ImageListResponse getImageList(Long pending, Long imageID) throws ApiException {
		return(new ImageListResponse(execute(ImageRequest.list(pending, imageID)).getJSON()));
	}

	/**
	 * <p>Update an Image record.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param imageID <strong>(REQUIRED)</strong>   The ID of the Image to modify.
	 *
	 * @return the parsed ImageResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public ImageResponse getImageUpdate(Long imageID) throws ApiException {
		return(new ImageResponse(execute(ImageRequest.update(imageID)).getJSON()));
	}

	/**
	 * <p>Update an Image record.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param imageID <strong>(REQUIRED)</strong> The ID of the Image to modify.
	 * @param label  <em>(OPTIONAL)</em> The label of the Image.
	 * @param description  <em>(OPTIONAL)</em> An optional description of the Image.
	 *
	 * @return the parsed ImageResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public ImageResponse getImageUpdate(Long imageID, String label, String description) throws ApiException {
		return(new ImageResponse(execute(ImageRequest.update(imageID, label, description)).getJSON()));
	}

	/**
	 * <p>Returns a list of Linode data center facilities.</p> 
	 *
	 *
	 * @return the parsed AvailDatacentersResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public AvailDatacentersResponse getAvailDatacenters() throws ApiException {
		return(new AvailDatacentersResponse(execute(AvailRequest.datacenters()).getJSON()));
	}

	/**
	 * <p>Returns a list of available Linux Distributions.</p> 
	 *
	 *
	 * @return the parsed AvailDistributionsResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public AvailDistributionsResponse getAvailDistributions() throws ApiException {
		return(new AvailDistributionsResponse(execute(AvailRequest.distributions()).getJSON()));
	}

	/**
	 * <p>Returns a list of available Linux Distributions.</p> 
	 *
	 * @param distributionID  <em>(OPTIONAL)</em> Limits the results to the specified DistributionID
	 *
	 * @return the parsed AvailDistributionsResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public AvailDistributionsResponse getAvailDistributions(Long distributionID) throws ApiException {
		return(new AvailDistributionsResponse(execute(AvailRequest.distributions(distributionID)).getJSON()));
	}

	/**
	 * <p>List available kernels.</p> 
	 *
	 *
	 * @return the parsed AvailKernelsResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public AvailKernelsResponse getAvailKernels() throws ApiException {
		return(new AvailKernelsResponse(execute(AvailRequest.kernels()).getJSON()));
	}

	/**
	 * <p>List available kernels.</p> 
	 *
	 * @param isXen  <em>(OPTIONAL)</em> Show or hide Xen compatible kernels
	 * @param isKVM  <em>(OPTIONAL)</em> Show or hide KVM compatible kernels
	 *
	 * @return the parsed AvailKernelsResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public AvailKernelsResponse getAvailKernels(Boolean isXen, Boolean isKVM) throws ApiException {
		return(new AvailKernelsResponse(execute(AvailRequest.kernels(isXen, isKVM)).getJSON()));
	}

	/**
	 * <p>Returns a structure of Linode PlanIDs containing the Plan label and the availability in 
	 * each Datacenter.</p> 
	 *
	 *
	 * @return the parsed AvailLinodePlansResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public AvailLinodePlansResponse getAvailLinodePlans() throws ApiException {
		return(new AvailLinodePlansResponse(execute(AvailRequest.linodeplans()).getJSON()));
	}

	/**
	 * <p>Returns a structure of Linode PlanIDs containing the Plan label and the availability in 
	 * each Datacenter.</p> 
	 *
	 * @param planID  <em>(OPTIONAL)</em> Limits the list to the specified PlanID
	 *
	 * @return the parsed AvailLinodePlansResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public AvailLinodePlansResponse getAvailLinodePlans(Long planID) throws ApiException {
		return(new AvailLinodePlansResponse(execute(AvailRequest.linodeplans(planID)).getJSON()));
	}

	/**
	 * <p>Returns NodeBalancer pricing information.</p> 
	 *
	 *
	 * @return the parsed AvailNodeBalancersResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public AvailNodeBalancersResponse getAvailNodeBalancers() throws ApiException {
		return(new AvailNodeBalancersResponse(execute(AvailRequest.nodebalancers()).getJSON()));
	}

	/**
	 * <p>Returns a list of available public StackScripts.</p> 
	 *
	 *
	 * @return the parsed AvailStackscriptsResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public AvailStackscriptsResponse getAvailStackscripts() throws ApiException {
		return(new AvailStackscriptsResponse(execute(AvailRequest.stackscripts()).getJSON()));
	}

	/**
	 * <p>Returns a list of available public StackScripts.</p> 
	 *
	 * @param distributionID  <em>(OPTIONAL)</em> Limit the results to StackScripts that can be applied to this DistributionID
	 * @param distributionVendor  <em>(OPTIONAL)</em> Debian, Ubuntu, Fedora, etc.
	 * @param keywords  <em>(OPTIONAL)</em> Search terms
	 *
	 * @return the parsed AvailStackscriptsResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public AvailStackscriptsResponse getAvailStackscripts(Long distributionID, String distributionVendor, String keywords) throws ApiException {
		return(new AvailStackscriptsResponse(execute(AvailRequest.stackscripts(distributionID, distributionVendor, keywords)).getJSON()));
	}

	/**
	 * <p>Echos back parameters that were passed in.</p> 
	 *
	 * @param parameters the map of parameters (String:String) to echo back
	 *
	 * @return the parsed TestEchoResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public TestEchoResponse getTestEcho(Map<String, String> parameters) throws ApiException {
		return(new TestEchoResponse(execute(TestRequest.echo(parameters)).getJSON()));
	}

	/**
	 * <p>Create a StackScript.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param label <strong>(REQUIRED)</strong>   The Label for this StackScript
	 * @param distributionIDList <strong>(REQUIRED)</strong>   Comma delimited list of DistributionIDs that this script works on
	 * @param script <strong>(REQUIRED)</strong>   The actual script
	 *
	 * @return the parsed StackscriptResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public StackscriptResponse getStackscriptCreate(String label, String distributionIDList, String script) throws ApiException {
		return(new StackscriptResponse(execute(StackscriptRequest.create(label, distributionIDList, script)).getJSON()));
	}

	/**
	 * <p>Create a StackScript.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param label <strong>(REQUIRED)</strong> The Label for this StackScript
	 * @param description  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param distributionIDList <strong>(REQUIRED)</strong> Comma delimited list of DistributionIDs that this script works on
	 * @param isPublic  <em>(OPTIONAL)</em> Whether this StackScript is published in the Library, for everyone to use
	 * @param rev_note  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param script <strong>(REQUIRED)</strong> The actual script
	 *
	 * @return the parsed StackscriptResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public StackscriptResponse getStackscriptCreate(String label, String description, String distributionIDList, Boolean isPublic, String rev_note, String script) throws ApiException {
		return(new StackscriptResponse(execute(StackscriptRequest.create(label, description, distributionIDList, isPublic, rev_note, script)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param stackScriptID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed StackscriptResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public StackscriptResponse getStackscriptDelete(Long stackScriptID) throws ApiException {
		return(new StackscriptResponse(execute(StackscriptRequest.delete(stackScriptID)).getJSON()));
	}

	/**
	 * <p>Lists StackScripts you have access to.</p> 
	 *
	 *
	 * @return the parsed StackscriptListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public StackscriptListResponse getStackscriptList() throws ApiException {
		return(new StackscriptListResponse(execute(StackscriptRequest.list()).getJSON()));
	}

	/**
	 * <p>Lists StackScripts you have access to.</p> 
	 *
	 * @param stackScriptID  <em>(OPTIONAL)</em> Limits the list to the specified StackScriptID
	 *
	 * @return the parsed StackscriptListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public StackscriptListResponse getStackscriptList(Long stackScriptID) throws ApiException {
		return(new StackscriptListResponse(execute(StackscriptRequest.list(stackScriptID)).getJSON()));
	}

	/**
	 * <p>Update a StackScript.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param stackScriptID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed StackscriptResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public StackscriptResponse getStackscriptUpdate(Long stackScriptID) throws ApiException {
		return(new StackscriptResponse(execute(StackscriptRequest.update(stackScriptID)).getJSON()));
	}

	/**
	 * <p>Update a StackScript.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param stackScriptID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param label  <em>(OPTIONAL)</em> The Label for this StackScript
	 * @param description  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param distributionIDList  <em>(OPTIONAL)</em> Comma delimited list of DistributionIDs that this script works on
	 * @param isPublic  <em>(OPTIONAL)</em> Whether this StackScript is published in the Library, for everyone to use
	 * @param rev_note  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param script  <em>(OPTIONAL)</em> The actual script
	 *
	 * @return the parsed StackscriptResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public StackscriptResponse getStackscriptUpdate(Long stackScriptID, String label, String description, String distributionIDList, Boolean isPublic, String rev_note, String script) throws ApiException {
		return(new StackscriptResponse(execute(StackscriptRequest.update(stackScriptID, label, description, distributionIDList, isPublic, rev_note, script)).getJSON()));
	}

	/**
	 * <p>Create a domain record.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param domain <strong>(REQUIRED)</strong>   The zone's name
	 * @param type <strong>(REQUIRED)</strong>   master or slave
	 *
	 * @return the parsed DomainResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public DomainResponse getDomainCreate(String domain, String type) throws ApiException {
		return(new DomainResponse(execute(DomainRequest.create(domain, type)).getJSON()));
	}

	/**
	 * <p>Create a domain record.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param domain <strong>(REQUIRED)</strong> The zone's name
	 * @param description  <em>(OPTIONAL)</em> Currently undisplayed.
	 * @param type <strong>(REQUIRED)</strong> master or slave
	 * @param SOA_Email  <em>(OPTIONAL)</em> Required when type=master
	 * @param Refresh_sec  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param Retry_sec  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param Expire_sec  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param TTL_sec  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param lpm_displayGroup  <em>(OPTIONAL)</em> Display group in the Domain list inside the Linode DNS Manager
	 * @param status  <em>(OPTIONAL)</em> 0, 1, or 2 (disabled, active, edit mode)
	 * @param master_ips  <em>(OPTIONAL)</em> When type=slave, the zone's master DNS servers list, semicolon separated
	 * @param axfr_ips  <em>(OPTIONAL)</em> IP addresses allowed to AXFR the entire zone, semicolon separated
	 *
	 * @return the parsed DomainResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public DomainResponse getDomainCreate(String domain, String description, String type, String SOA_Email, Long Refresh_sec, Long Retry_sec, Long Expire_sec, Long TTL_sec, String lpm_displayGroup, Long status, String master_ips, String axfr_ips) throws ApiException {
		return(new DomainResponse(execute(DomainRequest.create(domain, description, type, SOA_Email, Refresh_sec, Retry_sec, Expire_sec, TTL_sec, lpm_displayGroup, status, master_ips, axfr_ips)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param domainID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed DomainResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public DomainResponse getDomainDelete(Long domainID) throws ApiException {
		return(new DomainResponse(execute(DomainRequest.delete(domainID)).getJSON()));
	}

	/**
	 * <p>Lists domains you have access to.</p> 
	 *
	 *
	 * @return the parsed DomainListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public DomainListResponse getDomainList() throws ApiException {
		return(new DomainListResponse(execute(DomainRequest.list()).getJSON()));
	}

	/**
	 * <p>Lists domains you have access to.</p> 
	 *
	 * @param domainID  <em>(OPTIONAL)</em> Limits the list to the specified DomainID
	 *
	 * @return the parsed DomainListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public DomainListResponse getDomainList(Long domainID) throws ApiException {
		return(new DomainListResponse(execute(DomainRequest.list(domainID)).getJSON()));
	}

	/**
	 * <p>Create a domain record.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param domainID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param type <strong>(REQUIRED)</strong>   One of: NS, MX, A, AAAA, CNAME, TXT, or SRV
	 *
	 * @return the parsed DomainResourceResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public DomainResourceResponse getDomainResourceCreate(Long domainID, String type) throws ApiException {
		return(new DomainResourceResponse(execute(DomainRequest.resourcecreate(domainID, type)).getJSON()));
	}

	/**
	 * <p>Create a domain record.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param domainID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param type <strong>(REQUIRED)</strong> One of: NS, MX, A, AAAA, CNAME, TXT, or SRV
	 * @param name  <em>(OPTIONAL)</em> The hostname or FQDN. When Type=MX the subdomain to delegate to the Target MX server.
	 * @param target  <em>(OPTIONAL)</em> When Type=MX the hostname. When Type=CNAME the target of the alias. When Type=TXT the value of the record. When Type=A or AAAA the token of '[remote_addr]' will be substituted with the IP address of the request.
	 * @param priority  <em>(OPTIONAL)</em> Priority for MX and SRV records, 0-255
	 * @param weight  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param port  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param protocol  <em>(OPTIONAL)</em> The protocol to append to an SRV record. Ignored on other record types.
	 * @param TTL_sec  <em>(OPTIONAL)</em> TTL. Leave as 0 to accept our default.
	 *
	 * @return the parsed DomainResourceResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public DomainResourceResponse getDomainResourceCreate(Long domainID, String type, String name, String target, Long priority, Long weight, Long port, String protocol, Long TTL_sec) throws ApiException {
		return(new DomainResourceResponse(execute(DomainRequest.resourcecreate(domainID, type, name, target, priority, weight, port, protocol, TTL_sec)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param domainID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param resourceID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed DomainResourceResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public DomainResourceResponse getDomainResourceDelete(Long domainID, Long resourceID) throws ApiException {
		return(new DomainResourceResponse(execute(DomainRequest.resourcedelete(domainID, resourceID)).getJSON()));
	}

	/**
	 *
	 * @param domainID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed DomainResourceListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public DomainResourceListResponse getDomainResourceList(Long domainID) throws ApiException {
		return(new DomainResourceListResponse(execute(DomainRequest.resourcelist(domainID)).getJSON()));
	}

	/**
	 *
	 * @param domainID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param resourceID  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed DomainResourceListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public DomainResourceListResponse getDomainResourceList(Long domainID, Long resourceID) throws ApiException {
		return(new DomainResourceListResponse(execute(DomainRequest.resourcelist(domainID, resourceID)).getJSON()));
	}

	/**
	 * <p>Update a domain record.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param resourceID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed DomainResourceResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public DomainResourceResponse getDomainResourceUpdate(Long resourceID) throws ApiException {
		return(new DomainResourceResponse(execute(DomainRequest.resourceupdate(resourceID)).getJSON()));
	}

	/**
	 * <p>Update a domain record.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param domainID  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param resourceID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param name  <em>(OPTIONAL)</em> The hostname or FQDN. When Type=MX the subdomain to delegate to the Target MX server.
	 * @param target  <em>(OPTIONAL)</em> When Type=MX the hostname. When Type=CNAME the target of the alias. When Type=TXT the value of the record. When Type=A or AAAA the token of '[remote_addr]' will be substituted with the IP address of the request.
	 * @param priority  <em>(OPTIONAL)</em> Priority for MX and SRV records, 0-255
	 * @param weight  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param port  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param protocol  <em>(OPTIONAL)</em> The protocol to append to an SRV record. Ignored on other record types.
	 * @param TTL_sec  <em>(OPTIONAL)</em> TTL. Leave as 0 to accept our default.
	 *
	 * @return the parsed DomainResourceResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public DomainResourceResponse getDomainResourceUpdate(Long domainID, Long resourceID, String name, String target, Long priority, Long weight, Long port, String protocol, Long TTL_sec) throws ApiException {
		return(new DomainResourceResponse(execute(DomainRequest.resourceupdate(domainID, resourceID, name, target, priority, weight, port, protocol, TTL_sec)).getJSON()));
	}

	/**
	 * <p>Update a domain record.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param domainID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed DomainResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public DomainResponse getDomainUpdate(Long domainID) throws ApiException {
		return(new DomainResponse(execute(DomainRequest.update(domainID)).getJSON()));
	}

	/**
	 * <p>Update a domain record.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param domainID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param domain  <em>(OPTIONAL)</em> The zone's name
	 * @param description  <em>(OPTIONAL)</em> Currently undisplayed.
	 * @param type  <em>(OPTIONAL)</em> master or slave
	 * @param SOA_Email  <em>(OPTIONAL)</em> Required when type=master
	 * @param Refresh_sec  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param Retry_sec  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param Expire_sec  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param TTL_sec  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param lpm_displayGroup  <em>(OPTIONAL)</em> Display group in the Domain list inside the Linode DNS Manager
	 * @param status  <em>(OPTIONAL)</em> 0, 1, or 2 (disabled, active, edit mode)
	 * @param master_ips  <em>(OPTIONAL)</em> When type=slave, the zone's master DNS servers list, semicolon separated
	 * @param axfr_ips  <em>(OPTIONAL)</em> IP addresses allowed to AXFR the entire zone, semicolon separated
	 *
	 * @return the parsed DomainResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public DomainResponse getDomainUpdate(Long domainID, String domain, String description, String type, String SOA_Email, Long Refresh_sec, Long Retry_sec, Long Expire_sec, Long TTL_sec, String lpm_displayGroup, Long status, String master_ips, String axfr_ips) throws ApiException {
		return(new DomainResponse(execute(DomainRequest.update(domainID, domain, description, type, SOA_Email, Refresh_sec, Retry_sec, Expire_sec, TTL_sec, lpm_displayGroup, status, master_ips, axfr_ips)).getJSON()));
	}

	/**
	 * <p>Issues a boot job for the provided ConfigID. If no ConfigID is provided boots the last used 
	 * configuration profile, or the first configuration profile if this Linode has never been booted.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeJobResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeJobResponse getLinodeBoot(Long linodeID) throws ApiException {
		return(new LinodeJobResponse(execute(LinodeRequest.boot(linodeID)).getJSON()));
	}

	/**
	 * <p>Issues a boot job for the provided ConfigID. If no ConfigID is provided boots the last used 
	 * configuration profile, or the first configuration profile if this Linode has never been booted.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param configID  <em>(OPTIONAL)</em> The ConfigID to boot, available from linode.config.list().
	 *
	 * @return the parsed LinodeJobResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeJobResponse getLinodeBoot(Long linodeID, Long configID) throws ApiException {
		return(new LinodeJobResponse(execute(LinodeRequest.boot(linodeID, configID)).getJSON()));
	}

	/**
	 * <p>Creates a new Linode, assigns you full privileges, and then clones the specified LinodeID 
	 * to the new Linode. There is a limit of 5 active clone operations per source Linode. It is 
	 * recommended that the source Linode be powered down during the clone.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>NOTFOUND</li>
	 *   <li>CCFAILED</li>
	 *   <li>VALIDATION</li>
	 *   <li>LINODELIMITER</li>
	 *   <li>ACCOUNTLIMIT</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong>   The LinodeID that you want cloned
	 * @param datacenterID <strong>(REQUIRED)</strong>   The DatacenterID from avail.datacenters() where you wish to place this new Linode
	 * @param planID <strong>(REQUIRED)</strong>   The desired PlanID available from avail.LinodePlans()
	 *
	 * @return the parsed LinodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeResponse getLinodeClone(Long linodeID, Long datacenterID, Long planID) throws ApiException {
		return(new LinodeResponse(execute(LinodeRequest.clone(linodeID, datacenterID, planID)).getJSON()));
	}

	/**
	 * <p>Creates a new Linode, assigns you full privileges, and then clones the specified LinodeID 
	 * to the new Linode. There is a limit of 5 active clone operations per source Linode. It is 
	 * recommended that the source Linode be powered down during the clone.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>NOTFOUND</li>
	 *   <li>CCFAILED</li>
	 *   <li>VALIDATION</li>
	 *   <li>LINODELIMITER</li>
	 *   <li>ACCOUNTLIMIT</li>
	 * </ul>
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> The LinodeID that you want cloned
	 * @param datacenterID <strong>(REQUIRED)</strong> The DatacenterID from avail.datacenters() where you wish to place this new Linode
	 * @param planID <strong>(REQUIRED)</strong> The desired PlanID available from avail.LinodePlans()
	 * @param paymentTerm  <em>(OPTIONAL)</em> Subscription term in months for prepaid customers. One of: 1, 12, or 24
	 *
	 * @return the parsed LinodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeResponse getLinodeClone(Long linodeID, Long datacenterID, Long planID, Long paymentTerm) throws ApiException {
		return(new LinodeResponse(execute(LinodeRequest.clone(linodeID, datacenterID, planID, paymentTerm)).getJSON()));
	}

	/**
	 * <p>Creates a Linode Configuration Profile.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param kernelID <strong>(REQUIRED)</strong>   The KernelID for this profile. Found in avail.kernels()
	 * @param label <strong>(REQUIRED)</strong>   The Label for this profile
	 * @param diskList <strong>(REQUIRED)</strong>   A comma delimited list of DiskIDs; position reflects device node. The 9th element for specifying the initrd.
	 *
	 * @return the parsed LinodeConfigResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeConfigResponse getLinodeConfigCreate(Long linodeID, Long kernelID, String label, String diskList) throws ApiException {
		return(new LinodeConfigResponse(execute(LinodeRequest.configcreate(linodeID, kernelID, label, diskList)).getJSON()));
	}

	/**
	 * <p>Creates a Linode Configuration Profile.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param kernelID <strong>(REQUIRED)</strong> The KernelID for this profile. Found in avail.kernels()
	 * @param label <strong>(REQUIRED)</strong> The Label for this profile
	 * @param comments  <em>(OPTIONAL)</em> Comments you wish to save along with this profile
	 * @param rAMLimit  <em>(OPTIONAL)</em> RAMLimit in MB. 0 for max.
	 * @param diskList <strong>(REQUIRED)</strong> A comma delimited list of DiskIDs; position reflects device node. The 9th element for specifying the initrd.
	 * @param virt_mode  <em>(OPTIONAL)</em> Controls the virtualization mode. One of 'paravirt', 'fullvirt'
	 * @param runLevel  <em>(OPTIONAL)</em> One of 'default', 'single', 'binbash'
	 * @param rootDeviceNum  <em>(OPTIONAL)</em> Which device number (1-8) that contains the root partition. 0 to utilize RootDeviceCustom.
	 * @param rootDeviceCustom  <em>(OPTIONAL)</em> A custom root device setting.
	 * @param rootDeviceRO  <em>(OPTIONAL)</em> Enables the 'ro' kernel flag. Modern distros want this.
	 * @param helper_disableUpdateDB  <em>(OPTIONAL)</em> Enable the disableUpdateDB filesystem helper
	 * @param helper_distro  <em>(OPTIONAL)</em> Enable the Distro filesystem helper. Corrects fstab and inittab/upstart entries depending on the kernel you're booting. You want this.
	 * @param helper_xen  <em>(OPTIONAL)</em> Deprecated - use helper_distro.
	 * @param helper_depmod  <em>(OPTIONAL)</em> Creates an empty modprobe file for the kernel you're booting.
	 * @param helper_network  <em>(OPTIONAL)</em> Automatically creates network configuration files for your distro and places them into your filesystem.
	 * @param devtmpfs_automount  <em>(OPTIONAL)</em> Controls if pv_ops kernels should automount devtmpfs at boot.
	 *
	 * @return the parsed LinodeConfigResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeConfigResponse getLinodeConfigCreate(Long linodeID, Long kernelID, String label, String comments, Long rAMLimit, String diskList, String virt_mode, String runLevel, Long rootDeviceNum, String rootDeviceCustom, Boolean rootDeviceRO, Boolean helper_disableUpdateDB, Boolean helper_distro, Boolean helper_xen, Boolean helper_depmod, Boolean helper_network, Boolean devtmpfs_automount) throws ApiException {
		return(new LinodeConfigResponse(execute(LinodeRequest.configcreate(linodeID, kernelID, label, comments, rAMLimit, diskList, virt_mode, runLevel, rootDeviceNum, rootDeviceCustom, rootDeviceRO, helper_disableUpdateDB, helper_distro, helper_xen, helper_depmod, helper_network, devtmpfs_automount)).getJSON()));
	}

	/**
	 * <p>Deletes a Linode Configuration Profile.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param configID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeConfigResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeConfigResponse getLinodeConfigDelete(Long linodeID, Long configID) throws ApiException {
		return(new LinodeConfigResponse(execute(LinodeRequest.configdelete(linodeID, configID)).getJSON()));
	}

	/**
	 * <p>Lists a Linode's Configuration Profiles.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeConfigListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeConfigListResponse getLinodeConfigList(Long linodeID) throws ApiException {
		return(new LinodeConfigListResponse(execute(LinodeRequest.configlist(linodeID)).getJSON()));
	}

	/**
	 * <p>Lists a Linode's Configuration Profiles.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param configID  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeConfigListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeConfigListResponse getLinodeConfigList(Long linodeID, Long configID) throws ApiException {
		return(new LinodeConfigListResponse(execute(LinodeRequest.configlist(linodeID, configID)).getJSON()));
	}

	/**
	 * <p>Updates a Linode Configuration Profile.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param configID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeConfigResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeConfigResponse getLinodeConfigUpdate(Long configID) throws ApiException {
		return(new LinodeConfigResponse(execute(LinodeRequest.configupdate(configID)).getJSON()));
	}

	/**
	 * <p>Updates a Linode Configuration Profile.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param linodeID  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param configID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param kernelID  <em>(OPTIONAL)</em> The KernelID for this profile. Found in avail.kernels()
	 * @param label  <em>(OPTIONAL)</em> The Label for this profile
	 * @param comments  <em>(OPTIONAL)</em> Comments you wish to save along with this profile
	 * @param rAMLimit  <em>(OPTIONAL)</em> RAMLimit in MB. 0 for max.
	 * @param diskList  <em>(OPTIONAL)</em> A comma delimited list of DiskIDs; position reflects device node. The 9th element for specifying the initrd.
	 * @param virt_mode  <em>(OPTIONAL)</em> Controls the virtualization mode. One of 'paravirt', 'fullvirt'
	 * @param runLevel  <em>(OPTIONAL)</em> One of 'default', 'single', 'binbash'
	 * @param rootDeviceNum  <em>(OPTIONAL)</em> Which device number (1-8) that contains the root partition. 0 to utilize RootDeviceCustom.
	 * @param rootDeviceCustom  <em>(OPTIONAL)</em> A custom root device setting.
	 * @param rootDeviceRO  <em>(OPTIONAL)</em> Enables the 'ro' kernel flag. Modern distros want this.
	 * @param helper_disableUpdateDB  <em>(OPTIONAL)</em> Enable the disableUpdateDB filesystem helper
	 * @param helper_distro  <em>(OPTIONAL)</em> Enable the Distro filesystem helper. Corrects fstab and inittab/upstart entries depending on the kernel you're booting. You want this.
	 * @param helper_xen  <em>(OPTIONAL)</em> Deprecated - use helper_distro.
	 * @param helper_depmod  <em>(OPTIONAL)</em> Creates an empty modprobe file for the kernel you're booting.
	 * @param helper_network  <em>(OPTIONAL)</em> Automatically creates network configuration files for your distro and places them into your filesystem.
	 * @param devtmpfs_automount  <em>(OPTIONAL)</em> Controls if pv_ops kernels should automount devtmpfs at boot.
	 *
	 * @return the parsed LinodeConfigResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeConfigResponse getLinodeConfigUpdate(Long linodeID, Long configID, Long kernelID, String label, String comments, Long rAMLimit, String diskList, String virt_mode, String runLevel, Long rootDeviceNum, String rootDeviceCustom, Boolean rootDeviceRO, Boolean helper_disableUpdateDB, Boolean helper_distro, Boolean helper_xen, Boolean helper_depmod, Boolean helper_network, Boolean devtmpfs_automount) throws ApiException {
		return(new LinodeConfigResponse(execute(LinodeRequest.configupdate(linodeID, configID, kernelID, label, comments, rAMLimit, diskList, virt_mode, runLevel, rootDeviceNum, rootDeviceCustom, rootDeviceRO, helper_disableUpdateDB, helper_distro, helper_xen, helper_depmod, helper_network, devtmpfs_automount)).getJSON()));
	}

	/**
	 * <p>Creates a Linode and assigns you full privileges. There is a 75-linodes-per-hour 
	 * limiter.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>CCFAILED</li>
	 *   <li>VALIDATION</li>
	 *   <li>LINODELIMITER</li>
	 *   <li>ACCOUNTLIMIT</li>
	 * </ul>
	 * @param datacenterID <strong>(REQUIRED)</strong>   The DatacenterID from avail.datacenters() where you wish to place this new Linode
	 * @param planID <strong>(REQUIRED)</strong>   The desired PlanID available from avail.LinodePlans()
	 *
	 * @return the parsed LinodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeResponse getLinodeCreate(Long datacenterID, Long planID) throws ApiException {
		return(new LinodeResponse(execute(LinodeRequest.create(datacenterID, planID)).getJSON()));
	}

	/**
	 * <p>Creates a Linode and assigns you full privileges. There is a 75-linodes-per-hour 
	 * limiter.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>CCFAILED</li>
	 *   <li>VALIDATION</li>
	 *   <li>LINODELIMITER</li>
	 *   <li>ACCOUNTLIMIT</li>
	 * </ul>
	 *
	 * @param datacenterID <strong>(REQUIRED)</strong> The DatacenterID from avail.datacenters() where you wish to place this new Linode
	 * @param planID <strong>(REQUIRED)</strong> The desired PlanID available from avail.LinodePlans()
	 * @param paymentTerm  <em>(OPTIONAL)</em> Subscription term in months for prepaid customers. One of: 1, 12, or 24
	 *
	 * @return the parsed LinodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeResponse getLinodeCreate(Long datacenterID, Long planID, Long paymentTerm) throws ApiException {
		return(new LinodeResponse(execute(LinodeRequest.create(datacenterID, planID, paymentTerm)).getJSON()));
	}

	/**
	 * <p>Immediately removes a Linode from your account and issues a pro-rated credit back to your 
	 * account, if applicable. To prevent accidental deletes, this requires the Linode has no Disk images. You 
	 * must first delete its disk images."</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>LINODENOTEMPTY</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong>   The LinodeID to delete
	 *
	 * @return the parsed LinodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeResponse getLinodeDelete(Long linodeID) throws ApiException {
		return(new LinodeResponse(execute(LinodeRequest.delete(linodeID)).getJSON()));
	}

	/**
	 * <p>Immediately removes a Linode from your account and issues a pro-rated credit back to your 
	 * account, if applicable. To prevent accidental deletes, this requires the Linode has no Disk images. You 
	 * must first delete its disk images."</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>LINODENOTEMPTY</li>
	 * </ul>
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> The LinodeID to delete
	 * @param skipChecks  <em>(OPTIONAL)</em> Skips the safety checks and will always delete the Linode
	 *
	 * @return the parsed LinodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeResponse getLinodeDelete(Long linodeID, Boolean skipChecks) throws ApiException {
		return(new LinodeResponse(execute(LinodeRequest.delete(linodeID, skipChecks)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param label <strong>(REQUIRED)</strong>   The display label for this Disk
	 * @param type <strong>(REQUIRED)</strong>   The formatted type of this disk. Valid types are: ext3, ext4, swap, raw
	 * @param size <strong>(REQUIRED)</strong>   The size in MB of this Disk.
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskCreate(Long linodeID, String label, String type, Long size) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskcreate(linodeID, label, type, size)).getJSON()));
	}

	/**
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param fromDistributionID  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param rootPass  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param rootSSHKey  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param label <strong>(REQUIRED)</strong> The display label for this Disk
	 * @param type <strong>(REQUIRED)</strong> The formatted type of this disk. Valid types are: ext3, ext4, swap, raw
	 * @param isReadOnly  <em>(OPTIONAL)</em> Enable forced read-only for this Disk
	 * @param size <strong>(REQUIRED)</strong> The size in MB of this Disk.
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskCreate(Long linodeID, Long fromDistributionID, String rootPass, String rootSSHKey, String label, String type, Boolean isReadOnly, Long size) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskcreate(linodeID, fromDistributionID, rootPass, rootSSHKey, label, type, isReadOnly, size)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param distributionID <strong>(REQUIRED)</strong>   The DistributionID to create this disk from. Found in avail.distributions()
	 * @param label <strong>(REQUIRED)</strong>   The label of this new disk image
	 * @param size <strong>(REQUIRED)</strong>   Size of this disk image in MB
	 * @param rootPass <strong>(REQUIRED)</strong>   The root user's password
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskCreateFromDistribution(Long linodeID, Long distributionID, String label, Long size, String rootPass) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskcreatefromdistribution(linodeID, distributionID, label, size, rootPass)).getJSON()));
	}

	/**
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param distributionID <strong>(REQUIRED)</strong> The DistributionID to create this disk from. Found in avail.distributions()
	 * @param label <strong>(REQUIRED)</strong> The label of this new disk image
	 * @param size <strong>(REQUIRED)</strong> Size of this disk image in MB
	 * @param rootPass <strong>(REQUIRED)</strong> The root user's password
	 * @param rootSSHKey  <em>(OPTIONAL)</em> Optionally sets this string into /root/.ssh/authorized_keys upon distribution configuration.
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskCreateFromDistribution(Long linodeID, Long distributionID, String label, Long size, String rootPass, String rootSSHKey) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskcreatefromdistribution(linodeID, distributionID, label, size, rootPass, rootSSHKey)).getJSON()));
	}

	/**
	 * <p>Creates a new disk from a previously imagized disk.</p> 
	 *
	 * @param imageID <strong>(REQUIRED)</strong>   The ID of the frozen image to deploy from
	 * @param linodeID <strong>(REQUIRED)</strong>   Specifies the Linode to deploy on to
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskCreateFromImage(Long imageID, Long linodeID) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskcreatefromimage(imageID, linodeID)).getJSON()));
	}

	/**
	 * <p>Creates a new disk from a previously imagized disk.</p> 
	 *
	 * @param imageID <strong>(REQUIRED)</strong> The ID of the frozen image to deploy from
	 * @param linodeID <strong>(REQUIRED)</strong> Specifies the Linode to deploy on to
	 * @param label  <em>(OPTIONAL)</em> The label of this new disk image
	 * @param size  <em>(OPTIONAL)</em> The size of the disk image to creates. Defaults to the minimum size required for the requested image
	 * @param rootPass  <em>(OPTIONAL)</em> Optionally sets the root password at deployment time. If a password is not provided the existing root password of the frozen image will not be modified
	 * @param rootSSHKey  <em>(OPTIONAL)</em> Optionally sets this string into /root/.ssh/authorized_keys upon image deployment
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskCreateFromImage(Long imageID, Long linodeID, String label, Long size, String rootPass, String rootSSHKey) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskcreatefromimage(imageID, linodeID, label, size, rootPass, rootSSHKey)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param stackScriptID <strong>(REQUIRED)</strong>   The StackScript to create this image from
	 * @param stackScriptUDFResponses <strong>(REQUIRED)</strong>   JSON encoded name/value pairs, answering this StackScript's User Defined Fields
	 * @param distributionID <strong>(REQUIRED)</strong>   Which Distribution to apply this StackScript to. Must be one from the script's DistributionIDList
	 * @param label <strong>(REQUIRED)</strong>   The label of this new disk image
	 * @param size <strong>(REQUIRED)</strong>   Size of this disk image in MB
	 * @param rootPass <strong>(REQUIRED)</strong>   The root user's password
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskCreateFromStackscript(Long linodeID, Long stackScriptID, String stackScriptUDFResponses, Long distributionID, String label, Long size, String rootPass) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskcreatefromstackscript(linodeID, stackScriptID, stackScriptUDFResponses, distributionID, label, size, rootPass)).getJSON()));
	}

	/**
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param stackScriptID <strong>(REQUIRED)</strong> The StackScript to create this image from
	 * @param stackScriptUDFResponses <strong>(REQUIRED)</strong> JSON encoded name/value pairs, answering this StackScript's User Defined Fields
	 * @param distributionID <strong>(REQUIRED)</strong> Which Distribution to apply this StackScript to. Must be one from the script's DistributionIDList
	 * @param label <strong>(REQUIRED)</strong> The label of this new disk image
	 * @param size <strong>(REQUIRED)</strong> Size of this disk image in MB
	 * @param rootPass <strong>(REQUIRED)</strong> The root user's password
	 * @param rootSSHKey  <em>(OPTIONAL)</em> Optionally sets this string into /root/.ssh/authorized_keys upon distribution configuration.
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskCreateFromStackscript(Long linodeID, Long stackScriptID, String stackScriptUDFResponses, Long distributionID, String label, Long size, String rootPass, String rootSSHKey) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskcreatefromstackscript(linodeID, stackScriptID, stackScriptUDFResponses, distributionID, label, size, rootPass, rootSSHKey)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param diskID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskDelete(Long linodeID, Long diskID) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskdelete(linodeID, diskID)).getJSON()));
	}

	/**
	 * <p>Performs a bit-for-bit copy of a disk image.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param diskID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskDuplicate(Long linodeID, Long diskID) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskduplicate(linodeID, diskID)).getJSON()));
	}

	/**
	 * <p>Creates a gold-master image for future deployments</p> 
	 *
	 * @param linodeID <strong>(REQUIRED)</strong>   Specifies the source Linode to create the image from
	 * @param diskID <strong>(REQUIRED)</strong>   Specifies the source Disk to create the image from
	 *
	 * @return the parsed LinodeImageResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeImageResponse getLinodeDiskImagize(Long linodeID, Long diskID) throws ApiException {
		return(new LinodeImageResponse(execute(LinodeRequest.diskimagize(linodeID, diskID)).getJSON()));
	}

	/**
	 * <p>Creates a gold-master image for future deployments</p> 
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> Specifies the source Linode to create the image from
	 * @param diskID <strong>(REQUIRED)</strong> Specifies the source Disk to create the image from
	 * @param description  <em>(OPTIONAL)</em> An optional description of the created image
	 * @param label  <em>(OPTIONAL)</em> Sets the name of the image shown in the base image list, defaults to the source image label
	 *
	 * @return the parsed LinodeImageResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeImageResponse getLinodeDiskImagize(Long linodeID, Long diskID, String description, String label) throws ApiException {
		return(new LinodeImageResponse(execute(LinodeRequest.diskimagize(linodeID, diskID, description, label)).getJSON()));
	}

	/**
	 * <p>Status values are 1: Ready and 2: Being Deleted.</p> 
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeDiskListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeDiskListResponse getLinodeDiskList(Long linodeID) throws ApiException {
		return(new LinodeDiskListResponse(execute(LinodeRequest.disklist(linodeID)).getJSON()));
	}

	/**
	 * <p>Status values are 1: Ready and 2: Being Deleted.</p> 
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param diskID  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeDiskListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeDiskListResponse getLinodeDiskList(Long linodeID, Long diskID) throws ApiException {
		return(new LinodeDiskListResponse(execute(LinodeRequest.disklist(linodeID, diskID)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param diskID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param size <strong>(REQUIRED)</strong>   The requested new size of this Disk in MB
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskResize(Long linodeID, Long diskID, Long size) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskresize(linodeID, diskID, size)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param diskID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskUpdate(Long diskID) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskupdate(diskID)).getJSON()));
	}

	/**
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param linodeID  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param diskID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param label  <em>(OPTIONAL)</em> The display label for this Disk
	 * @param isReadOnly  <em>(OPTIONAL)</em> Enable forced read-only for this Disk
	 *
	 * @return the parsed LinodeDiskResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeDiskResponse getLinodeDiskUpdate(Long linodeID, Long diskID, String label, Boolean isReadOnly) throws ApiException {
		return(new LinodeDiskResponse(execute(LinodeRequest.diskupdate(linodeID, diskID, label, isReadOnly)).getJSON()));
	}

	/**
	 * <p>Assigns a Private IP to a Linode. Returns the IPAddressID that was added.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeIpResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeIpResponse getLinodeIpAddressPrivate(Long linodeID) throws ApiException {
		return(new LinodeIpResponse(execute(LinodeRequest.ipaddprivate(linodeID)).getJSON()));
	}

	/**
	 * <p>Assigns a Public IP to a Linode. Returns the IPAddressID and IPAddress that was added.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong>   The LinodeID of the Linode that will be assigned an additional public IP address
	 *
	 * @return the parsed LinodeIpResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeIpResponse getLinodeIpAddressPublic(Long linodeID) throws ApiException {
		return(new LinodeIpResponse(execute(LinodeRequest.ipaddpublic(linodeID)).getJSON()));
	}

	/**
	 * <p>Returns the IP addresses of all Linodes you have access to.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 *
	 * @return the parsed LinodeIpListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeIpListResponse getLinodeIpList() throws ApiException {
		return(new LinodeIpListResponse(execute(LinodeRequest.iplist()).getJSON()));
	}

	/**
	 * <p>Returns the IP addresses of all Linodes you have access to.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 *
	 * @param linodeID  <em>(OPTIONAL)</em> If specified, limits the result to this LinodeID
	 * @param iPAddressID  <em>(OPTIONAL)</em> If specified, limits the result to this IPAddressID
	 *
	 * @return the parsed LinodeIpListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeIpListResponse getLinodeIpList(Long linodeID, Long iPAddressID) throws ApiException {
		return(new LinodeIpListResponse(execute(LinodeRequest.iplist(linodeID, iPAddressID)).getJSON()));
	}

	/**
	 * <p>Sets the rDNS name of a Public IP. Returns the IPAddressID and IPAddress that were 
	 * updated.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param iPAddressID <strong>(REQUIRED)</strong>   The IPAddressID of the address to update
	 * @param hostname <strong>(REQUIRED)</strong>   The hostname to set the reverse DNS to
	 *
	 * @return the parsed LinodeIpSetRdnsResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeIpSetRdnsResponse getLinodeIpSetRdns(Long iPAddressID, String hostname) throws ApiException {
		return(new LinodeIpSetRdnsResponse(execute(LinodeRequest.ipsetrdns(iPAddressID, hostname)).getJSON()));
	}

	/**
	 * <p>Exchanges Public IP addresses between two Linodes within a Datacenter. The destination of 
	 * the IP Address can be designated by either the toLinodeID or withIPAddressID parameter. Returns 
	 * the resulting relationship of the Linode and IP Address parameters. When performing a one 
	 * directional swap, the source is represented by the first of the two resultant array members.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param iPAddressID <strong>(REQUIRED)</strong>   The IPAddressID of an IP Address to transfer or swap
	 *
	 * @return the parsed LinodeIpSwapResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeIpSwapResponse getLinodeIpSwap(Long iPAddressID) throws ApiException {
		return(new LinodeIpSwapResponse(execute(LinodeRequest.ipswap(iPAddressID)).getJSON()));
	}

	/**
	 * <p>Exchanges Public IP addresses between two Linodes within a Datacenter. The destination of 
	 * the IP Address can be designated by either the toLinodeID or withIPAddressID parameter. Returns 
	 * the resulting relationship of the Linode and IP Address parameters. When performing a one 
	 * directional swap, the source is represented by the first of the two resultant array members.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param iPAddressID <strong>(REQUIRED)</strong> The IPAddressID of an IP Address to transfer or swap
	 * @param withIPAddressID  <em>(OPTIONAL)</em> The IP Address ID to swap
	 * @param toLinodeID  <em>(OPTIONAL)</em> The LinodeID of the Linode where IPAddressID will be transfered
	 *
	 * @return the parsed LinodeIpSwapResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeIpSwapResponse getLinodeIpSwap(Long iPAddressID, Long withIPAddressID, Long toLinodeID) throws ApiException {
		return(new LinodeIpSwapResponse(execute(LinodeRequest.ipswap(iPAddressID, withIPAddressID, toLinodeID)).getJSON()));
	}

	/**
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeJobListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeJobListResponse getLinodeJobList(Long linodeID) throws ApiException {
		return(new LinodeJobListResponse(execute(LinodeRequest.joblist(linodeID)).getJSON()));
	}

	/**
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param jobID  <em>(OPTIONAL)</em> Limits the list to the specified JobID
	 * @param pendingOnly  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeJobListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeJobListResponse getLinodeJobList(Long linodeID, Long jobID, Boolean pendingOnly) throws ApiException {
		return(new LinodeJobListResponse(execute(LinodeRequest.joblist(linodeID, jobID, pendingOnly)).getJSON()));
	}

	/**
	 * <p>Returns a list of all Linodes user has access or delete to, including some properties. 
	 * Status values are -1: Being Created, 0: Brand New, 1: Running, and 2: Powered Off.</p> 
	 *
	 *
	 * @return the parsed LinodeListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeListResponse getLinodeList() throws ApiException {
		return(new LinodeListResponse(execute(LinodeRequest.list()).getJSON()));
	}

	/**
	 * <p>Returns a list of all Linodes user has access or delete to, including some properties. 
	 * Status values are -1: Being Created, 0: Brand New, 1: Running, and 2: Powered Off.</p> 
	 *
	 * @param linodeID  <em>(OPTIONAL)</em> Limits the list to the specified LinodeID
	 *
	 * @return the parsed LinodeListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeListResponse getLinodeList(Long linodeID) throws ApiException {
		return(new LinodeListResponse(execute(LinodeRequest.list(linodeID)).getJSON()));
	}

	/**
	 * <p>Issues a shutdown, and then boot job for a given LinodeID.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeJobResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeJobResponse getLinodeReboot(Long linodeID) throws ApiException {
		return(new LinodeJobResponse(execute(LinodeRequest.reboot(linodeID)).getJSON()));
	}

	/**
	 * <p>Issues a shutdown, and then boot job for a given LinodeID.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param configID  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeJobResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeJobResponse getLinodeReboot(Long linodeID, Long configID) throws ApiException {
		return(new LinodeJobResponse(execute(LinodeRequest.reboot(linodeID, configID)).getJSON()));
	}

	/**
	 * <p>Resizes a Linode from one plan to another. Immediately shuts the Linode down, 
	 * charges/credits the account, and issue a migration to another host server.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>CCFAILED</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param planID <strong>(REQUIRED)</strong>   The desired PlanID available from avail.LinodePlans()
	 *
	 * @return the parsed LinodeResizeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeResizeResponse getLinodeResize(Long linodeID, Long planID) throws ApiException {
		return(new LinodeResizeResponse(execute(LinodeRequest.resize(linodeID, planID)).getJSON()));
	}

	/**
	 * <p>Issues a shutdown job for a given LinodeID.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeJobResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeJobResponse getLinodeShutdown(Long linodeID) throws ApiException {
		return(new LinodeJobResponse(execute(LinodeRequest.shutdown(linodeID)).getJSON()));
	}

	/**
	 * <p>Updates a Linode's properties.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public LinodeResponse getLinodeUpdate(Long linodeID) throws ApiException {
		return(new LinodeResponse(execute(LinodeRequest.update(linodeID)).getJSON()));
	}

	/**
	 * <p>Updates a Linode's properties.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param linodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param label  <em>(OPTIONAL)</em> This Linode's label
	 * @param lpm_displayGroup  <em>(OPTIONAL)</em> Display group in the Linode list inside the Linode Manager
	 * @param Alert_cpu_enabled  <em>(OPTIONAL)</em> Enable the cpu usage email alert
	 * @param Alert_cpu_threshold  <em>(OPTIONAL)</em> CPU Alert threshold, percentage 0-800
	 * @param Alert_diskio_enabled  <em>(OPTIONAL)</em> Enable the disk IO email alert
	 * @param Alert_diskio_threshold  <em>(OPTIONAL)</em> IO ops/sec
	 * @param Alert_bwin_enabled  <em>(OPTIONAL)</em> Enable the incoming bandwidth email alert
	 * @param Alert_bwin_threshold  <em>(OPTIONAL)</em> Mb/sec
	 * @param Alert_bwout_enabled  <em>(OPTIONAL)</em> Enable the outgoing bandwidth email alert
	 * @param Alert_bwout_threshold  <em>(OPTIONAL)</em> Mb/sec
	 * @param Alert_bwquota_enabled  <em>(OPTIONAL)</em> Enable the bw quote email alert
	 * @param Alert_bwquota_threshold  <em>(OPTIONAL)</em> Percentage of monthly bw quota
	 * @param backupWindow  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param backupWeeklyDay  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param watchdog  <em>(OPTIONAL)</em> Enable the Lassie shutdown watchdog
	 * @param ms_ssh_disabled  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param ms_ssh_user  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param ms_ssh_ip  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param ms_ssh_port  <em>(OPTIONAL)</em> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed LinodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public LinodeResponse getLinodeUpdate(Long linodeID, String label, String lpm_displayGroup, Boolean Alert_cpu_enabled, Long Alert_cpu_threshold, Boolean Alert_diskio_enabled, Long Alert_diskio_threshold, Boolean Alert_bwin_enabled, Long Alert_bwin_threshold, Boolean Alert_bwout_enabled, Long Alert_bwout_threshold, Boolean Alert_bwquota_enabled, Long Alert_bwquota_threshold, Long backupWindow, Long backupWeeklyDay, Boolean watchdog, Boolean ms_ssh_disabled, String ms_ssh_user, String ms_ssh_ip, Long ms_ssh_port) throws ApiException {
		return(new LinodeResponse(execute(LinodeRequest.update(linodeID, label, lpm_displayGroup, Alert_cpu_enabled, Alert_cpu_threshold, Alert_diskio_enabled, Alert_diskio_threshold, Alert_bwin_enabled, Alert_bwin_threshold, Alert_bwout_enabled, Alert_bwout_threshold, Alert_bwquota_enabled, Alert_bwquota_threshold, backupWindow, backupWeeklyDay, watchdog, ms_ssh_disabled, ms_ssh_user, ms_ssh_ip, ms_ssh_port)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param nodeBalancerID <strong>(REQUIRED)</strong>   The parent NodeBalancer's ID
	 *
	 * @return the parsed NodebalancerConfigResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerConfigResponse getNodebalancerConfigCreate(Long nodeBalancerID) throws ApiException {
		return(new NodebalancerConfigResponse(execute(NodebalancerRequest.configcreate(nodeBalancerID)).getJSON()));
	}

	/**
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param nodeBalancerID <strong>(REQUIRED)</strong> The parent NodeBalancer's ID
	 * @param port  <em>(OPTIONAL)</em> Port to bind to on the public interfaces. 1-65534
	 * @param protocol  <em>(OPTIONAL)</em> Either 'tcp', 'http', or 'https'
	 * @param algorithm  <em>(OPTIONAL)</em> Balancing algorithm. One of 'roundrobin', 'leastconn', 'source'
	 * @param stickiness  <em>(OPTIONAL)</em> Session persistence. One of 'none', 'table', 'http_cookie'
	 * @param check  <em>(OPTIONAL)</em> Perform active health checks on the backend nodes. One of 'connection', 'http', 'http_body'
	 * @param check_interval  <em>(OPTIONAL)</em> Seconds between health check probes. 2-3600
	 * @param check_timeout  <em>(OPTIONAL)</em> Seconds to wait before considering the probe a failure. 1-30. Must be less than check_interval.
	 * @param check_attempts  <em>(OPTIONAL)</em> Number of failed probes before taking a node out of rotation. 1-30
	 * @param check_path  <em>(OPTIONAL)</em> When check=http, the path to request
	 * @param check_body  <em>(OPTIONAL)</em> When check=http, a regex to match within the first 16,384 bytes of the response body
	 * @param check_passive  <em>(OPTIONAL)</em> Enable passive checks based on observing communication with back-end nodes.
	 * @param ssl_cert  <em>(OPTIONAL)</em> SSL certificate served by the NodeBalancer when the protocol is 'https'
	 * @param ssl_key  <em>(OPTIONAL)</em> Unpassphrased private key for the SSL certificate when protocol is 'https'
	 * @param cipher_suite  <em>(OPTIONAL)</em> SSL cipher suite to enforce. One of 'recommended', 'legacy'
	 *
	 * @return the parsed NodebalancerConfigResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public NodebalancerConfigResponse getNodebalancerConfigCreate(Long nodeBalancerID, Long port, String protocol, String algorithm, String stickiness, String check, Long check_interval, String check_timeout, String check_attempts, String check_path, String check_body, Boolean check_passive, String ssl_cert, String ssl_key, String cipher_suite) throws ApiException {
		return(new NodebalancerConfigResponse(execute(NodebalancerRequest.configcreate(nodeBalancerID, port, protocol, algorithm, stickiness, check, check_interval, check_timeout, check_attempts, check_path, check_body, check_passive, ssl_cert, ssl_key, cipher_suite)).getJSON()));
	}

	/**
	 * <p>Deletes a NodeBalancer's Config</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param nodeBalancerID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param configID <strong>(REQUIRED)</strong>   The ConfigID to delete
	 *
	 * @return the parsed NodebalancerConfigResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerConfigResponse getNodebalancerConfigDelete(Long nodeBalancerID, Long configID) throws ApiException {
		return(new NodebalancerConfigResponse(execute(NodebalancerRequest.configdelete(nodeBalancerID, configID)).getJSON()));
	}

	/**
	 * <p>Returns a list of NodeBalancers this user has access or delete to, including their 
	 * properties</p> 
	 *
	 * @param nodeBalancerID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed NodebalancerConfigListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerConfigListResponse getNodebalancerConfigList(Long nodeBalancerID) throws ApiException {
		return(new NodebalancerConfigListResponse(execute(NodebalancerRequest.configlist(nodeBalancerID)).getJSON()));
	}

	/**
	 * <p>Returns a list of NodeBalancers this user has access or delete to, including their 
	 * properties</p> 
	 *
	 * @param nodeBalancerID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param configID  <em>(OPTIONAL)</em> Limits the list to the specified ConfigID
	 *
	 * @return the parsed NodebalancerConfigListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public NodebalancerConfigListResponse getNodebalancerConfigList(Long nodeBalancerID, Long configID) throws ApiException {
		return(new NodebalancerConfigListResponse(execute(NodebalancerRequest.configlist(nodeBalancerID, configID)).getJSON()));
	}

	/**
	 * <p>Updates a Config's properties</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param configID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed NodebalancerConfigResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerConfigResponse getNodebalancerConfigUpdate(Long configID) throws ApiException {
		return(new NodebalancerConfigResponse(execute(NodebalancerRequest.configupdate(configID)).getJSON()));
	}

	/**
	 * <p>Updates a Config's properties</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param configID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param port  <em>(OPTIONAL)</em> Port to bind to on the public interfaces. 1-65534
	 * @param protocol  <em>(OPTIONAL)</em> Either 'tcp', 'http', or 'https'
	 * @param algorithm  <em>(OPTIONAL)</em> Balancing algorithm. One of 'roundrobin', 'leastconn', 'source'
	 * @param stickiness  <em>(OPTIONAL)</em> Session persistence. One of 'none', 'table', 'http_cookie'
	 * @param check  <em>(OPTIONAL)</em> Perform active health checks on the backend nodes. One of 'connection', 'http', 'http_body'
	 * @param check_interval  <em>(OPTIONAL)</em> Seconds between health check probes. 2-3600
	 * @param check_timeout  <em>(OPTIONAL)</em> Seconds to wait before considering the probe a failure. 1-30. Must be less than check_interval.
	 * @param check_attempts  <em>(OPTIONAL)</em> Number of failed probes before taking a node out of rotation. 1-30
	 * @param check_path  <em>(OPTIONAL)</em> When check=http, the path to request
	 * @param check_body  <em>(OPTIONAL)</em> When check=http, a regex to match within the first 16,384 bytes of the response body
	 * @param check_passive  <em>(OPTIONAL)</em> Enable passive checks based on observing communication with back-end nodes.
	 * @param ssl_cert  <em>(OPTIONAL)</em> SSL certificate served by the NodeBalancer when the protocol is 'https'
	 * @param ssl_key  <em>(OPTIONAL)</em> Unpassphrased private key for the SSL certificate when protocol is 'https'
	 * @param cipher_suite  <em>(OPTIONAL)</em> SSL cipher suite to enforce. One of 'recommended', 'legacy'
	 *
	 * @return the parsed NodebalancerConfigResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public NodebalancerConfigResponse getNodebalancerConfigUpdate(Long configID, Long port, String protocol, String algorithm, String stickiness, String check, Long check_interval, String check_timeout, String check_attempts, String check_path, String check_body, Boolean check_passive, String ssl_cert, String ssl_key, String cipher_suite) throws ApiException {
		return(new NodebalancerConfigResponse(execute(NodebalancerRequest.configupdate(configID, port, protocol, algorithm, stickiness, check, check_interval, check_timeout, check_attempts, check_path, check_body, check_passive, ssl_cert, ssl_key, cipher_suite)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>CCFAILED</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param datacenterID <strong>(REQUIRED)</strong>   The DatacenterID from avail.datacenters() where you wish to place this new NodeBalancer
	 *
	 * @return the parsed NodebalancerResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerResponse getNodebalancerCreate(Long datacenterID) throws ApiException {
		return(new NodebalancerResponse(execute(NodebalancerRequest.create(datacenterID)).getJSON()));
	}

	/**
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOACCESS</li>
	 *   <li>CCFAILED</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param datacenterID <strong>(REQUIRED)</strong> The DatacenterID from avail.datacenters() where you wish to place this new NodeBalancer
	 * @param label  <em>(OPTIONAL)</em> This NodeBalancer's label
	 * @param clientConnThrottle  <em>(OPTIONAL)</em> To help mitigate abuse, throttle connections per second, per client IP. 0 to disable. Max of 20.
	 *
	 * @return the parsed NodebalancerResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public NodebalancerResponse getNodebalancerCreate(Long datacenterID, String label, Long clientConnThrottle) throws ApiException {
		return(new NodebalancerResponse(execute(NodebalancerRequest.create(datacenterID, label, clientConnThrottle)).getJSON()));
	}

	/**
	 * <p>Immediately removes a NodeBalancer from your account and issues a pro-rated credit back to 
	 * your account, if applicable.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param nodeBalancerID <strong>(REQUIRED)</strong>   The NodeBalancerID to delete
	 *
	 * @return the parsed NodebalancerResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerResponse getNodebalancerDelete(Long nodeBalancerID) throws ApiException {
		return(new NodebalancerResponse(execute(NodebalancerRequest.delete(nodeBalancerID)).getJSON()));
	}

	/**
	 * <p>Returns a list of NodeBalancers this user has access or delete to, including their 
	 * properties</p> 
	 *
	 *
	 * @return the parsed NodebalancerListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerListResponse getNodebalancerList() throws ApiException {
		return(new NodebalancerListResponse(execute(NodebalancerRequest.list()).getJSON()));
	}

	/**
	 * <p>Returns a list of NodeBalancers this user has access or delete to, including their 
	 * properties</p> 
	 *
	 * @param nodeBalancerID  <em>(OPTIONAL)</em> Limits the list to the specified NodeBalancerID
	 *
	 * @return the parsed NodebalancerListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public NodebalancerListResponse getNodebalancerList(Long nodeBalancerID) throws ApiException {
		return(new NodebalancerListResponse(execute(NodebalancerRequest.list(nodeBalancerID)).getJSON()));
	}

	/**
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param configID <strong>(REQUIRED)</strong>   The parent ConfigID to attach this Node to
	 * @param label <strong>(REQUIRED)</strong>   This backend Node's label
	 * @param address <strong>(REQUIRED)</strong>   The address:port combination used to communicate with this Node
	 *
	 * @return the parsed NodebalancerNodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerNodeResponse getNodebalancerNodeCreate(Long configID, String label, String address) throws ApiException {
		return(new NodebalancerNodeResponse(execute(NodebalancerRequest.nodecreate(configID, label, address)).getJSON()));
	}

	/**
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param configID <strong>(REQUIRED)</strong> The parent ConfigID to attach this Node to
	 * @param label <strong>(REQUIRED)</strong> This backend Node's label
	 * @param address <strong>(REQUIRED)</strong> The address:port combination used to communicate with this Node
	 * @param weight  <em>(OPTIONAL)</em> Load balancing weight, 1-255. Higher means more connections.
	 * @param mode  <em>(OPTIONAL)</em> The connections mode for this node. One of 'accept', 'reject', or 'drain'
	 *
	 * @return the parsed NodebalancerNodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public NodebalancerNodeResponse getNodebalancerNodeCreate(Long configID, String label, String address, Long weight, String mode) throws ApiException {
		return(new NodebalancerNodeResponse(execute(NodebalancerRequest.nodecreate(configID, label, address, weight, mode)).getJSON()));
	}

	/**
	 * <p>Deletes a Node from a NodeBalancer Config</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 * </ul>
	 * @param nodeID <strong>(REQUIRED)</strong>   The NodeID to delete
	 *
	 * @return the parsed NodebalancerNodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerNodeResponse getNodebalancerNodeDelete(Long nodeID) throws ApiException {
		return(new NodebalancerNodeResponse(execute(NodebalancerRequest.nodedelete(nodeID)).getJSON()));
	}

	/**
	 * <p>Returns a list of Nodes associated with a NodeBalancer Config</p> 
	 *
	 * @param configID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed NodebalancerNodeListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerNodeListResponse getNodebalancerNodeList(Long configID) throws ApiException {
		return(new NodebalancerNodeListResponse(execute(NodebalancerRequest.nodelist(configID)).getJSON()));
	}

	/**
	 * <p>Returns a list of Nodes associated with a NodeBalancer Config</p> 
	 *
	 * @param configID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param nodeID  <em>(OPTIONAL)</em> Limits the list to the specified NodeID
	 *
	 * @return the parsed NodebalancerNodeListResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public NodebalancerNodeListResponse getNodebalancerNodeList(Long configID, Long nodeID) throws ApiException {
		return(new NodebalancerNodeListResponse(execute(NodebalancerRequest.nodelist(configID, nodeID)).getJSON()));
	}

	/**
	 * <p>Updates a Node's properties</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param nodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed NodebalancerNodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerNodeResponse getNodebalancerNodeUpdate(Long nodeID) throws ApiException {
		return(new NodebalancerNodeResponse(execute(NodebalancerRequest.nodeupdate(nodeID)).getJSON()));
	}

	/**
	 * <p>Updates a Node's properties</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param nodeID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param label  <em>(OPTIONAL)</em> This backend Node's label
	 * @param address  <em>(OPTIONAL)</em> The address:port combination used to communicate with this Node
	 * @param weight  <em>(OPTIONAL)</em> Load balancing weight, 1-255. Higher means more connections.
	 * @param mode  <em>(OPTIONAL)</em> The connections mode for this node. One of 'accept', 'reject', or 'drain'
	 *
	 * @return the parsed NodebalancerNodeResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public NodebalancerNodeResponse getNodebalancerNodeUpdate(Long nodeID, String label, String address, Long weight, String mode) throws ApiException {
		return(new NodebalancerNodeResponse(execute(NodebalancerRequest.nodeupdate(nodeID, label, address, weight, mode)).getJSON()));
	}

	/**
	 * <p>Updates a NodeBalancer's properties</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param nodeBalancerID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed NodebalancerResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public NodebalancerResponse getNodebalancerUpdate(Long nodeBalancerID) throws ApiException {
		return(new NodebalancerResponse(execute(NodebalancerRequest.update(nodeBalancerID)).getJSON()));
	}

	/**
	 * <p>Updates a NodeBalancer's properties</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>NOTFOUND</li>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param nodeBalancerID <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param label  <em>(OPTIONAL)</em> This NodeBalancer's label
	 * @param clientConnThrottle  <em>(OPTIONAL)</em> To help mitigate abuse, throttle connections per second, per client IP. 0 to disable. Max of 20.
	 *
	 * @return the parsed NodebalancerResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public NodebalancerResponse getNodebalancerUpdate(Long nodeBalancerID, String label, Long clientConnThrottle) throws ApiException {
		return(new NodebalancerResponse(execute(NodebalancerRequest.update(nodeBalancerID, label, clientConnThrottle)).getJSON()));
	}

	/**
	 * <p>Returns a data structure of the entire Linode API specification. This method does not 
	 * require authorization.<br><br>For example: <a target="_blank" 
	 * href="https://api.linode.com/?api_action=api.spec">https://api.linode.com/?api_action=api.spec</a></p> 
	 *
	 *
	 * @return the parsed ApiSpecResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public ApiSpecResponse getApiSpec() throws ApiException {
		return(new ApiSpecResponse(execute(ApiRequest.spec()).getJSON()));
	}

	/**
	 * <p>Authenticates a Linode Manager user against their username, password, and two-factor 
	 * token (when enabled), and then returns a new API key, which can be used until it expires. The number of 
	 * active keys is limited to 20.</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>AUTHFAIL</li>
	 *   <li>NEEDTOKEN</li>
	 *   <li>PASSWORDEXPIRED</li>
	 *   <li>KEYLIMIT</li>
	 * </ul>
	 * @param username <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param password <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 *
	 * @return the parsed UserGetApiKeyResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public UserGetApiKeyResponse getUserGetApiKey(String username, String password) throws ApiException {
		return(new UserGetApiKeyResponse(execute(UserRequest.getapikey(username, password)).getJSON()));
	}

	/**
	 * <p>Authenticates a Linode Manager user against their username, password, and two-factor 
	 * token (when enabled), and then returns a new API key, which can be used until it expires. The number of 
	 * active keys is limited to 20.</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>AUTHFAIL</li>
	 *   <li>NEEDTOKEN</li>
	 *   <li>PASSWORDEXPIRED</li>
	 *   <li>KEYLIMIT</li>
	 * </ul>
	 *
	 * @param username <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param password <strong>(REQUIRED)</strong> (<strong>SORRY</strong> - there was no description provided in the documentation)
	 * @param token  <em>(OPTIONAL)</em> Required when two-factor authentication is enabled.
	 * @param expires  <em>(OPTIONAL)</em> Number of hours the key will remain valid, between 0 and 8760. 0 means no expiration. Defaults to 168.
	 * @param label  <em>(OPTIONAL)</em> An optional label for this key.
	 *
	 * @return the parsed UserGetApiKeyResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public UserGetApiKeyResponse getUserGetApiKey(String username, String password, String token, Long expires, String label) throws ApiException {
		return(new UserGetApiKeyResponse(execute(UserRequest.getapikey(username, password, token, expires, label)).getJSON()));
	}

	/**
	 * <p>Estimates the invoice for adding a new Linode or NodeBalancer as well as resizing a Linode. 
	 * This returns two fields: PRICE which is the estimated cost of the invoice, and INVOICE_TO which is 
	 * the date invoice would be though with timezone set to America/New_York</p> 
	 *
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>VALIDATION</li>
	 * </ul>
	 * @param mode <strong>(REQUIRED)</strong>   This is one of the following options: 'linode_new', 'linode_resize', or 'nodebalancer_new'.
	 *
	 * @return the parsed AccountEstimateInvoiceResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public AccountEstimateInvoiceResponse getAccountEstimateInvoice(String mode) throws ApiException {
		return(new AccountEstimateInvoiceResponse(execute(AccountRequest.estimateinvoice(mode)).getJSON()));
	}

	/**
	 * <p>Estimates the invoice for adding a new Linode or NodeBalancer as well as resizing a Linode. 
	 * This returns two fields: PRICE which is the estimated cost of the invoice, and INVOICE_TO which is 
	 * the date invoice would be though with timezone set to America/New_York</p> 
	 * 
	 * Possible return error codes:
	 * 
	 * <ul>
	 *   <li>VALIDATION</li>
	 * </ul>
	 *
	 * @param mode <strong>(REQUIRED)</strong> This is one of the following options: 'linode_new', 'linode_resize', or 'nodebalancer_new'.
	 * @param paymentTerm  <em>(OPTIONAL)</em> Subscription term in months. One of: 1, 12, or 24. This is required for modes 'linode_new' and 'nodebalancer_new'.
	 * @param planID  <em>(OPTIONAL)</em> The desired PlanID available from avail.LinodePlans(). This is required for modes 'linode_new' and 'linode_resize'.
	 * @param linodeID  <em>(OPTIONAL)</em> This is the LinodeID you want to resize and is required for mode 'linode_resize'.
	 *
	 * @return the parsed AccountEstimateInvoiceResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there is an error with the call
	 */
	public AccountEstimateInvoiceResponse getAccountEstimateInvoice(String mode, Long paymentTerm, Long planID, Long linodeID) throws ApiException {
		return(new AccountEstimateInvoiceResponse(execute(AccountRequest.estimateinvoice(mode, paymentTerm, planID, linodeID)).getJSON()));
	}

	/**
	 * <p>Shows information about your account such as the date your account was opened as well as your 
	 * network utilization for the current month in gigabytes.</p> 
	 *
	 *
	 * @return the parsed AccountInfoResponse response object
	 *
	 * @throws ApiException if a required parameter is null, or there was an error with the call
	 */
	public AccountInfoResponse getAccountInfo() throws ApiException {
		return(new AccountInfoResponse(execute(AccountRequest.info()).getJSON()));
	}

}
