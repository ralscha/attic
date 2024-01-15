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
public class TextResource {

	private int id;
	private String text;
	private Set<FieldTextres> fieldTextres;

	public TextResource() {
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

	@Column(nullable = false)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "textresId")
	public Set<FieldTextres> getFieldTextres() {
		return fieldTextres;
	}

	public void setFieldTextres(Set<FieldTextres> fieldTextres) {
		this.fieldTextres = fieldTextres;
	}

}
