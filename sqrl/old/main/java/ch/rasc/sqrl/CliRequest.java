package ch.rasc.sqrl;

import java.util.Set;

import ch.rasc.sqrl.auth.SqrlIdentity;

public class CliRequest {

	private ClientBody client;

	private String clientEncoded;

	private String server;

	private String ids;

	private String pids;

	private String urs;

	public ClientBody getClient() {
		return this.client;
	}

	public void setClient(ClientBody client) {
		this.client = client;
	}

	public String getClientEncoded() {
		return this.clientEncoded;
	}

	public void setClientEncoded(String clientEncoded) {
		this.clientEncoded = clientEncoded;
	}

	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getIds() {
		return this.ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getPids() {
		return this.pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public String getUrs() {
		return this.urs;
	}

	public void setUrs(String urs) {
		this.urs = urs;
	}

	public boolean isAuthCommand() {
		return this.getClient().getCmd().contains("ident")
				|| this.getClient().getCmd().contains("enable");
	}

	public boolean updateIdentity(SqrlIdentity identity) {
		Set<String> opts = getClient().getOpt();
		boolean sqrlOnly = opts.contains("sqrlonly");
		boolean hardlock = opts.contains("hardlock");

		boolean changed = false;

		if (identity.isSqrlOnly() != sqrlOnly) {
			identity.setSqrlOnly(sqrlOnly);
			changed = true;
		}

		if (identity.isHardlock() != hardlock) {
			identity.setHardlock(hardlock);
			changed = true;
		}

		return changed;
	}
	
}
