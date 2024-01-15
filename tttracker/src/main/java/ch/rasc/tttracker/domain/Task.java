package ch.rasc.tttracker.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelType;

@Entity
@Model(value = "TTT.model.Task")
@ModelField(value = "id", type = ModelType.INTEGER)
public class Task extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 200)
	private String name;

	@ManyToOne(optional = false)
	private Project project;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
