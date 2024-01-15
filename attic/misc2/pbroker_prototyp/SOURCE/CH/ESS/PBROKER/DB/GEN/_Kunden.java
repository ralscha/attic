package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Kunden {

	private int kundeid;
	private String kunde;
	private String strasse;
	private String plz;
	private String ort;
	private String land;
	private String telefon;
	private String email;
	private String fax;
	private String notiz;

	public _Kunden() {
		this.kundeid = 0;
		this.kunde = null;
		this.strasse = null;
		this.plz = null;
		this.ort = null;
		this.land = null;
		this.telefon = null;
		this.email = null;
		this.fax = null;
		this.notiz = null;
	}

	public _Kunden(int kundeid, String kunde, String strasse, String plz, String ort, String land, String telefon, String email, String fax, String notiz) {
		this.kundeid = kundeid;
		this.kunde = kunde;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.land = land;
		this.telefon = telefon;
		this.email = email;
		this.fax = fax;
		this.notiz = notiz;
	}

	public int getKundeid() {
		return kundeid;
	}

	public void setKundeid(int kundeid) {
		this.kundeid = kundeid;
	}

	public String getKunde() {
		return kunde;
	}

	public void setKunde(String kunde) {
		this.kunde = kunde;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNotiz() {
		return notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}


	public String toString() {
		return "_Kunden("+ kundeid + " " + kunde + " " + strasse + " " + plz + " " + ort + " " + land + " " + telefon + " " + email + " " + fax + " " + notiz+")";
	}

	private SoftReference benutzer = null;

	public int getBenutzerSize() throws SQLException, PoolPropsException {
		List t;

		if ( (benutzer != null) && ((t = (List)benutzer.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getBenutzerTable().count("KundeId = " + getKundeid());
		}
	}

	public List getBenutzer(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getBenutzerTable().select("KundeId = " + getKundeid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getBenutzerTable().select("KundeId = " + getKundeid());
	}

	public boolean hasBenutzer() throws SQLException, PoolPropsException {
		return (getBenutzerSize() > 0);
	}

	public List getBenutzer() throws SQLException, PoolPropsException {
		List resultList;

		if (benutzer == null) {
			resultList = ch.ess.pbroker.db.Db.getBenutzerTable().select("KundeId = " + getKundeid());
			benutzer = new SoftReference(resultList);
		} else {
			resultList = (List)benutzer.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getBenutzerTable().select("KundeId = " + getKundeid());
				benutzer = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateBenutzer() {
		benutzer = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getBenutzer();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Benutzer)it.next()).invalidateKunden();
			}
		}

	}

}
