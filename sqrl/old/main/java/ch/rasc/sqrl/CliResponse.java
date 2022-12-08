package ch.rasc.sqrl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

public class CliResponse {

	// the identity has been seen before
	private final static int TIFIDMatch = 0x1;
	// the previous identity is a known identity
	private final static int TIFPreviousIDMatch = 0x2;
	// the IP address of the current request and the original Nut request match
	private final static int TIFIPMatched = 0x4;
	// the SQRL account is disabled
	private final static int TIFSQRLDisabled = 0x8;
	// the ClientBody.Cmd is not recognized
	private final static int TIFFunctionNotSupported = 0x10;
	// used for all the random server errors like failures to connect to datastores
	private final static int TIFTransientError = 0x20;
	// the specific ClientBody.Cmd could not be completed for any reason
	private final static int TIFCommandFailed = 0x40;
	// the client sent bad or unrecognized data or signature validation failed
	private final static int TIFClientFailure = 0x80;
	// The owner of the Nut doesn't match this request
	private final static int TIFBadIDAssociation = 0x100;

	private Set<Integer> version;
	private String nut;
	private int tif;
	private String qry;
	private String url;
	private String sin;
	private String suk;
	private Ask ask;
	private String can;

	public CliResponse(String nut, String qry) {
		this.nut = nut;
		this.qry = qry;
		this.version = Set.of(1);
	}

	public String encode() {
		StringBuilder sb = new StringBuilder();

		sb.append("ver=");
		sb.append(this.version.stream().map(String::valueOf)
				.collect(Collectors.joining(",")));
		sb.append("\r\n");

		sb.append("nut=");
		sb.append(this.nut);
		sb.append("\r\n");

		sb.append("tif=");
		sb.append(this.tif);
		sb.append("\r\n");

		sb.append("qry=");
		sb.append(this.qry);
		sb.append("\r\n");

		if (this.url != null && !this.url.isBlank()) {
			sb.append("url=");
			sb.append(this.url);
			sb.append("\r\n");
		}

		if (this.sin != null && !this.sin.isBlank()) {
			sb.append("sin=");
			sb.append(this.sin);
			sb.append("\r\n");
		}

		if (this.suk != null && !this.suk.isBlank()) {
			sb.append("suk=");
			sb.append(this.suk);
			sb.append("\r\n");
		}

		if (this.ask != null) {
			sb.append("ask=");
			sb.append(this.ask.encode());
			sb.append("\r\n");
		}

		if (this.can != null && !this.can.isBlank()) {
			sb.append("can=");
			sb.append(this.can);
			sb.append("\r\n");
		}

		return Base64.getUrlEncoder().withoutPadding()
				.encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8));
	}

	public CliResponse withIDMatch() {
		this.tif |= TIFIDMatch;
		return this;
	}

	public CliResponse clearIDMatch() {
		this.tif = this.tif & ~TIFIDMatch;
		return this;
	}

	public CliResponse withPreviousIDMatch() {
		this.tif |= TIFPreviousIDMatch;
		return this;
	}

	public CliResponse clearPreviousIDMatch() {
		this.tif = this.tif & ~TIFPreviousIDMatch;
		return this;
	}

	public CliResponse withIPMatch() {
		this.tif |= TIFIPMatched;
		return this;
	}

	public CliResponse withSQRLDisabled() {
		this.tif |= TIFSQRLDisabled;
		return this;
	}

	public CliResponse withFunctionNotSupported() {
		this.tif |= TIFFunctionNotSupported;
		return this;
	}

	/**
	 * Sets the appropriate TIF bits on this response. Returns the object for easier
	 * chaining (not immutability).
	 */
	public CliResponse withTransientError() {
		this.tif |= TIFTransientError;
		return this;
	}

	public CliResponse withClientFailure() {
		this.tif |= TIFClientFailure;
		return this;
	}

	public CliResponse withCommandFailed() {
		this.tif |= TIFCommandFailed;
		return this;
	}

	public CliResponse withBadIDAssociation() {
		this.tif |= TIFBadIDAssociation;
		return this;
	}

	public Set<Integer> getVersion() {
		return this.version;
	}

	public void setVersion(Set<Integer> version) {
		this.version = version;
	}

	public String getNut() {
		return this.nut;
	}

	public void setNut(String nut) {
		this.nut = nut;
	}

	public int getTif() {
		return this.tif;
	}

	public void setTif(int tif) {
		this.tif = tif;
	}

	public String getQry() {
		return this.qry;
	}

	public void setQry(String qry) {
		this.qry = qry;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSin() {
		return this.sin;
	}

	public void setSin(String sin) {
		this.sin = sin;
	}

	public String getSuk() {
		return this.suk;
	}

	public void setSuk(String suk) {
		this.suk = suk;
	}

	public Ask getAsk() {
		return this.ask;
	}

	public void setAsk(Ask ask) {
		this.ask = ask;
	}

	public String getCan() {
		return this.can;
	}

	public void setCan(String can) {
		this.can = can;
	}

}
