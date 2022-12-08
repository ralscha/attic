package ch.rasc.sqrl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import ch.rasc.sqrl.auth.AuthStore;
import ch.rasc.sqrl.auth.SqrlIdentity;
import ch.rasc.sqrl.hoard.Hoard;
import ch.rasc.sqrl.hoard.HoardCache;
import ch.rasc.sqrl.tree.Tree;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;

@Controller
public class SqrlController {
	private final AppProperties appProperties;

	private final Tree tree;

	private final Hoard hoard;

	private final AuthStore authStore;

	private final Authenticator authenticator;

	public SqrlController(AppProperties appProperties, Tree tree, Hoard hoard,
			AuthStore authStore, Authenticator authenticator) {
		this.appProperties = appProperties;
		this.tree = tree;
		this.hoard = hoard;
		this.authStore = authStore;
		this.authenticator = authenticator;
	}

	@GetMapping(path = "/nut.sqrl",
			produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public MultiValueMap<String, Object> nutForm(HttpServletRequest request,
			@RequestHeader(name = "Referer", required = false) String referer) {
		HoardCache hoardCache = createAndSaveNut(request.getRemoteAddr());
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("nut", hoardCache.getOriginalNut());
		body.add("pag", hoardCache.getPagNut());
		body.add("exp", this.appProperties.getNutExpiration().getSeconds());

		if (referer != null && !referer.isBlank()) {
			body.add("can", Base64.getUrlEncoder().withoutPadding()
					.encodeToString(referer.getBytes(StandardCharsets.UTF_8)));
		}
		return body;
	}

	@GetMapping(path = "/nut.sqrl", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public NutResponse nutJson(HttpServletRequest request) {
		HoardCache hoardCache = createAndSaveNut(request.getRemoteAddr());
		return new NutResponse(hoardCache.getOriginalNut(), hoardCache.getPagNut(),
				this.appProperties.getNutExpiration().getSeconds());
	}

	static class NutResponse {
		private final String nut;
		private final String pag;
		private final long exp;

		public NutResponse(String nut, String pag, long exp) {
			this.nut = nut;
			this.pag = pag;
			this.exp = exp;
		}

		public String getNut() {
			return this.nut;
		}

		public String getPag() {
			return this.pag;
		}

		public long getExp() {
			return this.exp;
		}
	}

	private HoardCache createAndSaveNut(String remoteAddr) {
		String nut = this.tree.nut();
		String pagnut = this.tree.nut();

		HoardCache hoardCache = new HoardCache();
		hoardCache.setState(HoardCacheState.ISSUED);
		hoardCache.setRemoteIP(remoteAddr);
		hoardCache.setOriginalNut(nut);
		hoardCache.setPagNut(pagnut);

		this.hoard.save(nut, hoardCache, this.appProperties.getNutExpiration());

		return hoardCache;
	}

	@GetMapping("/png.sqrl")
	public void png(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "nut", required = false) String nut)
			throws IOException, WriterException {

		String requestNut = nut;
		HoardCache hoardCache = null;
		if (nut == null || nut.isBlank()) {
			hoardCache = createAndSaveNut(request.getRemoteAddr());
			requestNut = hoardCache.getOriginalNut();
		}

		String sqrlURL = this.appProperties.getAppUrl().replace("https", "sqrl")
				+ "/cli.sqrl?nut=" + requestNut;

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(sqrlURL, BarcodeFormat.QR_CODE, 256,
				256);

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
		byte[] pngData = pngOutputStream.toByteArray();

		if (hoardCache != null) {
			response.addHeader("Sqrl-Nut", hoardCache.getOriginalNut());
			response.addHeader("Sqrl-Pag", hoardCache.getPagNut());
			response.addHeader("Sqrl-Exp",
					String.valueOf(this.appProperties.getNutExpiration().getSeconds()));
		}
		response.setHeader("Content-Type", "image/png");
		response.getOutputStream().write(pngData);
	}

	@GetMapping("/pag.sqrl")
	public ResponseEntity<String> pag(
			@RequestParam(name = "nut", required = true) String nut,
			@RequestParam(name = "pag", required = true) String pag) {

		HoardCache hoardCache = this.hoard.getAndDelete(pag);
		if (hoardCache == null) {
			return ResponseEntity.notFound().build();
		}

		if (!hoardCache.getOriginalNut().equals(nut)) {
			Application.log.error("Got query for pagnut but original nut doesn't match");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (hoardCache.getIdentity() == null) {
			Application.log.error("Null identity on pag hoardCache");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity
				.ok(this.authenticator.authenticateIdentity(hoardCache.getIdentity()));
	}

	@GetMapping(path = "/pag.sqrl", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagResponse> pagJson(
			@RequestParam(name = "nut", required = true) String nut,
			@RequestParam(name = "pag", required = true) String pag) {

		HoardCache hoardCache = this.hoard.getAndDelete(pag);
		if (hoardCache == null) {
			return ResponseEntity.notFound().build();
		}

		if (!hoardCache.getOriginalNut().equals(nut)) {
			Application.log.error("Got query for pagnut but original nut doesn't match");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (hoardCache.getIdentity() == null) {
			Application.log.error("Nil identity on pag hoardCache");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.ok(new PagResponse(
				this.authenticator.authenticateIdentity(hoardCache.getIdentity())));
	}

	static class PagResponse {
		private final String url;

		public PagResponse(String url) {
			this.url = url;
		}

		public String getUrl() {
			return this.url;
		}
	}

	@PostMapping("/cli.sqrl")
	public void cli(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(name = "nut", required = false) String nut,
			@RequestParam(name = "client", required = false) String client,
			@RequestParam(name = "server", required = false) String server,
			@RequestParam(name = "ids", required = false) String ids,
			@RequestParam(name = "pids", required = false) String pids,
			@RequestParam(name = "urs", required = false) String urs

	) throws IOException {

		if (nut == null || nut.isBlank()) {
			response.getWriter()
					.write(new CliResponse("", "").withClientFailure().encode());
			return;
		}

		// response mutates from here depending on available values
		CliResponse cliResponse = new CliResponse(nut,
				this.appProperties.getAppUrl() + "/cli.sqrl?nut=" + nut);

		CliRequest cliRequest = new CliRequest();
		cliRequest.setClientEncoded(client);
		cliRequest.setServer(server);
		cliRequest.setIds(ids);
		cliRequest.setPids(pids);
		cliRequest.setUrs(urs);

		try {
			cliRequest = parseCliRequest(cliRequest);
			if (cliRequest == null) {
				response.getWriter()
						.write(cliResponse.withClientFailure().withCommandFailed().encode());
				return;
			}
		}
		catch (Exception e) {
			response.getWriter()
				.write(cliResponse.withClientFailure().withCommandFailed().encode());
				return;
		}

		// Signature is OK from here on!

		HoardCache hoardCache = this.hoard.getAndDelete(nut);
		if (hoardCache == null) {
			response.getWriter().write(cliResponse.withClientFailure().withCommandFailed().encode());
			return;
		}

		// validation checks
		if (!requestValidations(hoardCache, cliRequest, request, cliResponse)) {
			return;
		}

		if (cliRequest.getClient().getCmd().equals("query")) {
			SqrlIdentity tmpIdentity = new SqrlIdentity(cliRequest);
			tmpIdentity.setBtn(-1);
			cliResponse.setAsk(this.authenticator.askResponse(tmpIdentity));
		}

		// generate new nut
		String newNut = this.tree.nut();

		// new nut to the response from here on out
		cliResponse.setNut(newNut);
		cliResponse.setQry("/cli.sqrl?nut=" + newNut);

		// check if the same user has already been authenticated previously
		SqrlIdentity identity = this.authStore
				.findIdentity(cliRequest.getClient().getIdk());

		// Check is we know about a previous identity
		SqrlIdentity previousIdentity = checkPreviousIdentity(cliRequest, cliResponse);

		if (identity != null) {
			if (!knownIdentity(cliRequest, cliResponse, identity)) {
				return;
			}
		}
		else if (cliRequest.getClient().getCmd().equals("ident")) {
			// create new identity from the request
			identity = new SqrlIdentity(cliRequest);

			// handle previous identity swap if the current identity is new
			checkPreviousSwap(previousIdentity, identity, cliResponse);
			cliResponse.withIDMatch();
		}

		if (cliRequest.getClient().getOpt().contains("suk")) {
			if (identity != null) {
				cliResponse.setSuk(identity.getSuk());
			}
			else if (cliRequest.getClient().getCmd().equals("ident")) {
				cliResponse.setSuk(cliRequest.getClient().getSuk());
			}
		}

		// Finish authentication and saving
		finishCliResponse(cliRequest, cliResponse, identity, hoardCache);

		writeResponse(cliRequest, cliResponse, hoardCache, response);
	}

	private void writeResponse(CliRequest cliRequest, CliResponse cliResponse,
			HoardCache hoardCache, HttpServletResponse response) throws IOException {
		String encodedResponse = cliResponse.encode();

		// always save back the new nut
		if (hoardCache != null) {
			HoardCache newHoardCache = new HoardCache();
			newHoardCache.setState(HoardCacheState.ASSOCIATED);
			newHoardCache.setRemoteIP(hoardCache.getRemoteIP());
			newHoardCache.setOriginalNut(hoardCache.getOriginalNut());
			newHoardCache.setPagNut(hoardCache.getPagNut());
			newHoardCache.setLastRequest(cliRequest);
			newHoardCache.setLastResponse(encodedResponse);

			this.hoard.save(cliResponse.getNut(), newHoardCache,
					this.appProperties.getNutExpiration());
		}

		response.getWriter().write(encodedResponse);
	}

	private void finishCliResponse(CliRequest cliRequest, CliResponse cliResponse,
			SqrlIdentity identity, HoardCache hoardCache) {
		boolean accountDisabled = false;

		if (identity != null) {
			accountDisabled = identity.isDisabled();
		}

		if (cliRequest.isAuthCommand() && !accountDisabled) {
			String authURL = this.authenticator.authenticateIdentity(identity);
			this.authStore.saveIdentity(identity);
			if (cliRequest.getClient().getOpt().contains("cps")) {
				cliResponse.setUrl(authURL);
			}
		}

		// fail the ident on account disable
		if (cliRequest.getClient().getCmd().equals("ident") && accountDisabled) {
			cliResponse.withCommandFailed();
		}

		if (cliRequest.isAuthCommand() && !accountDisabled) {
			// for non-CPS we save the state back to the PagNut for redirect on polling
			if (!cliRequest.getClient().getOpt().contains("cps")) {
				HoardCache newHoardCache = new HoardCache();
				newHoardCache.setState(HoardCacheState.AUTHENTICATED);
				newHoardCache.setRemoteIP(hoardCache.getRemoteIP());
				newHoardCache.setOriginalNut(hoardCache.getOriginalNut());
				newHoardCache.setPagNut(hoardCache.getPagNut());
				newHoardCache.setLastRequest(cliRequest);
				newHoardCache.setIdentity(identity);

				this.hoard.save(hoardCache.getPagNut(), newHoardCache,
						this.appProperties.getNutExpiration());
			}
		}
	}

	private void checkPreviousSwap(SqrlIdentity previousIdentity, SqrlIdentity identity,
			CliResponse cliResponse) {
		if (previousIdentity != null) {
			this.authenticator.swapIdentities(previousIdentity, identity);
			cliResponse.clearPreviousIDMatch();
		}
	}

	private static boolean requestValidations(HoardCache hoardCache,
			CliRequest cliRequest, HttpServletRequest request, CliResponse cliResponse) {
		// validate last response against this request
		if (hoardCache.getLastResponse() != null
				&& !hoardCache.getLastResponse().equals(cliRequest.getServer())) {
			cliResponse.withCommandFailed();
			return false;
		}

		// validate the IP if required
		if (!hoardCache.getRemoteIP().equals(request.getRemoteAddr())) {
			if (!cliRequest.getClient().getOpt().contains("noiptest")) {
				cliResponse.withCommandFailed();
				return false;
			}
		}
		else {
			cliResponse.withIPMatch();
		}

		// validating the current request and associated Idk's match
		if (hoardCache.getLastRequest() != null && !hoardCache.getLastRequest()
				.getClient().getIdk().equals(cliRequest.getClient().getIdk())) {
			cliResponse.withCommandFailed().withClientFailure().withBadIDAssociation();
			return false;
		}

		if (Command.fromExternalValue(cliRequest.getClient().getCmd()) == null) {
			cliResponse.withFunctionNotSupported();
			return false;
		}

		return true;
	}

	private boolean knownIdentity(CliRequest cliRequest, CliResponse cliResponse,
			SqrlIdentity identity) {
		cliResponse.withIDMatch();
		identity.setBtn(cliRequest.getClient().getBtn());
		boolean changed = false;
		if (cliRequest.isAuthCommand()) {
			changed = cliRequest.updateIdentity(identity);
		}

		if (cliRequest.getClient().getCmd().equals("enable")
				|| cliRequest.getClient().getCmd().equals("remove")) {

			try {
				if (!verifyUrs(cliRequest, identity.getVuk())) {
					if (identity.isDisabled()) {
						cliResponse.withSQRLDisabled();
					}
					cliResponse.withClientFailure().withCommandFailed();
					return false;
				}
			}
			catch (InvalidKeyException | NoSuchAlgorithmException
					| SignatureException e) {
				cliResponse.withClientFailure().withCommandFailed();
				return false;
			}

			if (cliRequest.getClient().getCmd().equals("enable")) {
				identity.setDisabled(false);
				changed = true;
			}
			else if (cliRequest.getClient().getCmd().equals("remove")) {
				this.authenticator.removeIdentity(identity);
				cliResponse.clearIDMatch();
			}
		}

		if (cliRequest.getClient().getCmd().equals("disable")) {
			identity.setDisabled(true);
			changed = true;
		}

		if (identity.isDisabled()) {
			req.Client.Opt["suk"] = true
			cliResponse.withSQRLDisabled();
		}

		if (changed) {
			this.authStore.saveIdentity(identity);
		}

		return true;
	}

	private SqrlIdentity checkPreviousIdentity(CliRequest cliRequest,
			CliResponse cliResponse) {
		SqrlIdentity previousIdentity = null;

		if (cliRequest.getClient().getPidk() != null
				&& !cliRequest.getClient().getPidk().isBlank()) {
			previousIdentity = this.authStore
					.findIdentity(cliRequest.getClient().getPidk());
		}

		if (previousIdentity != null) {
			cliResponse.withPreviousIDMatch();
			req.Client.Opt["suk"] = true
		}

		return previousIdentity;
	}

	private static CliRequest parseCliRequest(CliRequest cliRequest) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {

		String decodedClient = new String(
				Base64.getUrlDecoder().decode(cliRequest.getClientEncoded()),
				StandardCharsets.UTF_8);
		String[] splitted = decodedClient.split("\r\n");
		Map<String, String> clientMap = new HashMap<>();
		for (String keyValue : splitted) {
			int pos = keyValue.indexOf('=');
			if (pos > 0) {
				clientMap.put(keyValue.substring(0, pos), keyValue.substring(pos + 1));
			}
		}

		ClientBody clientBody = new ClientBody();
		clientBody.setVersion(Set.of(Integer.valueOf(clientMap.get("ver"))));
		clientBody.setCmd(clientMap.get("cmd"));

		String opt = clientMap.get("opt");
		if (opt != null && !opt.isBlank()) {
			String[] splittedOpt = opt.split("~");
			clientBody.setOpt(Set.of(splittedOpt));
		}

		clientBody.setSuk(clientMap.get("suk"));
		clientBody.setVuk(clientMap.get("vuk"));
		clientBody.setPidk(clientMap.get("pidk"));
		clientBody.setIdk(clientMap.get("idk"));

		String btnString = clientMap.get("btn");
		int btn = -1;
		if (btnString != null && !btnString.isBlank()) {
			try {
				btn = Integer.parseInt(btnString);
			}
			catch (NumberFormatException nfe) {
				// ignore this
			}
		}
		clientBody.setBtn(btn);

		cliRequest.setClient(clientBody);

		if (verifySignature(cliRequest)) {
			return cliRequest;
		}

		return null;
	}

	private static boolean verifyUrs(CliRequest cliRequest, String vuk)
			throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		if (vuk == null || vuk.isBlank() || cliRequest.getUrs() == null
				|| cliRequest.getUrs().isBlank()) {
			return false;
		}

		byte[] publicKey = Base64.getUrlDecoder().decode(vuk);
		byte[] decodedUrs = Base64.getUrlDecoder().decode(cliRequest.getUrs());
		byte[] signing = (cliRequest.getClientEncoded() + cliRequest.getServer())
				.getBytes(StandardCharsets.UTF_8);

		return verifyED25519(publicKey, signing, decodedUrs);
	}

	private static boolean verifySignature(CliRequest cliRequest) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		byte[] publicKey = Base64.getUrlDecoder().decode(cliRequest.getClient().getIdk());
		byte[] decodedIds = Base64.getUrlDecoder().decode(cliRequest.getIds());
		byte[] signing = (cliRequest.getClientEncoded() + cliRequest.getServer())
				.getBytes(StandardCharsets.UTF_8);

		if (verifyED25519(publicKey, signing, decodedIds)) {
			return true;
		}

		if (cliRequest.getPids() != null && !cliRequest.getPids().isEmpty()
				|| cliRequest.getClient().getPidk() != null
						&& !cliRequest.getClient().getPidk().isEmpty()) {

			publicKey = Base64.getUrlDecoder().decode(cliRequest.getClient().getPidk());
			byte[] decodedPids = Base64.getUrlDecoder().decode(cliRequest.getPids());

			if (verifyED25519(publicKey, signing, decodedPids)) {
				return true;
			}
		}

		return false;
	}

	private static boolean verifyED25519(byte[] publicKeyBytes, byte[] signing,
			byte[] decodedIds)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

		final Signature signature = new EdDSAEngine(MessageDigest.getInstance("SHA-512"));
		final EdDSAParameterSpec edDsaSpec = EdDSANamedCurveTable
				.getByName(EdDSANamedCurveTable.ED_25519);

		final PublicKey publicKey = new EdDSAPublicKey(
				new EdDSAPublicKeySpec(publicKeyBytes, edDsaSpec));
		signature.initVerify(publicKey);

		signature.update(signing);
		return signature.verify(decodedIds);
	}

}
