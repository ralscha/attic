package ch.rasc.sqrl;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.EnumSet;

import ch.rasc.sqrl.auth.SqrlIdentity;
import ch.rasc.sqrl.auth.SqrlIdentityService;
import ch.rasc.sqrl.cache.NutCache;
import ch.rasc.sqrl.cache.NutCacheEntry;
import ch.rasc.sqrl.nut.NutGenerator;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

public class SqrlManager {

	private final static String SERVER_VERSION = "1";

	private final Signature signature;

	private final String host;
	
	private final String sqrlPath;

	private final NutCache nutCache;

	private final NutGenerator nutGenerator;

	private final SqrlIdentityService sqrlIdentityManager;

	public SqrlManager(String appURL, String sqrlPath,
			SqrlIdentityService sqrlIdentityManager, NutCache nutCache,
			NutGenerator nutGenerator) throws NoSuchAlgorithmException {
		this.host = appURL;
		this.sqrlPath = sqrlPath;
		this.sqrlIdentityManager = sqrlIdentityManager;
		this.nutCache = nutCache;
		this.nutGenerator = nutGenerator;
		this.signature = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
	}

	public NutCacheEntry createAndSaveNut(String ipAddress) {
		NutCacheEntry entry = new NutCacheEntry();
		entry.setIpAddress(ipAddress);
		String nut = this.nutGenerator.generateNut();
		entry.setNut(nut);
		entry.setPollingNut(this.nutGenerator.generateNut());
		
		entry.setLastResponse(Base64.getUrlEncoder().withoutPadding()
				.encodeToString(("sqrl://" + this.host + this.sqrlPath + "?nut=" + nut).getBytes(StandardCharsets.UTF_8)));

		this.nutCache.save(nut, entry);

		return entry;
	}

	public NutCacheEntry poll(String nut) {
		return this.nutCache.getAndDelete(nut);
	}

