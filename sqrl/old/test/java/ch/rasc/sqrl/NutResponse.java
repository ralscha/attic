package ch.rasc.sqrl;

public class NutResponse {
	private final String nut;
	private final String pag;
	private final String exp;
	private final String can;

	public NutResponse(String nut, String pag, String exp, String can) {
		this.nut = nut;
		this.pag = pag;
		this.exp = exp;
		this.can = can;
	}

	public String getNut() {
		return this.nut;
	}

	public String getPag() {
		return this.pag;
	}

	public String getExp() {
		return this.exp;
	}

	public String getCan() {
		return this.can;
	}

}
