package ch.rasc.sqrl;

public class Client {

	private final FakeIdentity identity;
	private NutResponse nutResponse;
	private String qry;
	private String currentNut;
	private String lastServerResponse;

	public Client(FakeIdentity identity) {
		this.identity = identity;
	}

	public static Client newClient() {
		return new Client(TestUtil.newFakeIdentity());
	}
	
	public FakeIdentity getIdentity() {
		return this.identity;
	}

	public NutResponse getNutResponse() {
		return this.nutResponse;
	}

	public void setNutResponse(NutResponse nutResponse) {
		this.nutResponse = nutResponse;
		this.currentNut = nutResponse.getNut();
	}

	public String getQry() {
		return this.qry;
	}

	public void setQry(String qry) {
		this.qry = qry;
	}

	public String getCurrentNut() {
		return this.currentNut;
	}

	public void setCurrentNut(String currentNut) {
		this.currentNut = currentNut;
	}

	public String getLastServerResponse() {
		return this.lastServerResponse;
	}

	public void setLastServerResponse(String lastServerResponse) {
		this.lastServerResponse = lastServerResponse;
	}

}
