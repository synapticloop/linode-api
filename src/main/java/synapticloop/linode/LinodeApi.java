package synapticloop.linode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import synapticloop.linode.exception.ApiException;

import synapticloop.linode.api.response.*;

/**
 * The main class for interacting with the Linode api.
 * 
 * @author synapticloop
 */
public class LinodeApi {
	private static final Logger LOGGER = Logger.getLogger(LinodeApi.class.getName());

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

			return(new LinodeApiResponse(new JSONObject(response)));
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
				if(LOGGER.isLoggable(Level.INFO)) {
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append("Response for end point: ");
					stringBuilder.append(API_ENDPOINT);
					stringBuilder.append(", with action '");
					stringBuilder.append(action);
					stringBuilder.append("'");
					LOGGER.info(stringBuilder.toString());
					LOGGER.info(response);
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
			stringBuilder.append("Non-200 HTTP Status code returned:");
			stringBuilder.append(statusCode);
			stringBuilder.append(" for action '");
			stringBuilder.append(action);
			stringBuilder.append("'.");
			throw new ApiException(stringBuilder.toString());
		}
	}

	public ImageResponse getImageDelete() { return(null); };

	public ImageListResponse getImageList() { return(null); };

	public ImageResponse getImageUpdate() { return(null); };

	public AvailDatacentersResponse getAvailDatacenters() { return(null); };

	public AvailDistributionsResponse getAvailDistributions() { return(null); };

	public AvailKernelsResponse getAvailKernels() { return(null); };

	public AvailLinodePlansResponse getAvailLinodePlans() { return(null); };

	public AvailNodeBalancersResponse getAvailNodeBalancers() { return(null); };

	public AvailStackscriptsResponse getAvailStackscripts() { return(null); };

	public TestEchoResponse getTestEcho() { return(null); };

	public StackscriptResponse getStackscriptCreate() { return(null); };

	public StackscriptResponse getStackscriptDelete() { return(null); };

	public StackscriptListResponse getStackscriptList() { return(null); };

	public StackscriptResponse getStackscriptUpdate() { return(null); };

	public DomainResponse getDomainCreate() { return(null); };

	public DomainResponse getDomainDelete() { return(null); };

	public DomainListResponse getDomainList() { return(null); };

	public DomainResourceCreateResponse getDomainResourceCreate() { return(null); };

	public DomainResourceDeleteResponse getDomainResourceDelete() { return(null); };

	public DomainResourceListResponse getDomainResourceList() { return(null); };

	public DomainResourceUpdateResponse getDomainResourceUpdate() { return(null); };

	public DomainResponse getDomainUpdate() { return(null); };

	public LinodeBootResponse getLinodeBoot() { return(null); };

	public LinodeCloneResponse getLinodeClone() { return(null); };

	public LinodeConfigCreateResponse getLinodeConfigCreate() { return(null); };

	public LinodeConfigDeleteResponse getLinodeConfigDelete() { return(null); };

	public LinodeConfigListResponse getLinodeConfigList() { return(null); };

	public LinodeConfigUpdateResponse getLinodeConfigUpdate() { return(null); };

	public LinodeResponse getLinodeCreate() { return(null); };

	public LinodeResponse getLinodeDelete() { return(null); };

	public LinodeDiskCreateResponse getLinodeDiskCreate() { return(null); };

	public LinodeDiskCreateFromDistributionResponse getLinodeDiskCreateFromDistribution() { return(null); };

	public LinodeDiskCreateFromImageResponse getLinodeDiskCreateFromImage() { return(null); };

	public LinodeDiskCreateFromStackscriptResponse getLinodeDiskCreateFromStackscript() { return(null); };

	public LinodeDiskDeleteResponse getLinodeDiskDelete() { return(null); };

	public LinodeDiskDuplicateResponse getLinodeDiskDuplicate() { return(null); };

	public LinodeDiskImagizeResponse getLinodeDiskImagize() { return(null); };

	public LinodeDiskListResponse getLinodeDiskList() { return(null); };

	public LinodeDiskResizeResponse getLinodeDiskResize() { return(null); };

	public LinodeDiskUpdateResponse getLinodeDiskUpdate() { return(null); };

	public LinodeIpAddressPrivateResponse getLinodeIpAddressPrivate() { return(null); };

	public LinodeIpAddressPublicResponse getLinodeIpAddressPublic() { return(null); };

	public LinodeIpListResponse getLinodeIpList() { return(null); };

	public LinodeIpSetRdnsResponse getLinodeIpSetRdns() { return(null); };

	public LinodeIpSwapResponse getLinodeIpSwap() { return(null); };

	public LinodeJobListResponse getLinodeJobList() { return(null); };

	public LinodeListResponse getLinodeList() { return(null); };

	public LinodeRebootResponse getLinodeReboot() { return(null); };

	public LinodeResizeResponse getLinodeResize() { return(null); };

	public LinodeShutdownResponse getLinodeShutdown() { return(null); };

	public LinodeResponse getLinodeUpdate() { return(null); };

	public NodebalancerConfigCreateResponse getNodebalancerConfigCreate() { return(null); };

	public NodebalancerConfigDeleteResponse getNodebalancerConfigDelete() { return(null); };

	public NodebalancerConfigListResponse getNodebalancerConfigList() { return(null); };

	public NodebalancerConfigUpdateResponse getNodebalancerConfigUpdate() { return(null); };

	public NodebalancerResponse getNodebalancerCreate() { return(null); };

	public NodebalancerResponse getNodebalancerDelete() { return(null); };

	public NodebalancerListResponse getNodebalancerList() { return(null); };

	public NodebalancerNodeCreateResponse getNodebalancerNodeCreate() { return(null); };

	public NodebalancerNodeDeleteResponse getNodebalancerNodeDelete() { return(null); };

	public NodebalancerNodeListResponse getNodebalancerNodeList() { return(null); };

	public NodebalancerNodeUpdateResponse getNodebalancerNodeUpdate() { return(null); };

	public NodebalancerResponse getNodebalancerUpdate() { return(null); };

	public ApiSpecResponse getApiSpec() { return(null); };

	public UserGetApiKeyResponse getUserGetApiKey() { return(null); };

	public AccountEstimateInvoiceResponse getAccountEstimateInvoice() { return(null); };

	public AccountInfoResponse getAccountInfo() { return(null); };
}
