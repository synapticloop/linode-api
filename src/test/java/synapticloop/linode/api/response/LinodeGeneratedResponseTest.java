package synapticloop.linode.api.response;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import synapticloop.linode.exception.ApiException;


/**
 * This is a generated test class for the Linode api calls, this was 
 * automatically generated from the linode api documentation - which can be 
 * found here:
 * <a href="http://www.linode.com/api/linode">http://www.linode.com/api/linode</a>
 * 
 * @author synapticloop
 */

public class LinodeGeneratedResponseTest {

	@Test
	public void testLinodeJob_boot() throws JSONException, ApiException {

		new LinodeJobResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.boot\"," + 
				"   \"DATA\":{" + 
				"      \"JobID\":1293" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinode_clone() throws JSONException, ApiException {

		new LinodeResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.clone\"," + 
				"   \"DATA\":{" + 
				"      \"LinodeID\":8098" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinodeConfig_configcreate() throws JSONException, ApiException {

		new LinodeConfigResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.config.create\"," + 
				"   \"DATA\":{" + 
				"      \"ConfigID\":31239" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinodeConfig_configdelete() throws JSONException, ApiException {

		new LinodeConfigResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.config.delete\"," + 
				"   \"DATA\":{" + 
				"      \"ConfigID\":31239" + 
				"   }" + 
				"}"));

	}


//	@Test
	public void testLinodeConfigList_configlist() throws JSONException, ApiException {

		new LinodeConfigListResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.config.list\"," + 
				"   \"DATA\":[" + 
				"      {" + 
				"         \"helper_disableUpdateDB\":1," + 
				"         \"RootDeviceRO\":true," + 
				"         \"RootDeviceCustom\":\"\"," + 
				"         \"Label\":\"My configuration profile\"," + 
				"         \"DiskList\":\"55319,55590,,55591,55592,,,,\"," + 
				"         \"LinodeID\":8098," + 
				"         \"Comments\":\"\"," + 
				"         \"ConfigID\":31058," + 
				"         \"helper_xen\":1," + 
				"         \"RunLevel\":\"default\"," + 
				"         \"helper_depmod\":1," + 
				"         \"KernelID\":85," + 
				"         \"RootDeviceNum\":1," + 
				"         \"helper_libtls\":false," + 
				"         \"RAMLimit\":0" + 
				"      }," + 
				"      {" + 
				"         \"helper_disableUpdateDB\":1," + 
				"         \"RootDeviceRO\":true," + 
				"         \"RootDeviceCustom\":\"\"," + 
				"         \"Label\":\"test profile\"," + 
				"         \"DiskList\":\",,,,,,,,\"," + 
				"         \"LinodeID\":8098," + 
				"         \"Comments\":\"\"," + 
				"         \"ConfigID\":31231," + 
				"         \"helper_xen\":1," + 
				"         \"RunLevel\":\"default\"," + 
				"         \"helper_depmod\":1," + 
				"         \"KernelID\":85," + 
				"         \"RootDeviceNum\":1," + 
				"         \"helper_libtls\":false," + 
				"         \"RAMLimit\":0" + 
				"      }" + 
				"   ]" + 
				"}"));

	}


	@Test
	public void testLinodeConfig_configupdate() throws JSONException, ApiException {

		new LinodeConfigResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.config.update\"," + 
				"   \"DATA\":{" + 
				"      \"ConfigID\":31239" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinode_create() throws JSONException, ApiException {

		new LinodeResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.create\"," + 
				"   \"DATA\":{" + 
				"      \"LinodeID\":8098" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinode_delete() throws JSONException, ApiException {

		new LinodeResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.delete\"," + 
				"   \"DATA\":{" + 
				"      \"LinodeID\":8204" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinodeDisk_diskcreate() throws JSONException, ApiException {

		new LinodeDiskResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.disk.create\"," + 
				"   \"DATA\":{" + 
				"      \"JobID\":1298," + 
				"      \"DiskID\":55647" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinodeDisk_diskcreatefromdistribution() throws JSONException, ApiException {

		new LinodeDiskResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.disk.createFromDistribution\"," + 
				"   \"DATA\":{" + 
				"      \"JobID\":1298," + 
				"      \"DiskID\":55647" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinodeDisk_diskcreatefromimage() throws JSONException, ApiException {

		new LinodeDiskResponse(new JSONObject("{" + 
				"   ERRORARRAY: []," + 
				"   ACTION: \"linode.disk.createfromimage\"," + 
				"   DATA: {" + 
				"      JobID: 999," + 
				"      DiskID: 4567" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinodeDisk_diskcreatefromstackscript() throws JSONException, ApiException {

		new LinodeDiskResponse(new JSONObject("{" + 
				"   ERRORARRAY: [ ]," + 
				"   DATA: {" + 
				"      JobID: 445," + 
				"      DiskID: 770050" + 
				"   }," + 
				"   ACTION: \"linode.disk.createfromstackscript\"" + 
				"}"));

	}


	@Test
	public void testLinodeDisk_diskdelete() throws JSONException, ApiException {

		new LinodeDiskResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.disk.delete\"," + 
				"   \"DATA\":{" + 
				"      \"JobID\":1298," + 
				"      \"DiskID\":55648" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinodeDisk_diskduplicate() throws JSONException, ApiException {

		new LinodeDiskResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.disk.duplicate\"," + 
				"   \"DATA\":{" + 
				"      \"JobID\":1299," + 
				"      \"DiskID\":55648" + 
				"   }" + 
				"}"));

	}


//	@Test
	public void testLinodeImage_diskimagize() throws JSONException, ApiException {

		new LinodeImageResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.disk.imagize\"," + 
				"   \"DATA\":{" + 
				"      \"JobID\":5693," + 
				"      \"ImageID\":1293" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinodeDiskList_disklist() throws JSONException, ApiException {

		new LinodeDiskListResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.disk.list\"," + 
				"   \"DATA\":[" + 
				"      {" + 
				"         \"UPDATE_DT\":\"2009-06-30 13:19:00.0\"," + 
				"         \"DISKID\":55319," + 
				"         \"LABEL\":\"test label\"," + 
				"         \"TYPE\":\"ext3\"," + 
				"         \"LINODEID\":8098," + 
				"         \"ISREADONLY\":0," + 
				"         \"STATUS\":1," + 
				"         \"CREATE_DT\":\"2008-04-04 10:08:06.0\"," + 
				"         \"SIZE\":4096" + 
				"      }," + 
				"      {" + 
				"         \"UPDATE_DT\":\"2009-07-18 12:53:043.0\"," + 
				"         \"DISKID\":55320," + 
				"         \"LABEL\":\"256M Swap Image\"," + 
				"         \"TYPE\":\"swap\"," + 
				"         \"LINODEID\":8098," + 
				"         \"ISREADONLY\":0," + 
				"         \"STATUS\":1," + 
				"         \"CREATE_DT\":\"2008-04-04 10:08:06.0\"," + 
				"         \"SIZE\":256" + 
				"      }" + 
				"   ]" + 
				"}"));

	}


	@Test
	public void testLinodeDisk_diskresize() throws JSONException, ApiException {

		new LinodeDiskResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.disk.resize\"," + 
				"   \"DATA\":{" + 
				"      \"JobID\":1298," + 
				"      \"DiskID\":55648" + 
				"   }" + 
				"}"));

	}


//	@Test
	public void testLinodeDisk_diskupdate() throws JSONException, ApiException {

		new LinodeDiskResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.disk.update\"," + 
				"   \"DATA\":{" + 
				"      \"DiskID\":55647" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinodeIp_ipaddprivate() throws JSONException, ApiException {

		new LinodeIpResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\": [ ]," + 
				"   \"DATA\": {" + 
				"      \"IPAddressID\": 8364," + 
				"      \"IPAddress\": \"192.168.131.118\"" + 
				"   }," + 
				"   \"ACTION\": \"linode.ip.addprivate\"" + 
				"}"));

	}


	@Test
	public void testLinodeIp_ipaddpublic() throws JSONException, ApiException {

		new LinodeIpResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\": [ ]," + 
				"   \"DATA\": {" + 
				"      \"IPADDRESSID\": 5384," + 
				"      \"IPADDRESS\": \"75.128.96.54\"" + 
				"   }," + 
				"   \"ACTION\": \"linode.ip.addpublic\"" + 
				"}"));

	}


	@Test
	public void testLinodeIpList_iplist() throws JSONException, ApiException {

		new LinodeIpListResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.ip.list\"," + 
				"   \"DATA\":[" + 
				"      {" + 
				"         \"LINODEID\":8098," + 
				"         \"ISPUBLIC\":1," + 
				"         \"IPADDRESS\":\"75.127.96.54\"," + 
				"         \"RDNS_NAME\":\"li22-54.members.linode.com\"," + 
				"         \"IPADDRESSID\":5384" + 
				"      }," + 
				"      {" + 
				"         \"LINODEID\":8099," + 
				"         \"ISPUBLIC\":1," + 
				"         \"IPADDRESS\":\"75.127.96.245\"," + 
				"         \"RDNS_NAME\":\"li22-245.members.linode.com\"," + 
				"         \"IPADDRESSID\":5575" + 
				"      }" + 
				"   ]" + 
				"}"));

	}


