package ch.rasc.changelog.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Splitter;

import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class Configuration extends AbstractPersistable {

	private static final long serialVersionUID = 1L;

	private String configurations;

	@Transient
	private Map<String, String> configurationMap = new HashMap<>();

	public void update(Map<String, String> modifiedMap) {
		this.configurationMap = modifiedMap;
	}

	@PostLoad
	private void postLoad() {
		if (StringUtils.isNotBlank(this.configurations)) {
			for (String keyValue : Splitter.on("|").split(this.configurations)) {
				int pos = keyValue.indexOf("=");
				String key = keyValue.substring(0, pos);
				String value = keyValue.substring(pos + 1);
				this.configurationMap.put(key, value);
			}
		}
	}

	public void updateValues() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : this.configurationMap.entrySet()) {
			if (sb.length() > 0) {
				sb.append("|");
			}
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(StringUtils.replace(entry.getValue(), "|", " "));
		}
		this.configurations = sb.toString();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@JsonIgnore
	public String getConfigurations() {
		return this.configurations;
	}

	public void setConfigurations(String configurations) {
		this.configurations = configurations;
	}

	@JsonAnyGetter
	public Map<String, String> getConfigurationMap() {
		return this.configurationMap;
	}

	public void setConfigurationMap(Map<String, String> configurationMap) {
		this.configurationMap = configurationMap;
	}

	@JsonAnySetter
	public void setConfigurationMapEntry(String name, String value) {
		this.configurationMap.put(name, value);
	}

	public String getValue(String key) {
		return this.configurationMap.get(key);
	}
}
