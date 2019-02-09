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
package org.c4c.tiny.controller;

import java.util.List;

import org.c4c.tiny.Utils;
import org.c4c.tiny.domain.UrlData;
import org.c4c.tiny.model.UrlDataResource;

import org.c4c.tiny.services.UrlDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/url")
public class UrlDataController extends BaseController {

	@Autowired
	private UrlDataService _service;

	@PostMapping(consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public @ResponseBody ResponseEntity<?> create(@RequestBody UrlDataResource data) {
		if(data.getUrl()==null || data.getUrl().trim().equals("") 
				|| data.getUrl().length() > 2048 || !Utils.isValidUrl(data.getUrl())) {
			 return sendBadRequest();
		}
		return new ResponseEntity<UrlData>(this._service.create(data, getLoggedinUser()), HttpStatus.OK);
	}

	private UrlData findUrlById(String id) {

		UrlData data = this._service.findById(id);
		return data;
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public @ResponseBody UrlData getUrl(@PathVariable String id) {
		return findUrlById(id);
	}
	
	@GetMapping(value = "/user", produces = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public @ResponseBody ResponseEntity<?> getAllUrlByUser() {
		return new ResponseEntity<>(findUrlByUser(), HttpStatus.OK);
	}
	
	private List<UrlData> findUrlByUser() {
		
		return this._service.findByUsername(getLoggedinUser());
		
	}

}
