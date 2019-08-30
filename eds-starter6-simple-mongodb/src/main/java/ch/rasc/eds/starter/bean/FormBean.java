package ch.rasc.eds.starter.bean;

import java.util.Date;

public class FormBean {

	private String osName;

	private String osVersion;

	private int availableProcessors;

	private String remarks;

	private Date date;

	public String getOsName() {
		return this.osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsVersion() {
		return this.osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public int getAvailableProcessors() {
		return this.availableProcessors;
	}

	public void setAvailableProcessors(int availableProcessors) {
		this.availableProcessors = availableProcessors;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "FormBean [osName=" + this.osName + ", osVersion=" + this.osVersion
				+ ", availableProcessors=" + this.availableProcessors + ", remarks="
				+ this.remarks + ", date=" + this.date + "]";
	}

}
