package ch.rasc.sqrl.cache;

public class NutCacheEntry {

	private String ipAddress;

	private String nut;

	private String pollingNut;

	private String identityKey;

	private String lastResponse;

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getNut() {
		return this.nut;
	}

	public void setNut(String nut) {
		this.nut = nut;
	}

	public String getPollingNut() {
		return this.pollingNut;
	}

	public void setPollingNut(String pollingNut) {
		this.pollingNut = pollingNut;
	}

	public String getLastResponse() {
		return this.lastResponse;
	}

	public void setLastResponse(String lastResponse) {
		this.lastResponse = lastResponse;
	}

	public String getIdentityKey() {
		return this.identityKey;
	}

	public void setIdentityKey(String identityKey) {
		this.identityKey = identityKey;
	}

}
