package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Benutzer {

	private int benutzerid;
	private String nachname;
	private String vorname;
	private String loginid;
	private String passwort;
	private boolean aktiv;
	private int kundeid;
	private int lieferantid;

	public _Benutzer() {
		this.benutzerid = 0;
		this.nachname = null;
		this.vorname = null;
		this.loginid = null;
		this.passwort = null;
		this.aktiv = false;
		this.kundeid = 0;
		this.lieferantid = 0;
	}

	public _Benutzer(int benutzerid, String nachname, String vorname, String loginid, String passwort, boolean aktiv, int kundeid, int lieferantid) {
		this.benutzerid = benutzerid;
		this.nachname = nachname;
		this.vorname = vorname;
		this.loginid = loginid;
		this.passwort = passwort;
		this.aktiv = aktiv;
		this.kundeid = kundeid;
		this.lieferantid = lieferantid;
	}

	public int getBenutzerid() {
		return benutzerid;
	}

	public void setBenutzerid(int benutzerid) {
		this.benutzerid = benutzerid;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public boolean getAktiv() {
		return aktiv;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}

	public int getKundeid() {
		return kundeid;
	}

	public void setKundeid(int kundeid) {
		this.kundeid = kundeid;
	}

	public int getLieferantid() {
		return lieferantid;
	}

	public void setLieferantid(int lieferantid) {
		this.lieferantid = lieferantid;
	}


	public String toString() {
		return "_Benutzer("+ benutzerid + " " + nachname + " " + vorname + " " + loginid + " " + passwort + " " + aktiv + " " + kundeid + " " + lieferantid+")";
	}

	private SoftReference benutzerrechte = null;

	public int getBenutzerrechteSize() throws SQLException, PoolPropsException {
		List t;

		if ( (benutzerrechte != null) && ((t = (List)benutzerrechte.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getBenutzerrechteTable().count("BenutzerId = " + getBenutzerid());
		}
	}

	public List getBenutzerrechte(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getBenutzerrechteTable().select("BenutzerId = " + getBenutzerid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getBenutzerrechteTable().select("BenutzerId = " + getBenutzerid());
	}

	public boolean hasBenutzerrechte() throws SQLException, PoolPropsException {
		return (getBenutzerrechteSize() > 0);
	}

	public List getBenutzerrechte() throws SQLException, PoolPropsException {
		List resultList;

		if (benutzerrechte == null) {
			resultList = ch.ess.pbroker.db.Db.getBenutzerrechteTable().select("BenutzerId = " + getBenutzerid());
			benutzerrechte = new SoftReference(resultList);
		} else {
			resultList = (List)benutzerrechte.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getBenutzerrechteTable().select("BenutzerId = " + getBenutzerid());
				benutzerrechte = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateBenutzerrechte() {
		benutzerrechte = null;
	}


	private SoftReference anfragen = null;

	public int getAnfragenSize() throws SQLException, PoolPropsException {
		List t;

		if ( (anfragen != null) && ((t = (List)anfragen.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getAnfragenTable().count("AnfrageId = " + getBenutzerid());
		}
	}

	public List getAnfragen(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getAnfragenTable().select("AnfrageId = " + getBenutzerid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getAnfragenTable().select("AnfrageId = " + getBenutzerid());
	}

	public boolean hasAnfragen() throws SQLException, PoolPropsException {
		return (getAnfragenSize() > 0);
	}

	public List getAnfragen() throws SQLException, PoolPropsException {
		List resultList;

		if (anfragen == null) {
			resultList = ch.ess.pbroker.db.Db.getAnfragenTable().select("AnfrageId = " + getBenutzerid());
			anfragen = new SoftReference(resultList);
		} else {
			resultList = (List)anfragen.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getAnfragenTable().select("AnfrageId = " + getBenutzerid());
				anfragen = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateAnfragen() {
		anfragen = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getBenutzerrechte();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Benutzerrechte)it.next()).invalidateBenutzer();
			}
		}

		resultList = getAnfragen();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Anfragen)it.next()).invalidateBenutzer();
			}
		}

	}

	private SoftReference kunden = null;

	public boolean hasKunden() throws SQLException, PoolPropsException {
		return (getKunden() != null);
	}

	public Kunden getKunden() throws SQLException, PoolPropsException {

		Kunden t;

		if (kunden == null) {
			t = ch.ess.pbroker.db.Db.getKundenTable().selectOne("KundeId = " + getKundeid());
			kunden = new SoftReference(t);
		} else {
			t = (Kunden)kunden.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getKundenTable().selectOne("KundeId = " + getKundeid());
				kunden = new SoftReference(t);
			}
		}

		return t;
	}

	public void setKunden(Kunden newKunden) throws SQLException, PoolPropsException {
		if (kunden != null) {
			Kunden t = (Kunden)kunden.get();

			if (t != null) {
				t.invalidateBenutzer();
			}
		}

		kunden = new SoftReference(newKunden);

		if (newKunden != null) {
			newKunden.invalidateBenutzer();
			kundeid = newKunden.getKundeid();
		}
	}

	public void invalidateKunden() {
		kunden = null;
	}

	private SoftReference lieferanten = null;

	public boolean hasLieferanten() throws SQLException, PoolPropsException {
		return (getLieferanten() != null);
	}

	public Lieferanten getLieferanten() throws SQLException, PoolPropsException {

		Lieferanten t;

		if (lieferanten == null) {
			t = ch.ess.pbroker.db.Db.getLieferantenTable().selectOne("LieferantId = " + getLieferantid());
			lieferanten = new SoftReference(t);
		} else {
			t = (Lieferanten)lieferanten.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getLieferantenTable().selectOne("LieferantId = " + getLieferantid());
				lieferanten = new SoftReference(t);
			}
		}

		return t;
	}

	public void setLieferanten(Lieferanten newLieferanten) throws SQLException, PoolPropsException {
		if (lieferanten != null) {
			Lieferanten t = (Lieferanten)lieferanten.get();

			if (t != null) {
				t.invalidateBenutzer();
			}
		}

		lieferanten = new SoftReference(newLieferanten);

		if (newLieferanten != null) {
			newLieferanten.invalidateBenutzer();
			lieferantid = newLieferanten.getLieferantid();
		}
	}

	public void invalidateLieferanten() {
		lieferanten = null;
	}

}
