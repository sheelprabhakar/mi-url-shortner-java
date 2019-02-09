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
package org.c4c.tiny.service.test;

import org.c4c.tiny.services.ShortIdGeneratorService;
import org.c4c.tiny.services.impl.ShortIdGeneratorServiceImpl;


//@RunWith(PowerMockRunner.class)
//@PrepareForTest(ShortIdGeneratorServiceImpl.class)
public class TestShortIdGeneratorService {

	ShortIdGeneratorService _service = new ShortIdGeneratorServiceImpl();

	
	//@Test
	public void test_getNextSeq() throws Exception {
		/* when(this._repository.findById("MAX_SEQ")).thenReturn(100);
		//ShortIdGeneratorService spy = PowerMockito.spy(_service);
		PowerMockito.doReturn(100).when(spy, "getMaxSeq");
		String value = spy.getNextSeq();

		assertEquals("bM", value);
		value = spy.getNextSeq();
		assertEquals("bN", value);*/
	}

}
