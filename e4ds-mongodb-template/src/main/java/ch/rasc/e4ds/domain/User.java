package ch.rasc.e4ds.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;

@Document
public class User {

	@Id
	private String id;

	@Indexed(unique = true)
	private String userName;

	private String name;

	private String firstName;

	private String email;

	private String passwordHash;

	private String locale;

	private boolean enabled;

	private Set<String> roles;

	public void update(User modifiedUser, boolean personalOptionsUpdate) {
		if (!personalOptionsUpdate) {
			this.userName = modifiedUser.getUserName();
			this.enabled = modifiedUser.isEnabled();

			if (modifiedUser.getRoles() != null) {
				this.roles = Sets.newHashSet(modifiedUser.getRoles());
			} else {
				this.roles = Sets.newHashSet();
			}
		}

		this.name = modifiedUser.getName();
		this.firstName = modifiedUser.getFirstName();
		this.email = modifiedUser.getEmail();
		this.locale = modifiedUser.getLocale();

		if (StringUtils.hasText(modifiedUser.getPasswordHash())) {
			this.passwordHash = modifiedUser.getPasswordHash();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
