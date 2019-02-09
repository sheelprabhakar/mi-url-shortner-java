/*******************************************************************************
 * Copyright 2019 Sheel Prabhakar
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.c4c.tiny.controller.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.c4c.tiny.App;
import org.c4c.tiny.domain.UrlData;
import org.c4c.tiny.model.UrlDataResource;
import org.c4c.tiny.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
public class FrontControllerTest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void test_redirect_url_ok() throws Exception {
		UrlDataResource res = new UrlDataResource();
		res.setUrl("https://www.google.com");

		MvcResult result = mvc
				.perform(post("/api/url").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertToJSON(res)))
				.andExpect(status().isOk()).andReturn();
		String str = result.getResponse().getContentAsString();

		UrlData response = TestUtil.convertToObject(UrlData.class, str);
		for(int i =0; i < 300; ++i) {
			mvc.perform(get("/"+response.getShortUrl())).andExpect(status().is3xxRedirection());
		}
		
	}
	
	@Test
	public void test_redirect_url_not_found() throws Exception {
			
		mvc.perform(get("/00000000000000")).andExpect(status().isNotFound());
	}
}
