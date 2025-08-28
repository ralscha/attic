package ch.rasc.travellog.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CspReport {

	@JsonProperty("csp-report")
	private CspViolation cspReport;

	public CspViolation getCspReport() {
		return this.cspReport;
	}

	public void setCspReport(CspViolation cspReport) {
		this.cspReport = cspReport;
	}

	private final Map<String, Object> unrecognizedFields = new HashMap<>();

	public Map<String, Object> getUnrecognizedFields() {
		return this.unrecognizedFields;
	}

	@JsonAnySetter
	public void setUnrecognizedFields(String key, Object value) {
		this.unrecognizedFields.put(key, value);
	}

	@Override
	public String toString() {
		return "CspReport [cspReport=" + this.cspReport + ", unrecognizedFields="
				+ this.unrecognizedFields + "]";
	}

}
