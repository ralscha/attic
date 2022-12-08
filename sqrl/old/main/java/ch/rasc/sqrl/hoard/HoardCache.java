package ch.rasc.sqrl.hoard;

import ch.rasc.sqrl.CliRequest;
import ch.rasc.sqrl.HoardCacheState;
import ch.rasc.sqrl.auth.SqrlIdentity;

public class HoardCache {

	private HoardCacheState state;
	private String remoteIP;
	private String originalNut;
	private String pagNut;
	private CliRequest lastRequest;
	private SqrlIdentity identity;
	private String lastResponse;

	public HoardCacheState getState() {
		return this.state;
	}

	public void setState(HoardCacheState state) {
		this.state = state;
	}

	public String getRemoteIP() {
		return this.remoteIP;
	}

	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}

	public String getOriginalNut() {
		return this.originalNut;
	}

	public void setOriginalNut(String originalNut) {
		this.originalNut = originalNut;
	}

	public String getPagNut() {
		return this.pagNut;
	}

	public void setPagNut(String pagNut) {
		this.pagNut = pagNut;
	}

	public CliRequest getLastRequest() {
		return this.lastRequest;
	}

	public void setLastRequest(CliRequest lastRequest) {
		this.lastRequest = lastRequest;
	}

	public SqrlIdentity getIdentity() {
		return this.identity;
	}

	public void setIdentity(SqrlIdentity identity) {
		this.identity = identity;
	}

	public String getLastResponse() {
		return this.lastResponse;
	}

	public void setLastResponse(String lastResponse) {
		this.lastResponse = lastResponse;
	}

}
