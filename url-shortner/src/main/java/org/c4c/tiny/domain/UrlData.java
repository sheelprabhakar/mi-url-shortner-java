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
package org.c4c.tiny.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity(name = "url_data")
@Table(name = "url_data")
public class UrlData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5142743467011015611L;

	@Id
	@Column(name = "short_url", nullable = false, length = 6)
	private String shortUrl;

	@Column(name = "url", nullable = false, length = 2048)
	private String url;

	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name = "create_date", nullable = false)
	private Calendar createDate;

	@Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
	@Column(unique = false, nullable = false)
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "hit_count", nullable = false)
	private int hitCount;

	@Column(name = "active", nullable = false)
	private boolean active;

	public Calendar getCreateDate() {
		return createDate;
	}

	public int getHitCount() {
		return hitCount;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public String getUrl() {
		return url;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
