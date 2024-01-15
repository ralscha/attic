package ch.rasc.todo.dto;

import java.util.Date;

import ch.rasc.todo.entity.Type;

public class MiniTodo {
	private long id;
	private Type type;
	private Date due;
	private String title;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getDue() {
		return this.due;
	}

	public void setDue(Date due) {
		this.due = due;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
