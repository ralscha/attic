package ch.rasc.upi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AhvvnResponse {
	private Long latestAhvvn;

	private Short refusedReason;
	private String refusedDetailedReason;

	public Long getLatestAhvvn() {
		return this.latestAhvvn;
	}

	public void setLatestAhvvn(Long latestAhvvn) {
		this.latestAhvvn = latestAhvvn;
	}

	public Short getRefusedReason() {
		return this.refusedReason;
	}

	public void setRefusedReason(Short refusedReason) {
		this.refusedReason = refusedReason;
	}

	public String getRefusedDetailedReason() {
		return this.refusedDetailedReason;
	}

	public void setRefusedDetailedReason(String refusedDetailedReason) {
		this.refusedDetailedReason = refusedDetailedReason;
	}

}
