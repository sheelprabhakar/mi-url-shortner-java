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
package org.c4c.tiny.account.controller.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.c4c.tiny.account.App;
import org.c4c.tiny.account.domain.Role;
import org.c4c.tiny.account.model.UserResource;
import org.c4c.tiny.account.model.UserResponseResource;
import org.c4c.tiny.account.util.test.TestUtil;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
public class AppControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void test_signup_new_user() throws Exception {
		UserResource resource = new UserResource();
		resource.setEmail("sheel.prabhakar@gmail.com");
		resource.setUsername("sheel");
		resource.setPassword("pas01234");
		mvc.perform(post("/api/accounts/signup").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertToJSON(resource))).andExpect(status().isOk()).andReturn();

	}

	@Test
	public void test_signup_new_user_and_sign_in() throws Exception {
		UserResource resource = new UserResource();
		resource.setEmail("sheel.prabhakar1@gmail.com");
		resource.setUsername("sheel1");
		resource.setPassword("pas01234");
		mvc.perform(post("/api/accounts/signup").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertToJSON(resource))).andExpect(status().isOk()).andReturn();

		mvc.perform(post("/api/accounts/signin").param("username", resource.getUsername()).param("password",
				resource.getPassword())).andExpect(status().isOk());

	}

	@Test
	public void test_login_with_admin_and_check_role_by_jwt_token() throws Exception {

		MvcResult mvcResult = mvc
				.perform(post("/api/accounts/signin").param("username", "admin").param("password", "admin"))
				.andExpect(status().isOk()).andReturn();

		String token = mvcResult.getResponse().getContentAsString();

		mvcResult = mvc.perform(get("/api/accounts/me").header("Authorization", "Bearer " + token))
				.andExpect(status().isOk()).andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		UserResponseResource response = TestUtil.convertToObject(UserResponseResource.class, result);
		assertEquals(response.getRoles().get(0), Role.ROLE_ADMIN);
	}

	@Test
	public void test_login_with_admin_and_search_user() throws Exception {

		MvcResult mvcResult = mvc
				.perform(post("/api/accounts/signin").param("username", "admin").param("password", "admin"))
				.andExpect(status().isOk()).andReturn();

		String token = mvcResult.getResponse().getContentAsString();

		mvcResult = mvc.perform(get("/api/accounts/" + "client").header("Authorization", "Bearer " + token))
				.andExpect(status().isOk()).andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		UserResponseResource response = TestUtil.convertToObject(UserResponseResource.class, result);
		assertEquals(response.getUsername(), "client");
	}

	@Test
	public void test_login_with_user_and_search_user_access_denied() throws Exception {

		MvcResult mvcResult = mvc
				.perform(post("/api/accounts/signin").param("username", "client").param("password", "client"))
				.andExpect(status().isOk()).andReturn();

		String token = mvcResult.getResponse().getContentAsString();

		mvc.perform(get("/api/accounts/" + "client").header("Authorization", "Bearer " + token))
				.andExpect(status().isForbidden());

	}

	@Test
	public void test_login_with_user_and_refresh_token() throws Exception {

		MvcResult mvcResult = mvc
				.perform(post("/api/accounts/signin").param("username", "client").param("password", "client"))
				.andExpect(status().isOk()).andReturn();

		String token = mvcResult.getResponse().getContentAsString();

		mvc.perform(get("/api/accounts/refresh").header("Authorization", "Bearer " + token)).andExpect(status().isOk());

	}

	@Test
	public void test_login_with_admin_and_refresh_token() throws Exception {

		MvcResult mvcResult = mvc
				.perform(post("/api/accounts/signin").param("username", "admin").param("password", "admin"))
				.andExpect(status().isOk()).andReturn();

		String token = mvcResult.getResponse().getContentAsString();

		mvc.perform(get("/api/accounts/refresh").header("Authorization", "Bearer " + token)).andExpect(status().isOk());

	}

	@Test
	public void test_login_with_admin_and_delete_user() throws Exception {
		UserResource resource = new UserResource();
		resource.setEmail("sheel.prabhakar45@gmail.com");
		resource.setUsername("sheel45");
		resource.setPassword("pas01234");
		mvc.perform(post("/api/accounts/signup").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertToJSON(resource))).andExpect(status().isOk()).andReturn();

		MvcResult mvcResult = mvc
				.perform(post("/api/accounts/signin").param("username", "admin").param("password", "admin"))
				.andExpect(status().isOk()).andReturn();

		String token = mvcResult.getResponse().getContentAsString();

		mvc.perform(delete("/api/accounts/" + "sheel45").header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());

	}

}
