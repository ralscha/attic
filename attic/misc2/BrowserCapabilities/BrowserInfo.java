

public class BrowserInfo {


	private String userAgent;
	private boolean isJavascriptOn;
	private boolean isCookiesOn;
	private String javascriptVersion;
	private int screenWidth;
	private int screenHeight;
	
	
	public BrowserInfo(String userAgent) {
		this.userAgent = userAgent;
		this.isJavascriptOn = false;
		this.isCookiesOn = false;
		this.javascriptVersion = null;
		this.screenWidth = -1;
		this.screenHeight = -1;
	}

	public String getUserAgent() {
		return userAgent;
	}
	
	public void setJavascriptOn(boolean state) {
		this.isJavascriptOn = state;		
	}
	
	public boolean isJavascriptOn() {
		return isJavascriptOn;
	}

	public void setCookiesOn(boolean state) {
		this.isCookiesOn = state;
	}	
	
	public boolean isCookiesOn() {
		return isCookiesOn;
	}
	
	public void setScreenWidth(int width) {
		this.screenWidth = width;
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}
	
	public void setScreenHeight(int height) {
		this.screenHeight = height;
	}
	
	public int getScreenHeight() {
		return screenHeight;
	}
	
	public void setJavascriptVersion(String version) {
		this.javascriptVersion = version;
	}
	
	public String getJavascriptVersion() {
		return javascriptVersion;
	}
}