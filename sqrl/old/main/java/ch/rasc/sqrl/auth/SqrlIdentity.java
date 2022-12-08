package ch.rasc.sqrl.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.rasc.sqrl.CliRequest;
import ch.rasc.sqrl.ClientBody;

/**
 * Holds all the info about a valid SQRL identity
 */
public class SqrlIdentity {

	private String idk;

	private String suk;

	private String vuk;

	private String pidk;

	private boolean sqrlOnly;

	private boolean hardlock;

	private boolean disabled;

	@JsonIgnore
	private int btn;

	public SqrlIdentity() {
	}

	public SqrlIdentity(CliRequest cliRequest) {
		ClientBody client = cliRequest.getClient();
		this.idk = client.getIdk();
		this.suk = client.getSuk();
		this.vuk = client.getVuk();
		this.pidk = client.getPidk();
		this.sqrlOnly = client.getOpt().contains("sqrlonly");
		this.hardlock = client.getOpt().contains("hardlock");
		this.btn = client.getBtn();
	}

	public String getIdk() {
		return this.idk;
	}

	public void setIdk(String idk) {
		this.idk = idk;
	}

	public String getSuk() {
		return this.suk;
	}

	public void setSuk(String suk) {
		this.suk = suk;
	}

	public String getVuk() {
		return this.vuk;
	}

	public void setVuk(String vuk) {
		this.vuk = vuk;
	}

	public String getPidk() {
		return this.pidk;
	}

	public void setPidk(String pidk) {
		this.pidk = pidk;
	}

	public boolean isSqrlOnly() {
		return this.sqrlOnly;
	}

	public void setSqrlOnly(boolean sqrlOnly) {
		this.sqrlOnly = sqrlOnly;
	}

	public boolean isHardlock() {
		return this.hardlock;
	}

	public void setHardlock(boolean hardlock) {
		this.hardlock = hardlock;
	}

	public boolean isDisabled() {
		return this.disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public int getBtn() {
		return this.btn;
	}

	public void setBtn(int btn) {
		this.btn = btn;
	}

}
