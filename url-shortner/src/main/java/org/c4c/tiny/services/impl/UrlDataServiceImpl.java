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
package org.c4c.tiny.services.impl;

import java.util.Calendar;
import java.util.List;

import org.c4c.tiny.domain.UrlData;
import org.c4c.tiny.model.UrlDataResource;
import org.c4c.tiny.repository.UrlDataRepository;
import org.c4c.tiny.services.ShortIdGeneratorService;
import org.c4c.tiny.services.UrlDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.ssm.api.ParameterValueKeyProvider;

import com.google.code.ssm.api.ReadThroughSingleCache;

@Service
public class UrlDataServiceImpl implements UrlDataService {

	@Autowired
	private UrlDataRepository _urlData;

	@Autowired
	private ShortIdGeneratorService _shortIdGenerator;

	@Override
	public void addUrl(UrlData data) {
		this._urlData.save(data);

	}

	@Override
	public UrlData create(UrlDataResource data, String username) {

		UrlData d = new UrlData();
		d.setShortUrl(_shortIdGenerator.getNextSeq());
		d.setActive(true);
		d.setCreateDate(Calendar.getInstance());
		d.setUrl(data.getUrl());
		d.setUsername(username);
		return this._urlData.save(d);

	}
	@ReadThroughSingleCache(namespace="UrlData", expiration = 300)
	@Override
	public UrlData findById(@ParameterValueKeyProvider String id) {
		if(this._urlData.findById(id).isPresent())
			return this._urlData.findById(id).get();
		else {
			return null;
		}
	}

	@Override
	public List<UrlData> getallUrls() {

		return (List<UrlData>) this._urlData.findAll();
	}

	@Override
	public List<UrlData> findByUsername(String username) {
		return (List<UrlData>) this._urlData.findByUsername(username);
	}

}
