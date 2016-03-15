package synapticloop.linode.api.request;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.LinodeApi;
import synapticloop.linode.LinodeApiRequest;
import synapticloop.linode.LinodeApiResponse;
import synapticloop.linode.exception.ApiException;


public class ImageRequestTest {
	private LinodeApi linodeApi = null;

	@Before
	public void setup() {
		linodeApi = new LinodeApi(System.getenv("LINODE_API_KEY"), true);
	}

	@Test
	public void testListImages() throws ApiException, JSONException {
		LinodeApiRequest linodeRequest = ImageRequest.list();
		LinodeApiResponse linodeResponse = linodeApi.execute(linodeRequest);
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testImageList() throws Exception, ApiException {
		LinodeApiResponse linodeResponse = linodeApi.execute(ImageRequest.list());
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testImageListParameters() throws Exception, ApiException {
		LinodeApiResponse linodeResponse = linodeApi.execute(ImageRequest.list(null, null));
		Assert.assertEquals(0, linodeResponse.getErrorArray().length());
	}

	@Test
	public void testDeleteImagesInvalid() throws ApiException, JSONException {
		LinodeApiRequest linodeRequest = ImageRequest.delete(-1l);
		LinodeApiResponse linodeResponse = linodeApi.execute(linodeRequest);
		Assert.assertEquals(1, linodeResponse.getErrorArray().length());
	}

	@Test(expected = ApiException.class)
	public void testUpdateImagesInvalid() throws ApiException {
		linodeApi.execute(ImageRequest.update(null, null, null));
	}

}
