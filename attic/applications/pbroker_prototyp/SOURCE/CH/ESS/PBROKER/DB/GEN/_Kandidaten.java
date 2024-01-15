package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Kandidaten {

	private int lieferantid;
	private int kandidatid;
	private String anrede;
	private String vorname;
	private String name;
	private java.sql.Timestamp geburtsdatum;
	private String strasse;
	private String land;
	private String plz;
	private String ort;
	private String telefon;
	private String teldirekt;
	private String mobil;
	private String email;
	private String fax;
	private java.math.BigDecimal stdsatz;
	private String notiz;
	private boolean swisscomerfahrung;
	private String skills;
	private String titel;
	private null kalender_alt;

	public _Kandidaten() {
		this.lieferantid = 0;
		this.kandidatid = 0;
		this.anrede = null;
		this.vorname = null;
		this.name = null;
		this.geburtsdatum = null;
		this.strasse = null;
		this.land = null;
		this.plz = null;
		this.ort = null;
		this.telefon = null;
		this.teldirekt = null;
		this.mobil = null;
		this.email = null;
		this.fax = null;
		this.stdsatz = null;
		this.notiz = null;
		this.swisscomerfahrung = false;
		this.skills = null;
		this.titel = null;
		this.kalender_alt = null;
	}

	public _Kandidaten(int lieferantid, int kandidatid, String anrede, String vorname, String name, java.sql.Timestamp geburtsdatum, String strasse, String land, String plz, String ort, String telefon, String teldirekt, String mobil, String email, String fax, java.math.BigDecimal stdsatz, String notiz, boolean swisscomerfahrung, String skills, String titel, null kalender_alt) {
		this.lieferantid = lieferantid;
		this.kandidatid = kandidatid;
		this.anrede = anrede;
		this.vorname = vorname;
		this.name = name;
		this.geburtsdatum = geburtsdatum;
		this.strasse = strasse;
		this.land = land;
		this.plz = plz;
		this.ort = ort;
		this.telefon = telefon;
		this.teldirekt = teldirekt;
		this.mobil = mobil;
		this.email = email;
		this.fax = fax;
		this.stdsatz = stdsatz;
		this.notiz = notiz;
		this.swisscomerfahrung = swisscomerfahrung;
		this.skills = skills;
		this.titel = titel;
		this.kalender_alt = kalender_alt;
	}

	public int getLieferantid() {
		return lieferantid;
	}

	public void setLieferantid(int lieferantid) {
		this.lieferantid = lieferantid;
	}

	public int getKandidatid() {
		return kandidatid;
	}

	public void setKandidatid(int kandidatid) {
		this.kandidatid = kandidatid;
	}

	public String getAnrede() {
		return anrede;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public java.sql.Timestamp getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(java.sql.Timestamp geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
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

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getTeldirekt() {
		return teldirekt;
	}

	public void setTeldirekt(String teldirekt) {
		this.teldirekt = teldirekt;
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
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

	public java.math.BigDecimal getStdsatz() {
		return stdsatz;
	}

	public void setStdsatz(java.math.BigDecimal stdsatz) {
		this.stdsatz = stdsatz;
	}

	public String getNotiz() {
		return notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}

	public boolean getSwisscomerfahrung() {
		return swisscomerfahrung;
	}

	public void setSwisscomerfahrung(boolean swisscomerfahrung) {
		this.swisscomerfahrung = swisscomerfahrung;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public null getKalender_alt() {
		return kalender_alt;
	}

	public void setKalender_alt(null kalender_alt) {
		this.kalender_alt = kalender_alt;
	}


	public String toString() {
		return "_Kandidaten("+ lieferantid + " " + kandidatid + " " + anrede + " " + vorname + " " + name + " " + geburtsdatum + " " + strasse + " " + land + " " + plz + " " + ort + " " + telefon + " " + teldirekt + " " + mobil + " " + email + " " + fax + " " + stdsatz + " " + notiz + " " + swisscomerfahrung + " " + skills + " " + titel + " " + kalender_alt+")";
	}

	private SoftReference kandidatenskills = null;

	public int getKandidatenskillsSize() throws SQLException, PoolPropsException {
		List t;

		if ( (kandidatenskills != null) && ((t = (List)kandidatenskills.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getKandidatenskillsTable().count("KandidatId = " + getKandidatid());
		}
	}

	public List getKandidatenskills(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("KandidatId = " + getKandidatid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("KandidatId = " + getKandidatid());
	}

	public boolean hasKandidatenskills() throws SQLException, PoolPropsException {
		return (getKandidatenskillsSize() > 0);
	}

	public List getKandidatenskills() throws SQLException, PoolPropsException {
		List resultList;

		if (kandidatenskills == null) {
			resultList = ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("KandidatId = " + getKandidatid());
			kandidatenskills = new SoftReference(resultList);
		} else {
			resultList = (List)kandidatenskills.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("KandidatId = " + getKandidatid());
				kandidatenskills = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateKandidatenskills() {
		kandidatenskills = null;
	}


	private SoftReference offerten = null;

	public int getOffertenSize() throws SQLException, PoolPropsException {
		List t;

		if ( (offerten != null) && ((t = (List)offerten.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getOffertenTable().count("KandidatId = " + getKandidatid());
		}
	}

	public List getOfferten(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getOffertenTable().select("KandidatId = " + getKandidatid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getOffertenTable().select("KandidatId = " + getKandidatid());
	}

	public boolean hasOfferten() throws SQLException, PoolPropsException {
		return (getOffertenSize() > 0);
	}

	public List getOfferten() throws SQLException, PoolPropsException {
		List resultList;

		if (offerten == null) {
			resultList = ch.ess.pbroker.db.Db.getOffertenTable().select("KandidatId = " + getKandidatid());
			offerten = new SoftReference(resultList);
		} else {
			resultList = (List)offerten.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getOffertenTable().select("KandidatId = " + getKandidatid());
				offerten = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateOfferten() {
		offerten = null;
	}


	private SoftReference anfragekandidaten = null;

	public int getAnfragekandidatenSize() throws SQLException, PoolPropsException {
		List t;

		if ( (anfragekandidaten != null) && ((t = (List)anfragekandidaten.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getAnfragekandidatenTable().count("KandidatId = " + getKandidatid());
		}
	}

	public List getAnfragekandidaten(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getAnfragekandidatenTable().select("KandidatId = " + getKandidatid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getAnfragekandidatenTable().select("KandidatId = " + getKandidatid());
	}

	public boolean hasAnfragekandidaten() throws SQLException, PoolPropsException {
		return (getAnfragekandidatenSize() > 0);
	}

	public List getAnfragekandidaten() throws SQLException, PoolPropsException {
		List resultList;

		if (anfragekandidaten == null) {
			resultList = ch.ess.pbroker.db.Db.getAnfragekandidatenTable().select("KandidatId = " + getKandidatid());
			anfragekandidaten = new SoftReference(resultList);
		} else {
			resultList = (List)anfragekandidaten.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getAnfragekandidatenTable().select("KandidatId = " + getKandidatid());
				anfragekandidaten = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateAnfragekandidaten() {
		anfragekandidaten = null;
	}


	private SoftReference vertraege = null;

	public int getVertraegeSize() throws SQLException, PoolPropsException {
		List t;

		if ( (vertraege != null) && ((t = (List)vertraege.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getVertraegeTable().count("KandidatId = " + getKandidatid());
		}
	}

	public List getVertraege(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getVertraegeTable().select("KandidatId = " + getKandidatid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getVertraegeTable().select("KandidatId = " + getKandidatid());
	}

	public boolean hasVertraege() throws SQLException, PoolPropsException {
		return (getVertraegeSize() > 0);
	}

	public List getVertraege() throws SQLException, PoolPropsException {
		List resultList;

		if (vertraege == null) {
			resultList = ch.ess.pbroker.db.Db.getVertraegeTable().select("KandidatId = " + getKandidatid());
			vertraege = new SoftReference(resultList);
		} else {
			resultList = (List)vertraege.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getVertraegeTable().select("KandidatId = " + getKandidatid());
				vertraege = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateVertraege() {
		vertraege = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getKandidatenskills();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Kandidatenskills)it.next()).invalidateKandidaten();
			}
		}

		resultList = getOfferten();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Offerten)it.next()).invalidateKandidaten();
			}
		}

		resultList = getAnfragekandidaten();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Anfragekandidaten)it.next()).invalidateKandidaten();
			}
		}

		resultList = getVertraege();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Vertraege)it.next()).invalidateKandidaten();
			}
		}

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
				t.invalidateKandidaten();
			}
		}

		lieferanten = new SoftReference(newLieferanten);

		if (newLieferanten != null) {
			newLieferanten.invalidateKandidaten();
			lieferantid = newLieferanten.getLieferantid();
		}
	}

	public void invalidateLieferanten() {
		lieferanten = null;
	}

}