	public SqrlResponse handleRequest(SqrlRequest sqrlRequest) {
		String nut = sqrlRequest.getNut();		
		String nextQuery = this.sqrlPath + "?nut=" + (nut != null ? nut : "");

		if (nut == null || nut.isBlank()) {
			return new SqrlResponse(new SqrlServerParameters(
					SERVER_VERSION, "", nextQuery,
					SqrlTransactionInformationFlag.CLIENT_FAILURE));
		}

		NutCacheEntry oldNutCacheEntry = this.nutCache.getAndDelete(nut);
		if (oldNutCacheEntry == null) {
			return new SqrlResponse(new SqrlServerParameters(
					SERVER_VERSION, nut, nextQuery,
					SqrlTransactionInformationFlag.CLIENT_FAILURE));
		}

		NutCacheEntry newNutCacheEntry = new NutCacheEntry();
		newNutCacheEntry.setIpAddress(oldNutCacheEntry.getIpAddress());
		String newNut = this.nutGenerator.generateNut();
		newNutCacheEntry.setNut(newNut);
		newNutCacheEntry.setPollingNut(oldNutCacheEntry.getPollingNut());
		this.nutCache.save(newNut, newNutCacheEntry);

		nextQuery = this.sqrlPath + "?nut=" + newNut;

		SqrlClientParameters clientParameters;
		try {
			clientParameters = new SqrlClientParameters(sqrlRequest.getClient());
		}
		catch (SqrlCommandNotSupportedException e) {
			SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
					SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
					SqrlTransactionInformationFlag.CLIENT_FAILURE);
			String sqrlServerParametersEncoded = sqrlServerParameters.encode();
			newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
			return new SqrlResponse(sqrlServerParameters, sqrlServerParametersEncoded);
		}
		catch (SqrlTransactionOptionNotSupportedException e) {
			SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
					SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
					SqrlTransactionInformationFlag.CLIENT_FAILURE);
			String sqrlServerParametersEncoded = sqrlServerParameters.encode();
			newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
			return new SqrlResponse(sqrlServerParameters, sqrlServerParametersEncoded);
		}
				
		if (!sqrlRequest.getServer().equals(oldNutCacheEntry.getLastResponse())) {
			SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
					SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
					SqrlTransactionInformationFlag.COMMAND_FAILED);
			String sqrlServerParametersEncoded = sqrlServerParameters.encode();
			newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
			return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
		}

		EnumSet<SqrlTransactionInformationFlag> transactionInformationFlags = EnumSet
				.noneOf(SqrlTransactionInformationFlag.class);

		// validate the IP if required
		if (!clientParameters.getTransactionOptions()
				.contains(SqrlTransactionOption.NO_IP_TEST)) {
			if (oldNutCacheEntry.getIpAddress().equals(sqrlRequest.getIpAddress())) {
				transactionInformationFlags
						.add(SqrlTransactionInformationFlag.IP_MATCHED);
			}
			else {
				SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
						SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
						SqrlTransactionInformationFlag.COMMAND_FAILED);
				String sqrlServerParametersEncoded = sqrlServerParameters.encode();
				newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
				return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
			}
		}

		byte[] signatureData = (sqrlRequest.getClient() + sqrlRequest.getServer()).getBytes(StandardCharsets.UTF_8);

		String previousSqrlIdentityKey = null;

		if (!isSignatureValid(clientParameters.getIdentityKey(), signatureData,
				sqrlRequest.getIdentitySignature())) {
			SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
					SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
					SqrlTransactionInformationFlag.CLIENT_FAILURE);
			String sqrlServerParametersEncoded = sqrlServerParameters.encode();
			newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
			return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
		}

		SqrlIdentity sqrlIdentity = this.sqrlIdentityManager
				.find(clientParameters.getIdentityKey());
		if (sqrlIdentity == null && clientParameters.getPreviousIdentityKey() != null) {

			if (!isSignatureValid(clientParameters.getPreviousIdentityKey(),
					signatureData, sqrlRequest.getPreviousIdentitySignature())) {
				SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
						SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
						SqrlTransactionInformationFlag.CLIENT_FAILURE);
				String sqrlServerParametersEncoded = sqrlServerParameters.encode();
				newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
				return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
			}

			sqrlIdentity = this.sqrlIdentityManager
					.find(clientParameters.getPreviousIdentityKey());
			if (sqrlIdentity != null) {
				previousSqrlIdentityKey = clientParameters.getPreviousIdentityKey();
			}
			else {
				SqrlIdentity prev = this.sqrlIdentityManager
						.findInPreviousIdentityKeys(clientParameters.getIdentityKey());
				if (prev != null) {
					if (clientParameters.getCommand() == SqrlCommand.QUERY) {
						SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
								SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
								SqrlTransactionInformationFlag.IDENTITY_SUPERSEDED);
						String sqrlServerParametersEncoded = sqrlServerParameters.encode();
						newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
						return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
					}
					SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
							SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
							SqrlTransactionInformationFlag.COMMAND_FAILED,
							SqrlTransactionInformationFlag.IDENTITY_SUPERSEDED);
					String sqrlServerParametersEncoded = sqrlServerParameters.encode();
					newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
					return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
				}
			}
		}

		if (sqrlIdentity != null) {
			String value = oldNutCacheEntry.getIdentityKey();
			if (value != null && !value.isBlank() && !sqrlIdentity.getIdentityKey()
					.equals(oldNutCacheEntry.getIdentityKey())) {

				SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
						SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
						SqrlTransactionInformationFlag.CLIENT_FAILURE,
						SqrlTransactionInformationFlag.BAD_ID_ASSOCIATION);
				String sqrlServerParametersEncoded = sqrlServerParameters.encode();
				newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
				return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
			}

			newNutCacheEntry.setIdentityKey(sqrlIdentity.getIdentityKey());

			if (sqrlIdentity.isDisabled()) {

				SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
						SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
						SqrlTransactionInformationFlag.SQRL_DISABLED);
				String sqrlServerParametersEncoded = sqrlServerParameters.encode();
				newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
				return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
			}
		}

		if (clientParameters.getCommand() == SqrlCommand.IDENT) {
			if (sqrlIdentity == null) {
				this.sqrlIdentityManager.newIdentity(clientParameters.getIdentityKey(),
						clientParameters.getServerUnlockKey(),
						clientParameters.getVerifyUnlockKey());
			}
			else if (previousSqrlIdentityKey != null) {
				if (!isSignatureValid(sqrlIdentity.getVerifyUnlockKey(), signatureData,
						sqrlRequest.getUnlockRequestSignature())) {
					SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
							SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
							SqrlTransactionInformationFlag.CLIENT_FAILURE);
					String sqrlServerParametersEncoded = sqrlServerParameters.encode();
					newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
					return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
				}
				this.sqrlIdentityManager.swap(previousSqrlIdentityKey,
						clientParameters.getIdentityKey());
			}
		}
		else if (clientParameters.getCommand() == SqrlCommand.DISABLE
				|| clientParameters.getCommand() == SqrlCommand.ENABLE
				|| clientParameters.getCommand() == SqrlCommand.REMOVE) {

			if (sqrlIdentity == null) {
				SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
						SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
						SqrlTransactionInformationFlag.COMMAND_FAILED);
				String sqrlServerParametersEncoded = sqrlServerParameters.encode();
				newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
				return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
			}

			if (clientParameters.getCommand() != SqrlCommand.DISABLE) {
				if (!isSignatureValid(sqrlIdentity.getVerifyUnlockKey(), signatureData,
						sqrlRequest.getUnlockRequestSignature())) {
					SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
							SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
							SqrlTransactionInformationFlag.CLIENT_FAILURE);
					String sqrlServerParametersEncoded = sqrlServerParameters.encode();
					newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
					return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
				}
			}

			if (clientParameters.getCommand() == SqrlCommand.DISABLE) {
				this.sqrlIdentityManager.disable(sqrlIdentity.getIdentityKey());
			}
			else if (clientParameters.getCommand() == SqrlCommand.ENABLE) {
				this.sqrlIdentityManager.enable(sqrlIdentity.getIdentityKey());
			}
			else if (clientParameters.getCommand() == SqrlCommand.REMOVE) {
				this.sqrlIdentityManager.remove(sqrlIdentity.getIdentityKey());
			}
		}

		SqrlServerParameters sqrlServerParameters = new SqrlServerParameters(
				SERVER_VERSION, newNutCacheEntry.getNut(), nextQuery,
				transactionInformationFlags.toArray(
						new SqrlTransactionInformationFlag[transactionInformationFlags
								.size()]));

		if (clientParameters.getTransactionOptions()
				.contains(SqrlTransactionOption.SUK)) {
			if (sqrlIdentity != null) {
				sqrlServerParameters
						.setServerUnlockKey(sqrlIdentity.getServerUnlockKey());
			}
			else if (clientParameters.getCommand() == SqrlCommand.IDENT) {
				sqrlServerParameters
						.setServerUnlockKey(clientParameters.getServerUnlockKey());
			}
		}

		if (clientParameters.getTransactionOptions()
				.contains(SqrlTransactionOption.CPS)) {
			sqrlServerParameters.setAuthenticationRedirectionURL(this.host);
		}

		String sqrlServerParametersEncoded = sqrlServerParameters.encode();
		newNutCacheEntry.setLastResponse(sqrlServerParametersEncoded);
		if (clientParameters.getCommand() == SqrlCommand.IDENT) {
			return new SqrlResponse(newNutCacheEntry.getPollingNut(), clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
		} 
		
		return new SqrlResponse(clientParameters, sqrlServerParameters, sqrlServerParametersEncoded);
		
	}

	private boolean isSignatureValid(String publicKeyString, byte[] data,
			String signatureString) {

		if (publicKeyString == null || publicKeyString.isBlank()
				|| signatureString == null || signatureString.isBlank()) {
			return false;
		}

		try {
			byte[] publicKeyBytes = Base64.getUrlDecoder().decode(publicKeyString);
			byte[] signatureBytes = Base64.getUrlDecoder().decode(signatureString);

			final EdDSAParameterSpec edDsaSpec = EdDSANamedCurveTable
					.getByName(EdDSANamedCurveTable.ED_25519);

			final PublicKey publicKey = new EdDSAPublicKey(
					new EdDSAPublicKeySpec(publicKeyBytes, edDsaSpec));
			this.signature.initVerify(publicKey);

			this.signature.update(data);
			return this.signature.verify(signatureBytes);
		}
		catch (InvalidKeyException | SignatureException e) {
			return false;
		}
	}
}
