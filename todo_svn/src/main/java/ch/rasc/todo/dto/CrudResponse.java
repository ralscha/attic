package ch.rasc.todo.dto;

import java.util.Collections;
import java.util.List;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CrudResponse<T> {
	private Long total;
	private List<T> root;
	private boolean success;
	private String message;
	private List<FieldError> fieldErrors;

	public CrudResponse() {
		// default constructor
	}

	public CrudResponse(T root) {
		this.root = Collections.singletonList(root);
		this.success = true;
	}

	public CrudResponse(long total, List<T> root) {
		this.root = root;
		this.total = total;
		this.success = true;
	}

	public CrudResponse(List<T> root) {
		this.root = root;
		this.total = Long.valueOf(root.size());
		this.success = true;
	}

	public Long getTotal() {
		return this.total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRoot() {
		return this.root;
	}

	public void setRoot(List<T> root) {
		this.root = root;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FieldError> getFieldErrors() {
		return this.fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
