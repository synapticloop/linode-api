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
	 * @param linodeRequest the request to be made
	 * 
	 * @return the response of the request
	 * 
	 * @throws ApiException if there was a problem with the request
	 */
	public LinodeResponse execute(LinodeRequest linodeRequest) throws ApiException {
		try {
			HttpPost httpPost = new HttpPost(API_ENDPOINT);

			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair(PARAMETER_API_KEY, this.apiKey));
			postParameters.add(new BasicNameValuePair(PARAMETER_API_ACTION, linodeRequest.getAction()));

			// now add the parameters
			Map<String, String> parameters = linodeRequest.getParameters();
			for (String parameterKey : parameters.keySet()) {
				postParameters.add(new BasicNameValuePair(parameterKey, parameters.get(parameterKey)));
			}
			

			String response = callApi(linodeRequest.getAction(), httpPost, postParameters);

			return(new LinodeResponse(new JSONObject(response)));
		} catch (JSONException ex) {
			throw new ApiException(ex);
		}
	}

	/**
	 * Execute a batch request to the linode api.
	 * 
	 * @param linodeRequests The list of requests to execute
	 * 
	 * @return the list of responses to the requests
	 * 
	 * @throws ApiException if there was an error with the request
	 */
	public List<LinodeResponse> execute(List<LinodeRequest> linodeRequests) throws ApiException {
		List<LinodeResponse> linodeResponses = new ArrayList<LinodeResponse>();
		try {
			HttpPost httpPost = new HttpPost(API_ENDPOINT);

			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair(PARAMETER_API_KEY, this.apiKey));
			postParameters.add(new BasicNameValuePair(PARAMETER_API_ACTION, PARAMETER_BATCH));

			// now add the parameters
			JSONArray apiRequestArray = new JSONArray();
			for (LinodeRequest linodeRequest : linodeRequests) {
				JSONObject jsonObject = new JSONObject();

				Map<String, String> parameters = linodeRequest.getParameters();
				jsonObject.put(PARAMETER_API_ACTION, linodeRequest.getAction());
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
				linodeResponses.add(new LinodeResponse(jsonArray.getJSONObject(i)));
			}
		} catch (JSONException | IOException ex) {
			throw new ApiException(ex);
		}

		return(linodeResponses);
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
}
