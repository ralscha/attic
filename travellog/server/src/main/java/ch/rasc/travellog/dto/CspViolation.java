package ch.rasc.travellog.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CspViolation {

	@JsonProperty("disposition")
	private String disposition;

	@JsonProperty("referrer")
	private String referrer;

	@JsonProperty("document-uri")
	private String documentUri;

	@JsonProperty("violated-directive")
	private String violatedDirective;

	@JsonProperty("effective-directive")
	private String effectiveDirective;

	@JsonProperty("original-policy")
	private String originalPolicy;

	@JsonProperty("blocked-uri")
	private String blockedUri;

	@JsonProperty("line-number")
	private Integer lineNumber;

	@JsonProperty("column-number")
	private Integer columnNumber;

	@JsonProperty("status-code")
	private Integer statusCode;

	@JsonProperty("source-file")
	private String sourceFile;

	@JsonProperty("script-sample")
	private String scriptSample;

	private final Map<String, Object> unrecognizedFields = new HashMap<>();

	public Map<String, Object> getUnrecognizedFields() {
		return this.unrecognizedFields;
	}

	@JsonAnySetter
	public void setUnrecognizedFields(String key, Object value) {
		this.unrecognizedFields.put(key, value);
	}

	public String getDocumentUri() {
		return this.documentUri;
	}

	public void setDocumentUri(String documentUri) {
		this.documentUri = documentUri;
	}

	public String getViolatedDirective() {
		return this.violatedDirective;
	}

	public void setViolatedDirective(String violatedDirective) {
		this.violatedDirective = violatedDirective;
	}

	public String getEffectiveDirective() {
		return this.effectiveDirective;
	}

	public void setEffectiveDirective(String effectiveDirective) {
		this.effectiveDirective = effectiveDirective;
	}

	public String getOriginalPolicy() {
		return this.originalPolicy;
	}

	public void setOriginalPolicy(String originalPolicy) {
		this.originalPolicy = originalPolicy;
	}

	public String getBlockedUri() {
		return this.blockedUri;
	}

	public void setBlockedUri(String blockedUri) {
		this.blockedUri = blockedUri;
	}

	public Integer getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Integer getColumnNumber() {
		return this.columnNumber;
	}

	public void setColumnNumber(Integer columnNumber) {
		this.columnNumber = columnNumber;
	}

	public Integer getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getSourceFile() {
		return this.sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getScriptSample() {
		return this.scriptSample;
	}

	public void setScriptSample(String scriptSample) {
		this.scriptSample = scriptSample;
	}

	public String getDisposition() {
		return this.disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public String getReferrer() {
		return this.referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	@Override
	public String toString() {
		return "CspViolation [disposition=" + this.disposition + ", referrer="
				+ this.referrer + ", documentUri=" + this.documentUri
				+ ", violatedDirective=" + this.violatedDirective
				+ ", effectiveDirective=" + this.effectiveDirective + ", originalPolicy="
				+ this.originalPolicy + ", blockedUri=" + this.blockedUri
				+ ", lineNumber=" + this.lineNumber + ", columnNumber="
				+ this.columnNumber + ", statusCode=" + this.statusCode + ", sourceFile="
				+ this.sourceFile + ", scriptSample=" + this.scriptSample + "]";
	}

}
