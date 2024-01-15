package ch.rasc.tttracker.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelType;

@Entity
@Model(value = "TTT.model.TaskLog")
@ModelField(value = "id", type = ModelType.INTEGER)
public class TaskLog extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 2000)
	private String taskDescription;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date taskLogDate;

	@NotNull
	private int taskMinutes;

	@ManyToOne(optional = false)
	private User user;

	@ManyToOne(optional = false)
	private Task task;

	public String getTaskDescription() {
		return this.taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public Date getTaskLogDate() {
		return this.taskLogDate;
	}

	public void setTaskLogDate(Date taskLogDate) {
		this.taskLogDate = taskLogDate;
	}

	public int getTaskMinutes() {
		return this.taskMinutes;
	}

	public void setTaskMinutes(int taskMinutes) {
		this.taskMinutes = taskMinutes;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
