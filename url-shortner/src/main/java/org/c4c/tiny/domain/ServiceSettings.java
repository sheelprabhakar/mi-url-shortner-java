package org.c4c.tiny.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="service_settings")
@Table(name="service_settings")
public class ServiceSettings {
	@Id
	@Column(name = "id", nullable = false, length = 16)
	private String key;
	
	@Column(name = "value", nullable = false, length = 128)
	private String value;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
