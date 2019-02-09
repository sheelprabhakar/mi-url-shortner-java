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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.c4c.tiny.App;
import org.c4c.tiny.domain.UrlData;
import org.c4c.tiny.model.UrlDataResource;
import org.c4c.tiny.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
public class AppControllerTest {

	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length:3600000}")
	private long validityInMilliseconds = 3600000; // 1h
	
	@Autowired
	private MockMvc mvc;
	
	private String token;
	@Before
	public void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		token = createToken("admin");
	}

	private String createToken(String username) {

		Claims claims = Jwts.claims().setSubject(username);
		List<String> authorithies = new ArrayList<>();
		authorithies.add("ROLE_ADMIN");
		claims.put("authorities", authorithies);

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();
	}
	
	@Test
	public void test_create_short_url() throws Exception {
		UrlDataResource res = new UrlDataResource();
		res.setUrl("https://www.google.com");

		MvcResult result = mvc
				.perform(post("/api/url").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).content(TestUtil.convertToJSON(res)))
				.andExpect(status().isOk()).andReturn();
		String str = result.getResponse().getContentAsString();

		UrlData response = TestUtil.convertToObject(UrlData.class, str);
		assertNotNull(response.getShortUrl());
		assertEquals(response.getUrl(), res.getUrl());
	}
	
	@Test
	public void test_list_all_Url_by_user() throws Exception {
		UrlDataResource res = new UrlDataResource();
		res.setUrl("https://www.google.co.in");

		MvcResult result = mvc
				.perform(post("/api/url").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).content(TestUtil.convertToJSON(res)))
				.andExpect(status().isOk()).andReturn();
		String str = result.getResponse().getContentAsString();

		UrlData response = TestUtil.convertToObject(UrlData.class, str);
		assertNotNull(response.getShortUrl());
		assertEquals(response.getUrl(), res.getUrl());
		
		result = mvc.perform(get("/api/url/user").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
		.andExpect(status().isOk()).andReturn();
		str = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		//List<UrlData> responseList = mapper.readValue(str, new TypeReference<List<UrlData>>(){});
		List<UrlData> responseList = mapper.readValue(str, mapper.getTypeFactory().constructCollectionType(List.class, UrlData.class));
		if(responseList != null) {
			assertEquals((responseList.size() >0), true);
		}
	}

	@Test
	public void test_create_short_url_null_empty_check() throws Exception {
		UrlDataResource res = new UrlDataResource();

		mvc.perform(post("/api/url").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).content(TestUtil.convertToJSON(res)))
				.andExpect(status().isBadRequest());

		res.setUrl("");

		mvc.perform(post("/api/url").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).content(TestUtil.convertToJSON(res)))
				.andExpect(status().isBadRequest());

	}

}
