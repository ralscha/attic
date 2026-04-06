package ch.rasc.nosql.rethink;

import java.util.List;

public class InsertResponse {

	private Long deleted;
	private Long inserted;
	private Long unchanged;
	private Long replaced;
	private Long errors;
	private Long skipped;

	private List<String> generated_keys;

	public Long getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public Long getInserted() {
		return this.inserted;
	}

	public void setInserted(Long inserted) {
		this.inserted = inserted;
	}

	public Long getUnchanged() {
		return this.unchanged;
	}

	public void setUnchanged(Long unchanged) {
		this.unchanged = unchanged;
	}

	public Long getReplaced() {
		return this.replaced;
	}

	public void setReplaced(Long replaced) {
		this.replaced = replaced;
	}

	public Long getErrors() {
		return this.errors;
	}

	public void setErrors(Long errors) {
		this.errors = errors;
	}

	public Long getSkipped() {
		return this.skipped;
	}

	public void setSkipped(Long skipped) {
		this.skipped = skipped;
	}

	public List<String> getGenerated_keys() {
		return this.generated_keys;
	}

	public void setGenerated_keys(List<String> generated_keys) {
		this.generated_keys = generated_keys;
	}

	@Override
	public String toString() {
		return "InsertResponse [deleted=" + this.deleted + ", inserted=" + this.inserted
				+ ", unchanged=" + this.unchanged + ", replaced=" + this.replaced
				+ ", errors=" + this.errors + ", skipped=" + this.skipped
				+ ", generated_keys=" + this.generated_keys + "]";
	}

}
