package synapticloop.linode.api.response.bean;

/*
 * Copyright (c) 2016-2017 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import synapticloop.linode.exception.ApiException;


public class JobTest {
	private Job job;

	private static final String JOB_JSON_1207 = "{\n" + 
			"   \"ENTERED_DT\":\"2009-06-29 18:43:25.0\",\n" + 
			"   \"ACTION\":\"fs.create\",\n" + 
			"   \"LABEL\":\"Create Filesystem - test label\",\n" + 
			"   \"HOST_START_DT\":\"2009-07-27 15:32:40.0\",\n" + 
			"   \"LINODEID\":8098,\n" + 
			"   \"HOST_FINISH_DT\":\"2009-07-27 15:32:40.0\",\n" + 
			"   \"DURATION\":0,\n" + 
			"   \"HOST_MESSAGE\":\"\",\n" + 
			"   \"JOBID\":1207,\n" + 
			"   \"HOST_SUCCESS\":1\n" + 
			"}\n";

	private static final String JOB_JSON_1205 = "{\n" + 
			"   \"ENTERED_DT\":\"2009-06-16 18:08:36.0\",\n" + 
			"   \"ACTION\":\"linode.shutdown\",\n" + 
			"   \"LABEL\":\"System Shutdown\",\n" + 
			"   \"HOST_START_DT\":\"2009-07-27 15:32:39.0\",\n" + 
			"   \"LINODEID\":8098,\n" + 
			"   \"HOST_FINISH_DT\":\"2009-07-27 15:32:40.0\",\n" + 
			"   \"DURATION\":1,\n" + 
			"   \"HOST_MESSAGE\":\"\",\n" + 
			"   \"JOBID\":1205,\n" + 
			"   \"HOST_SUCCESS\":1\n" + 
			"}\n";

	@Before
	public void setup() {
	}

	@Test
	public void testParse1207() throws JSONException, ApiException {
		job = new Job(new JSONObject(JOB_JSON_1207));
		assertTrue(true);
	}

	@Test
	public void testParse1205() throws JSONException, ApiException {
		job = new Job(new JSONObject(JOB_JSON_1205));
		assertTrue(true);
	}

}