//	@Test
	public void testLinodeIpSetRdns_ipsetrdns() throws JSONException, ApiException {

		new LinodeIpSetRdnsResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\": [ ]," + 
				"   \"DATA\": {" + 
				"      \"HOSTNAME\": \"li13-10.members.linode.com\"," + 
				"      \"IPADDRESSID\": 5384," + 
				"      \"IPADDRESS\": \"69.93.127.10\"" + 
				"   }," + 
				"   \"ACTION\": \"linode.ip.setrdns\"" + 
				"}"));

	}


	@Test
	public void testLinodeIpSwap_ipswap() throws JSONException, ApiException {

		new LinodeIpSwapResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\": [ ]," + 
				"   \"DATA\": [" + 
				"      {" + 
				"         \"LINODEID\": 8099," + 
				"         \"IPADDRESS\": \"75.128.96.54\"," + 
				"         \"IPADDRESSID\": 5384" + 
				"      }," + 
				"      {" + 
				"         \"IPADDRESS\": \"75.127.96.245\"," + 
				"         \"LINODEID\": 8098," + 
				"         \"IPADDRESSID\": 5575" + 
				"      }" + 
				"   ]," + 
				"   \"ACTION\": \"linode.ip.swap\"" + 
				"}"));

	}


	@Test
	public void testLinodeJobList_joblist() throws JSONException, ApiException {

		new LinodeJobListResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.job.list\"," + 
				"   \"DATA\":[" + 
				"      {" + 
				"         \"ENTERED_DT\":\"2009-06-29 18:43:25.0\"," + 
				"         \"ACTION\":\"fs.create\"," + 
				"         \"LABEL\":\"Create Filesystem - test label\"," + 
				"         \"HOST_START_DT\":\"2009-07-27 15:32:40.0\"," + 
				"         \"LINODEID\":8098," + 
				"         \"HOST_FINISH_DT\":\"2009-07-27 15:32:40.0\"," + 
				"         \"DURATION\":0," + 
				"         \"HOST_MESSAGE\":\"\"," + 
				"         \"JOBID\":1207," + 
				"         \"HOST_SUCCESS\":1" + 
				"      }," + 
				"      {" + 
				"         \"ENTERED_DT\":\"2009-06-16 18:08:36.0\"," + 
				"         \"ACTION\":\"linode.shutdown\"," + 
				"         \"LABEL\":\"System Shutdown\"," + 
				"         \"HOST_START_DT\":\"2009-07-27 15:32:39.0\"," + 
				"         \"LINODEID\":8098," + 
				"         \"HOST_FINISH_DT\":\"2009-07-27 15:32:40.0\"," + 
				"         \"DURATION\":1," + 
				"         \"HOST_MESSAGE\":\"\"," + 
				"         \"JOBID\":1205," + 
				"         \"HOST_SUCCESS\":1" + 
				"      }" + 
				"   ]" + 
				"}"));

	}


	@Test
	public void testLinodeKvmIfy_kvmify() throws JSONException, ApiException {

	/*
		new LinodeKvmIfyResponse(new JSONObject("No example."));

	*/
	}


	@Test
	public void testLinodeList_list() throws JSONException, ApiException {

		new LinodeListResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.list\"," + 
				"   \"DATA\":[" + 
				"      {" + 
				"         \"TOTALXFER\":2000," + 
				"         \"BACKUPSENABLED\":1," + 
				"         \"WATCHDOG\":1," + 
				"         \"LPM_DISPLAYGROUP\":\"\"," + 
				"         \"ALERT_BWQUOTA_ENABLED\":1," + 
				"         \"STATUS\":2," + 
				"         \"TOTALRAM\":1024," + 
				"         \"ALERT_DISKIO_THRESHOLD\":1000," + 
				"         \"BACKUPWINDOW\":1," + 
				"         \"ALERT_BWOUT_ENABLED\":1," + 
				"         \"ALERT_BWOUT_THRESHOLD\":5," + 
				"         \"LABEL\":\"api-node3\"," + 
				"         \"ALERT_CPU_ENABLED\":1," + 
				"         \"ALERT_BWQUOTA_THRESHOLD\":80," + 
				"         \"ALERT_BWIN_THRESHOLD\":5," + 
				"         \"BACKUPWEEKLYDAY\":0," + 
				"         \"DATACENTERID\":5," + 
				"         \"ALERT_CPU_THRESHOLD\":90," + 
				"         \"TOTALHD\":40960," + 
				"         \"ALERT_DISKIO_ENABLED\":1," + 
				"         \"ALERT_BWIN_ENABLED\":1," + 
				"         \"LINODEID\":8098," + 
				"         \"CREATE_DT\":\"2015-09-22 11:33:06.0\"," + 
				"         \"PLANID\":1," + 
				"         \"DISTRIBUTIONVENDOR\": \"Debian\"," + 
				"         \"ISXEN\":0," + 
				"         \"ISKVM\":1" + 
				"      }" + 
				"   ]" + 
				"}"));

	}


	@Test
	public void testLinodeMutate_mutate() throws JSONException, ApiException {

	/*
		new LinodeMutateResponse(new JSONObject("No example."));

	*/
	}


	@Test
	public void testLinodeJob_reboot() throws JSONException, ApiException {

		new LinodeJobResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.reboot\"," + 
				"   \"DATA\":{" + 
				"      \"JobID\":1295" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinodeResize_resize() throws JSONException, ApiException {

		new LinodeResizeResponse(new JSONObject("{" + 
				"   ERRORARRAY: [" + 
				"      {" + 
				"         ERRORCODE: 0," + 
				"         ERRORMESSAGE: \"ok\"" + 
				"      }" + 
				"   ]," + 
				"   DATA: { }," + 
				"   ACTION: \"linode.resize\"" + 
				"}"));

	}


	@Test
	public void testLinodeJob_shutdown() throws JSONException, ApiException {

		new LinodeJobResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.shutdown\"," + 
				"   \"DATA\":{" + 
				"      \"JobID\":1292" + 
				"   }" + 
				"}"));

	}


	@Test
	public void testLinode_update() throws JSONException, ApiException {

		new LinodeResponse(new JSONObject("{" + 
				"   \"ERRORARRAY\":[]," + 
				"   \"ACTION\":\"linode.update\"," + 
				"   \"DATA\":{" + 
				"      \"LinodeID\":8098" + 
				"   }" + 
				"}"));

	}

};