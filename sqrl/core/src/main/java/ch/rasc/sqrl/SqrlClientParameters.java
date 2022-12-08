package ch.rasc.sqrl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SqrlClientParameters {
	private final String version;
	private final SqrlCommand command;
	private final Set<SqrlTransactionOption> transactionOptions;
	private final String button;

	// keys
	private final String identityKey;
	private final String previousIdentityKey;
	private final String serverUnlockKey;
	private final String verifyUnlockKey;

	// secrets
	private final String indexSecret;
	private final String previousIndexSecret;

	public SqrlClientParameters(String client) throws SqrlCommandNotSupportedException,
			SqrlTransactionOptionNotSupportedException {

		String decodedClient = new String(Base64.getUrlDecoder().decode(client),
				StandardCharsets.UTF_8);

		String[] splitted = decodedClient.split("\r\n");
		Map<String, String> clientMap = new HashMap<>();
		for (String keyValue : splitted) {
			int pos = keyValue.indexOf('=');
			if (pos > 0) {
				clientMap.put(keyValue.substring(0, pos), keyValue.substring(pos + 1));
			}
		}

		this.version = clientMap.get("ver");
		this.command = SqrlCommand.fromExternalValue(clientMap.get("cmd"));

		if (this.command == null) {
			throw new SqrlCommandNotSupportedException(
					"SQRL Command '" + clientMap.get("cmd") + "' not supported");
		}

		this.identityKey = clientMap.get("idk");

		String transactionOptionsString = clientMap.get("opt");
		if (transactionOptionsString != null && !transactionOptionsString.isBlank()) {
			String[] splittedOptions = transactionOptionsString.split("~");
			Set<SqrlTransactionOption> opts = new HashSet<>();
			for (String option : splittedOptions) {
				SqrlTransactionOption sqrlTransactionOption = SqrlTransactionOption
						.fromExternalValue(option);
				if (sqrlTransactionOption == null) {
					throw new SqrlTransactionOptionNotSupportedException(
							"SQRL Transaction option '" + option + "' not supported");
				}
				opts.add(sqrlTransactionOption);
			}
			this.transactionOptions = Set.copyOf(opts);
		}
		else {
			this.transactionOptions = Set.of();
		}

		this.button = clientMap.get("btn");
		this.previousIdentityKey = clientMap.get("pidk");

		this.indexSecret = clientMap.get("ins");
		this.previousIndexSecret = clientMap.get("pins");

		this.serverUnlockKey = clientMap.get("suk");
		this.verifyUnlockKey = clientMap.get("vuk");
	}

	public String getVersion() {
		return this.version;
	}

	public SqrlCommand getCommand() {
		return this.command;
	}

	public Set<SqrlTransactionOption> getTransactionOptions() {
		return this.transactionOptions;
	}

	public String getButton() {
		return this.button;
	}

	public String getIdentityKey() {
		return this.identityKey;
	}

	public String getPreviousIdentityKey() {
		return this.previousIdentityKey;
	}

	public String getServerUnlockKey() {
		return this.serverUnlockKey;
	}

	public String getVerifyUnlockKey() {
		return this.verifyUnlockKey;
	}

	public String getIndexSecret() {
		return this.indexSecret;
	}

	public String getPreviousIndexSecret() {
		return this.previousIndexSecret;
	}

	@Override
	public String toString() {
		return "SqrlClientParameters [version=" + this.version + ", command=" + this.command
				+ ", transactionOptions=" + this.transactionOptions + ", button=" + this.button
				+ ", identityKey=" + this.identityKey + ", previousIdentityKey="
				+ this.previousIdentityKey + ", serverUnlockKey=" + this.serverUnlockKey
				+ ", verifyUnlockKey=" + this.verifyUnlockKey + ", indexSecret=" + this.indexSecret
				+ ", previousIndexSecret=" + this.previousIndexSecret + "]";
	}

}
