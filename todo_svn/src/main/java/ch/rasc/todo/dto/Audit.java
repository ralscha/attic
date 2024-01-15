package ch.rasc.todo.dto;

public class Audit {
	private long timestamp;
	private AuditType type;
	private Long todoId;
	private String property;
	private Object left;
	private Object right;

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public AuditType getType() {
		return this.type;
	}

	public void setType(AuditType type) {
		this.type = type;
	}

	public Long getTodoId() {
		return this.todoId;
	}

	public void setTodoId(Long todoId) {
		this.todoId = todoId;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getLeft() {
		return this.left;
	}

	public void setLeft(Object left) {
		this.left = left;
	}

	public Object getRight() {
		return this.right;
	}

	public void setRight(Object right) {
		this.right = right;
	}

}
