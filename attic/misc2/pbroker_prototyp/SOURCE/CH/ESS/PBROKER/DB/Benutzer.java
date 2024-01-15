package ch.ess.pbroker.db;

public class Benutzer extends ch.ess.pbroker.db.gen._Benutzer {

	public Benutzer() {
		super();
	}

	public Benutzer(int benutzerid, String nachname, String vorname, String loginid, String passwort, boolean aktiv, int kundeid, int lieferantid) {
		super(benutzerid, nachname, vorname, loginid, passwort, aktiv, kundeid, lieferantid);
	}

}
