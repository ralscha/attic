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
public class Field {

	private int id;
	private String name;
	private Set<FieldTextres> fieldTextres;
	
	public Field() {
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

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany (cascade = CascadeType.ALL, mappedBy = "id.fieldId")
	public Set<FieldTextres> getFieldTextres() {
		return fieldTextres;
	}
	
	public void setFieldTextres(Set<FieldTextres> fieldTextres) {
		this.fieldTextres = fieldTextres;
	}
	
}
