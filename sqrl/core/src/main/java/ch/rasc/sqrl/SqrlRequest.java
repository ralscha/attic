package ch.rasc.sqrl;

public class SqrlRequest {

	private final String ipAddress;
	private final String nut;
	private final String client;
	private final String server;
	private final String identitySignature;
	private final String previousIdentitySignature;
	private final String unlockRequestSignature;

	public SqrlRequest(String ipAddress, String nut, String client, String server,
			String identitySignature, String previousIdentitySignature,
			String unlockRequestSignature) {
		this.ipAddress = ipAddress;
		this.nut = nut;
		this.client = client;
		this.server = server;
		this.identitySignature = identitySignature;
		this.previousIdentitySignature = previousIdentitySignature;
		this.unlockRequestSignature = unlockRequestSignature;
	}


	public String getIpAddress() {
		return this.ipAddress;
	}


	public String getNut() {
		return this.nut;
	}


	public String getClient() {
		return this.client;
	}


	public String getServer() {
		return this.server;
	}


	public String getIdentitySignature() {
		return this.identitySignature;
	}


	public String getPreviousIdentitySignature() {
		return this.previousIdentitySignature;
	}


	public String getUnlockRequestSignature() {
		return this.unlockRequestSignature;
	}

}
