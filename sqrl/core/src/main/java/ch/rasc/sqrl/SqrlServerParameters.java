package ch.rasc.sqrl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SqrlServerParameters {
	// required
	private final String version;
	private final String nut;
	private int transactionInformationFlags;
	private final String nextQuery;

	// optional
	private String authenticationRedirectionURL;
	private String cancellationRedirectionURL;
	private String serverUnlockKey;
	private String secretIndex;
	private SqrlAsk ask;

	public SqrlServerParameters(String version, String nut, String nextQuery,
			SqrlTransactionInformationFlag... transactionInformationFlags) {
		this.version = version;
		this.nut = nut;

		int flagValue = 0;
		if (transactionInformationFlags != null) {
			for (SqrlTransactionInformationFlag flag : transactionInformationFlags) {
				flagValue |= flag.getExternalValue();
			}
		}
		this.transactionInformationFlags = flagValue;

		this.nextQuery = nextQuery;
	}

	public void setTransactionInformationFlag(
			SqrlTransactionInformationFlag clientFailure) {
		this.transactionInformationFlags |= clientFailure.getExternalValue();
	}

	public String getAuthenticationRedirectionURL() {
		return this.authenticationRedirectionURL;
	}

	public void setAuthenticationRedirectionURL(String authenticationRedirectionURL) {
		this.authenticationRedirectionURL = authenticationRedirectionURL;
	}

	public String getCancellationRedirectionURL() {
		return this.cancellationRedirectionURL;
	}

	public void setCancellationRedirectionURL(String cancellationRedirectionURL) {
		this.cancellationRedirectionURL = cancellationRedirectionURL;
	}

	public String getServerUnlockKey() {
		return this.serverUnlockKey;
	}

	public void setServerUnlockKey(String serverUnlockKey) {
		this.serverUnlockKey = serverUnlockKey;
	}

	public String getSecretIndex() {
		return this.secretIndex;
	}

	public void setSecretIndex(String secretIndex) {
		this.secretIndex = secretIndex;
	}

	public SqrlAsk getAsk() {
		return this.ask;
	}

	public void setAsk(SqrlAsk ask) {
		this.ask = ask;
	}

	public String encode() {
		StringBuilder sb = new StringBuilder();

		sb.append("ver=");
		sb.append(this.version);
		sb.append("\r\n");

		sb.append("nut=");
		if (this.nut != null) {
			sb.append(this.nut);
		}
		sb.append("\r\n");

		sb.append("tif=");
		sb.append(this.transactionInformationFlags);
		sb.append("\r\n");

		sb.append("qry=");
		sb.append(this.nextQuery);
		sb.append("\r\n");

		if (this.authenticationRedirectionURL != null
				&& !this.authenticationRedirectionURL.isBlank()) {
			sb.append("url=");
			sb.append(this.authenticationRedirectionURL);
			sb.append("\r\n");
		}

		if (this.cancellationRedirectionURL != null
				&& !this.cancellationRedirectionURL.isBlank()) {
			sb.append("can=");
			sb.append(this.cancellationRedirectionURL);
			sb.append("\r\n");
		}

		if (this.secretIndex != null && !this.secretIndex.isBlank()) {
			sb.append("sin=");
			sb.append(this.secretIndex);
			sb.append("\r\n");
		}

		if (this.serverUnlockKey != null && !this.serverUnlockKey.isBlank()) {
			sb.append("suk=");
			sb.append(this.serverUnlockKey);
			sb.append("\r\n");
		}

		if (this.ask != null) {
			sb.append("ask=");
			sb.append(this.ask.encode());
			sb.append("\r\n");
		}

		return Base64.getUrlEncoder().withoutPadding()
				.encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public String toString() {
		return "SqrlServerParameters [version=" + this.version + ", nut=" + this.nut
				+ ", transactionInformationFlags=" + this.transactionInformationFlags
				+ ", nextQuery=" + this.nextQuery + ", authenticationRedirectionURL="
				+ this.authenticationRedirectionURL + ", cancellationRedirectionURL="
				+ this.cancellationRedirectionURL + ", serverUnlockKey=" + this.serverUnlockKey
				+ ", secretIndex=" + this.secretIndex + ", ask=" + this.ask + "]";
	}

}
