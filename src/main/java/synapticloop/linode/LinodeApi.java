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

import synapticloop.linode.exception.ApiException;

public class LinodeApi {
	private static final String PARAMETER_API_KEY = "api_key";
	private static final String PARAMETER_API_ACTION = "api_action";
	private static final String PARAMETER_API_REQUEST_ARRAY = "api_requestArray";

	/** API end point - defaults to https://api.linode.com/ */
	private static final String API_ENDPOINT = "https://api.linode.com/";

	private CloseableHttpClient closeableHttpClient = null;

	/** User's API key */
	private String apiKey = null;

	/** True to enable request debugging, false otherwise */
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
	 * @param apiKey - the user's API key
	 * @param debug - true to enable request debugging, false otherwise
	 */
	public LinodeApi(String apiKey, boolean debug) {
		this.apiKey = apiKey;

		this.closeableHttpClient = HttpClients.createDefault();
		this.debug = debug;
	}

	public LinodeResponse execute(LinodeRequest linodeRequest) throws ApiException {
		try {
			HttpPost httpPost = new HttpPost(API_ENDPOINT);

			ArrayList<NameValuePair> postParameters;
			postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair(PARAMETER_API_KEY, this.apiKey));
			postParameters.add(new BasicNameValuePair(PARAMETER_API_ACTION, linodeRequest.getAction()));

			// now add the parameters
			Map<String, String> parameters = linodeRequest.getParameters();
			for (String parameterKey : parameters.keySet()) {
				postParameters.add(new BasicNameValuePair(parameterKey, parameters.get(parameterKey)));
			}

			httpPost.setEntity(new UrlEncodedFormEntity(postParameters));

			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				throw new ApiException("Non-200 HTTP Status code returned: " + statusCode);
			}

			String response = null;
			try {
				response = EntityUtils.toString(httpResponse.getEntity());
			} catch (ParseException | IOException ex) {
				throw new ApiException(ex);
			}

			if (debug) {
				System.out.println("End point: " + API_ENDPOINT);
				System.out.println(response);
			}

			return(new LinodeResponse(new JSONObject(response)));
		} catch (JSONException | IOException ex) {
			throw new ApiException(ex);
		}
	}

	public List<LinodeResponse> execute(List<LinodeRequest> linodeRequests) throws ApiException {
		List<LinodeResponse> linodeResponses = new ArrayList<LinodeResponse>();
		try {
			HttpPost httpPost = new HttpPost(API_ENDPOINT);

			ArrayList<NameValuePair> postParameters;
			postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair(PARAMETER_API_KEY, this.apiKey));
			postParameters.add(new BasicNameValuePair(PARAMETER_API_ACTION, "batch"));

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

			CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				throw new ApiException("Non-200 HTTP Status code returned: " + statusCode);
			}

			String response = null;
			try {
				response = EntityUtils.toString(httpResponse.getEntity());
			} catch (ParseException | IOException ex) {
				throw new ApiException(ex);
			}

			if (debug) {
				System.out.println("End point: " + API_ENDPOINT);
				System.out.println(response);
			}


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
}
