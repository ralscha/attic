package ch.rasc.smninfo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app")
@Component
public class AppProperties {

	private String xodusPath;

	private String backupFilename;

	public String getXodusPath() {
		return this.xodusPath;
	}

	public void setXodusPath(String xodusPath) {
		this.xodusPath = xodusPath;
	}

	public String getBackupFilename() {
		return this.backupFilename;
	}

	public void setBackupFilename(String backupFilename) {
		this.backupFilename = backupFilename;
	}

}
