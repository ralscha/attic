package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Lieferanten {

	private int lieferantid;
	private String lieferant;
	private String strasse;
	private String plz;
	private String ort;
	private String land;
	private String telefon;
	private String email;
	private String fax;
	private String notiz;

	public _Lieferanten() {
		this.lieferantid = 0;
		this.lieferant = null;
		this.strasse = null;
		this.plz = null;
		this.ort = null;
		this.land = null;
		this.telefon = null;
		this.email = null;
		this.fax = null;
		this.notiz = null;
	}

	public _Lieferanten(int lieferantid, String lieferant, String strasse, String plz, String ort, String land, String telefon, String email, String fax, String notiz) {
		this.lieferantid = lieferantid;
		this.lieferant = lieferant;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.land = land;
		this.telefon = telefon;
		this.email = email;
		this.fax = fax;
		this.notiz = notiz;
	}

	public int getLieferantid() {
		return lieferantid;
	}

	public void setLieferantid(int lieferantid) {
		this.lieferantid = lieferantid;
	}

	public String getLieferant() {
		return lieferant;
	}

	public void setLieferant(String lieferant) {
		this.lieferant = lieferant;
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
		return "_Lieferanten("+ lieferantid + " " + lieferant + " " + strasse + " " + plz + " " + ort + " " + land + " " + telefon + " " + email + " " + fax + " " + notiz+")";
	}

	private SoftReference benutzer = null;

	public int getBenutzerSize() throws SQLException, PoolPropsException {
		List t;

		if ( (benutzer != null) && ((t = (List)benutzer.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getBenutzerTable().count("LieferantId = " + getLieferantid());
		}
	}

	public List getBenutzer(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getBenutzerTable().select("LieferantId = " + getLieferantid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getBenutzerTable().select("LieferantId = " + getLieferantid());
	}

	public boolean hasBenutzer() throws SQLException, PoolPropsException {
		return (getBenutzerSize() > 0);
	}

	public List getBenutzer() throws SQLException, PoolPropsException {
		List resultList;

		if (benutzer == null) {
			resultList = ch.ess.pbroker.db.Db.getBenutzerTable().select("LieferantId = " + getLieferantid());
			benutzer = new SoftReference(resultList);
		} else {
			resultList = (List)benutzer.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getBenutzerTable().select("LieferantId = " + getLieferantid());
				benutzer = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateBenutzer() {
		benutzer = null;
	}


	private SoftReference anfragelieferanten = null;

	public int getAnfragelieferantenSize() throws SQLException, PoolPropsException {
		List t;

		if ( (anfragelieferanten != null) && ((t = (List)anfragelieferanten.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getAnfragelieferantenTable().count("LieferantId = " + getLieferantid());
		}
	}

	public List getAnfragelieferanten(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getAnfragelieferantenTable().select("LieferantId = " + getLieferantid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getAnfragelieferantenTable().select("LieferantId = " + getLieferantid());
	}

	public boolean hasAnfragelieferanten() throws SQLException, PoolPropsException {
		return (getAnfragelieferantenSize() > 0);
	}

	public List getAnfragelieferanten() throws SQLException, PoolPropsException {
		List resultList;

		if (anfragelieferanten == null) {
			resultList = ch.ess.pbroker.db.Db.getAnfragelieferantenTable().select("LieferantId = " + getLieferantid());
			anfragelieferanten = new SoftReference(resultList);
		} else {
			resultList = (List)anfragelieferanten.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getAnfragelieferantenTable().select("LieferantId = " + getLieferantid());
				anfragelieferanten = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateAnfragelieferanten() {
		anfragelieferanten = null;
	}


	private SoftReference kandidaten = null;

	public int getKandidatenSize() throws SQLException, PoolPropsException {
		List t;

		if ( (kandidaten != null) && ((t = (List)kandidaten.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getKandidatenTable().count("LieferantId = " + getLieferantid());
		}
	}

	public List getKandidaten(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getKandidatenTable().select("LieferantId = " + getLieferantid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getKandidatenTable().select("LieferantId = " + getLieferantid());
	}

	public boolean hasKandidaten() throws SQLException, PoolPropsException {
		return (getKandidatenSize() > 0);
	}

	public List getKandidaten() throws SQLException, PoolPropsException {
		List resultList;

		if (kandidaten == null) {
			resultList = ch.ess.pbroker.db.Db.getKandidatenTable().select("LieferantId = " + getLieferantid());
			kandidaten = new SoftReference(resultList);
		} else {
			resultList = (List)kandidaten.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getKandidatenTable().select("LieferantId = " + getLieferantid());
				kandidaten = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateKandidaten() {
		kandidaten = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getBenutzer();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Benutzer)it.next()).invalidateLieferanten();
			}
		}

		resultList = getAnfragelieferanten();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Anfragelieferanten)it.next()).invalidateLieferanten();
			}
		}

		resultList = getKandidaten();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Kandidaten)it.next()).invalidateLieferanten();
			}
		}

	}

}
