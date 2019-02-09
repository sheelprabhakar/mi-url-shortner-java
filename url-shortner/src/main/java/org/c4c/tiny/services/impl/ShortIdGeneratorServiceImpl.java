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

import java.util.LinkedList;
import java.util.Queue;

import org.c4c.tiny.domain.ServiceSettings;
import org.c4c.tiny.repository.SeqRepository;
import org.c4c.tiny.services.ShortIdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortIdGeneratorServiceImpl implements ShortIdGeneratorService {

	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static final int BASE = ALPHABET.length();
	private static final int MAX_SEQ = 100;
	private static final String SETTINGS_KEY = "MAX_SEQ";

	@Autowired
	private SeqRepository _repository;

	private Queue<String> _seqNos;

	@Autowired
	public ShortIdGeneratorServiceImpl() {
		_seqNos = new LinkedList<String>();
	}

	public int decode(String str) {
		int num = 0;
		for (int i = 0; i < str.length(); i++)
			num = num * BASE + ALPHABET.indexOf(str.charAt(i));
		return num;
	}

	public String encode(int num) {
		StringBuilder sb = new StringBuilder();
		while (num > 0) {
			sb.append(ALPHABET.charAt(num % BASE));
			num /= BASE;
		}
		return sb.reverse().toString();
	}

	@Override
	public String getNextSeq() {
		if (!_seqNos.isEmpty())
			return _seqNos.poll();
		else {
			ServiceSettings settings;
			int seqNo = getMaxSeq();
			for (int i = 0; i < MAX_SEQ; ++i) {
				_seqNos.add(encode(seqNo++));
			}

			settings = new ServiceSettings();
			settings.setKey(SETTINGS_KEY);
			settings.setValue(String.valueOf(seqNo));
			_repository.save(settings);

			return _seqNos.poll();
		}
	}

	private int getMaxSeq() {
		int seqNo =1;
		if(_repository.findById(SETTINGS_KEY).isPresent())
		{	
			ServiceSettings settings = _repository.findById(SETTINGS_KEY).get();
			if (settings != null) {
				seqNo = Integer.valueOf(settings.getValue());
			}
		}
		return seqNo;
	}

}
