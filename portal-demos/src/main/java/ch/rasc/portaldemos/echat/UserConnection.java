package ch.rasc.portaldemos.echat;

public class UserConnection {
	private String username;

	private String browser;

	private String image;

	private boolean supportsWebRTC;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isSupportsWebRTC() {
		return supportsWebRTC;
	}

	public void setSupportsWebRTC(boolean supportsWebRTC) {
		this.supportsWebRTC = supportsWebRTC;
	}

}
