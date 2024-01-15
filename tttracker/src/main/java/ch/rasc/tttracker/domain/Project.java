package ch.rasc.tttracker.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelType;

@Entity
@Model(value = "TTT.model.Project")
@ModelField(value = "id", type = ModelType.INTEGER)
public class Project extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 200)
	private String name;

	@ManyToOne(optional = false)
	private Company company;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
	private List<Task> tasks;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
