package ch.rasc.todo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app")
@Component
public class AppConfig {
	private String imagepath;
	private String smbuser;
	private String smbpwd;

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getSmbuser() {
		return smbuser;
	}

	public void setSmbuser(String smbuser) {
		this.smbuser = smbuser;
	}

	public String getSmbpwd() {
		return smbpwd;
	}

	public void setSmbpwd(String smbpwd) {
		this.smbpwd = smbpwd;
	}

}
