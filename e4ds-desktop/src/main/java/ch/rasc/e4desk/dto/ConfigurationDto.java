package ch.rasc.e4desk.dto;

public class ConfigurationDto {

	private String logLevel;

	private String sender;

	private String server;

	private int port;

	private String username;

	private String password;

	public String getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
