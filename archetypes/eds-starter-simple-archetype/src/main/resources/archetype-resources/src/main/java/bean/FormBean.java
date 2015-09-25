#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import java.util.Date;

public class FormBean {

	private String osName;

	private String osVersion;

	private int availableProcessors;

	private String remarks;

	private Date date;

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public int getAvailableProcessors() {
		return availableProcessors;
	}

	public void setAvailableProcessors(int availableProcessors) {
		this.availableProcessors = availableProcessors;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "FormBean [osName=" + osName + ", osVersion=" + osVersion
				+ ", availableProcessors=" + availableProcessors + ", remarks=" + remarks
				+ ", date=" + date + "]";
	}

}
