package ch.rasc.changelog.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Splitter;

import ch.rasc.changelog.util.DMYDateSerializer;
import ch.rasc.edsutil.entity.AbstractPersistable;

@Entity
public class Customer extends AbstractPersistable {

	private static final long serialVersionUID = 1L;

	@Size(max = 20)
	private String shortName;

	@Size(max = 254)
	private String longName;

	@Size(max = 254)
	private String testUrl;

	@Size(max = 254)
	private String responsible;

	private String features;

	@Size(max = 50)
	private String ftpUser;

	@Enumerated(EnumType.STRING)
	private InstallationType installation;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)
	private Set<CustomerBuild> builds = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)
	private Set<Documents> documents = new HashSet<>();

	@OneToMany(mappedBy = "customer")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<CustomerChange> changes = new HashSet<>();

	@Transient
	private String latestVersionNumber;

	@Transient
	private Date latestVersionDate;

	@Transient
	private Map<String, String> featureMap = new HashMap<>();

	public void update(Customer modifiedCustomer) {
		this.shortName = modifiedCustomer.getShortName();
		this.longName = modifiedCustomer.getLongName();
		this.testUrl = StringUtils.trimToNull(modifiedCustomer.getTestUrl());
		this.ftpUser = StringUtils.trimToNull(modifiedCustomer.getFtpUser());
		this.responsible = StringUtils.trimToNull(modifiedCustomer.getResponsible());
		this.featureMap = modifiedCustomer.getFeatureMap();
		this.installation = modifiedCustomer.getInstallation();
	}

	@PostLoad
	private void postLoad() {
		if (StringUtils.isNotBlank(this.features)) {
			for (String keyValue : Splitter.on("|").split(this.features)) {
				int pos = keyValue.indexOf("=");
				String key = keyValue.substring(0, pos);
				String value = keyValue.substring(pos + 1);
				this.featureMap.put(key, value);
			}
		}
	}

	public void updateFeatures() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : this.featureMap.entrySet()) {
			if (sb.length() > 0) {
				sb.append("|");
			}
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(StringUtils.replace(entry.getValue(), "|", " "));
		}
		this.features = sb.toString();
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return this.longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	@JsonIgnore
	public Set<CustomerBuild> getBuilds() {
		return this.builds;
	}

	public void setBuilds(Set<CustomerBuild> builds) {
		this.builds = builds;
	}

	@JsonIgnore
	public Set<CustomerChange> getChanges() {
		return this.changes;
	}

	public void setChanges(Set<CustomerChange> changes) {
		this.changes = changes;
	}

	@JsonIgnore
	public Set<Documents> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Documents> documents) {
		this.documents = documents;
	}

	public String getLatestVersionNumber() {
		return this.latestVersionNumber;
	}

	public void setLatestVersionNumber(String latestVersionNumber) {
		this.latestVersionNumber = latestVersionNumber;
	}

	@JsonSerialize(using = DMYDateSerializer.class)
	public Date getLatestVersionDate() {
		return this.latestVersionDate;
	}

	public void setLatestVersionDate(Date latestVersionDate) {
		this.latestVersionDate = latestVersionDate;
	}

	public String getTestUrl() {
		return this.testUrl;
	}

	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@JsonIgnore
	public String getFeatures() {
		return this.features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getFtpUser() {
		return this.ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getResponsible() {
		return this.responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	@JsonAnyGetter
	public Map<String, String> getFeatureMap() {
		return this.featureMap;
	}

	public void setFeatureMap(Map<String, String> featureMap) {
		this.featureMap = featureMap;
	}

	@JsonAnySetter
	public void setFeatureMapEntry(String name, String value) {
		this.featureMap.put(name, value);
	}

	public InstallationType getInstallation() {
		return this.installation;
	}

	public void setInstallation(InstallationType installation) {
		this.installation = installation;
	}

}
