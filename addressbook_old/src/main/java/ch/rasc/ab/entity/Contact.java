package ch.rasc.ab.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Collections2;

@Entity
@Model(value = "Addressbook.model.Contact", readMethod = "contactService.read",
		createMethod = "contactService.create", updateMethod = "contactService.update",
		destroyMethod = "contactService.destroy", paging = true)
public class Contact extends AbstractPersistable {

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ContactContactGroup",
			joinColumns = @JoinColumn(name = "contactId") ,
			inverseJoinColumns = @JoinColumn(name = "contactGroupId") )
	private Set<ContactGroup> contactGroups = new HashSet<>();

	@ModelField
	@Transient
	private Collection<Long> contactGroupIds;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "contact", orphanRemoval = true)
	@JsonIgnore
	private Set<ContactInfo> contactInfos = new HashSet<>();

	@Size(max = 255)
	private String firstName;

	@Size(max = 255)
	private String lastName;

	@ModelField(dateFormat = "c")
	private Date dateOfBirth;

	@Lob
	private String notes;

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Set<ContactInfo> getContactInfos() {
		return this.contactInfos;
	}

	public void setContactInfos(Set<ContactInfo> contactInfos) {
		this.contactInfos = contactInfos;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Set<ContactGroup> getContactGroups() {
		return this.contactGroups;
	}

	public void setContactGroups(Set<ContactGroup> contactGroups) {
		this.contactGroups = contactGroups;
	}

	public Collection<Long> getContactGroupIds() {
		return this.contactGroupIds;
	}

	public void setContactGroupIds(Collection<Long> contactGroupIds) {
		this.contactGroupIds = contactGroupIds;
	}

	@PostLoad
	@PostPersist
	@PostUpdate
	private void populate() {
		this.contactGroupIds = Collections2.transform(this.contactGroups,
				input -> input.getId());
	}
}
