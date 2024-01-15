package test3;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Language {

	private int id;
	private String name;
	private int priority;
	private String iso;
	private boolean active;
	private Set<FieldTextres> fieldTextres;

	public Language() {
		fieldTextres = new HashSet<FieldTextres>();
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Column(length = 2, nullable = false, unique = true)
	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	@Column(length = 20, nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id.langId")
	public Set<FieldTextres> getFieldTextres() {
		return fieldTextres;
	}

	public void setFieldTextres(Set<FieldTextres> fieldTextres) {
		this.fieldTextres = fieldTextres;
	}

}
