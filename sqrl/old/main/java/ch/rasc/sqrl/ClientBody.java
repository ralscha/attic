package ch.rasc.sqrl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientBody {

	private Set<Integer> version;

	private String cmd;

	private Set<String> opt;

	private String suk;

	private String vuk;

	private String pidk;

	private String idk;

	/**
	 * Valid values are 0,1,2; -1 means no value
	 */
	private int btn;

	public String encode() {
		StringBuilder sb = new StringBuilder();

		sb.append("ver=");
		sb.append(this.version.stream().map(String::valueOf)
				.collect(Collectors.joining(",")));
		sb.append("\r\n");

		sb.append("cmd=");
		sb.append(this.cmd);
		sb.append("\r\n");

		if (this.opt != null && !this.opt.isEmpty()) {
			sb.append("opt=");
			sb.append(this.opt.stream().collect(Collectors.joining("~")));
			sb.append("\r\n");
		}

		sb.append("idk=");
		sb.append(this.idk);
		sb.append("\r\n");

		if (this.suk != null && !this.suk.isBlank()) {
			sb.append("suk=");
			sb.append(this.suk);
			sb.append("\r\n");
		}

		if (this.vuk != null && !this.vuk.isBlank()) {
			sb.append("vuk=");
			sb.append(this.vuk);
			sb.append("\r\n");
		}

		if (this.pidk != null && !this.pidk.isBlank()) {
			sb.append("pidk=");
			sb.append(this.pidk);
			sb.append("\r\n");
		}

		return Base64.getUrlEncoder().withoutPadding()
				.encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8));
	}

	public Set<Integer> getVersion() {
		return this.version;
	}

	public void setVersion(Set<Integer> version) {
		this.version = version;
	}

	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Set<String> getOpt() {
		return this.opt;
	}

	public void setOpt(Set<String> opt) {
		this.opt = opt;
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

	public String getIdk() {
		return this.idk;
	}

	public void setIdk(String idk) {
		this.idk = idk;
	}

	public int getBtn() {
		return this.btn;
	}

	public void setBtn(int btn) {
		this.btn = btn;
	}

}
