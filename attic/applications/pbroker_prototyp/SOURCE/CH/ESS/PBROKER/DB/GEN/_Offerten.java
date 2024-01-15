package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Offerten {

	private int offerteid;
	private int kandidatid;
	private int anfrageid;
	private String arbeitsbeginn;
	private boolean weiterverpflichtung;
	private int stundenzahl;
	private int tage;
	private double stundensatz;
	private double tagessatz;
	private String besonderes;

	public _Offerten() {
		this.offerteid = 0;
		this.kandidatid = 0;
		this.anfrageid = 0;
		this.arbeitsbeginn = null;
		this.weiterverpflichtung = false;
		this.stundenzahl = 0;
		this.tage = 0;
		this.stundensatz = 0.0;
		this.tagessatz = 0.0;
		this.besonderes = null;
	}

	public _Offerten(int offerteid, int kandidatid, int anfrageid, String arbeitsbeginn, boolean weiterverpflichtung, int stundenzahl, int tage, double stundensatz, double tagessatz, String besonderes) {
		this.offerteid = offerteid;
		this.kandidatid = kandidatid;
		this.anfrageid = anfrageid;
		this.arbeitsbeginn = arbeitsbeginn;
		this.weiterverpflichtung = weiterverpflichtung;
		this.stundenzahl = stundenzahl;
		this.tage = tage;
		this.stundensatz = stundensatz;
		this.tagessatz = tagessatz;
		this.besonderes = besonderes;
	}

	public int getOfferteid() {
		return offerteid;
	}

	public void setOfferteid(int offerteid) {
		this.offerteid = offerteid;
	}

	public int getKandidatid() {
		return kandidatid;
	}

	public void setKandidatid(int kandidatid) {
		this.kandidatid = kandidatid;
	}

	public int getAnfrageid() {
		return anfrageid;
	}

	public void setAnfrageid(int anfrageid) {
		this.anfrageid = anfrageid;
	}

	public String getArbeitsbeginn() {
		return arbeitsbeginn;
	}

	public void setArbeitsbeginn(String arbeitsbeginn) {
		this.arbeitsbeginn = arbeitsbeginn;
	}

	public boolean getWeiterverpflichtung() {
		return weiterverpflichtung;
	}

	public void setWeiterverpflichtung(boolean weiterverpflichtung) {
		this.weiterverpflichtung = weiterverpflichtung;
	}

	public int getStundenzahl() {
		return stundenzahl;
	}

	public void setStundenzahl(int stundenzahl) {
		this.stundenzahl = stundenzahl;
	}

	public int getTage() {
		return tage;
	}

	public void setTage(int tage) {
		this.tage = tage;
	}

	public double getStundensatz() {
		return stundensatz;
	}

	public void setStundensatz(double stundensatz) {
		this.stundensatz = stundensatz;
	}

	public double getTagessatz() {
		return tagessatz;
	}

	public void setTagessatz(double tagessatz) {
		this.tagessatz = tagessatz;
	}

	public String getBesonderes() {
		return besonderes;
	}

	public void setBesonderes(String besonderes) {
		this.besonderes = besonderes;
	}


	public String toString() {
		return "_Offerten("+ offerteid + " " + kandidatid + " " + anfrageid + " " + arbeitsbeginn + " " + weiterverpflichtung + " " + stundenzahl + " " + tage + " " + stundensatz + " " + tagessatz + " " + besonderes+")";
	}

	private SoftReference vertraege = null;

	public int getVertraegeSize() throws SQLException, PoolPropsException {
		List t;

		if ( (vertraege != null) && ((t = (List)vertraege.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getVertraegeTable().count("OfferteId = " + getOfferteid());
		}
	}

	public List getVertraege(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getVertraegeTable().select("OfferteId = " + getOfferteid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getVertraegeTable().select("OfferteId = " + getOfferteid());
	}

	public boolean hasVertraege() throws SQLException, PoolPropsException {
		return (getVertraegeSize() > 0);
	}

	public List getVertraege() throws SQLException, PoolPropsException {
		List resultList;

		if (vertraege == null) {
			resultList = ch.ess.pbroker.db.Db.getVertraegeTable().select("OfferteId = " + getOfferteid());
			vertraege = new SoftReference(resultList);
		} else {
			resultList = (List)vertraege.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getVertraegeTable().select("OfferteId = " + getOfferteid());
				vertraege = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateVertraege() {
		vertraege = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getVertraege();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Vertraege)it.next()).invalidateOfferten();
			}
		}

	}

	private SoftReference anfragen = null;

	public boolean hasAnfragen() throws SQLException, PoolPropsException {
		return (getAnfragen() != null);
	}

	public Anfragen getAnfragen() throws SQLException, PoolPropsException {

		Anfragen t;

		if (anfragen == null) {
			t = ch.ess.pbroker.db.Db.getAnfragenTable().selectOne("AnfrageId = " + getAnfrageid());
			anfragen = new SoftReference(t);
		} else {
			t = (Anfragen)anfragen.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getAnfragenTable().selectOne("AnfrageId = " + getAnfrageid());
				anfragen = new SoftReference(t);
			}
		}

		return t;
	}

	public void setAnfragen(Anfragen newAnfragen) throws SQLException, PoolPropsException {
		if (anfragen != null) {
			Anfragen t = (Anfragen)anfragen.get();

			if (t != null) {
				t.invalidateOfferten();
			}
		}

		anfragen = new SoftReference(newAnfragen);

		if (newAnfragen != null) {
			newAnfragen.invalidateOfferten();
			anfrageid = newAnfragen.getAnfrageid();
		}
	}

	public void invalidateAnfragen() {
		anfragen = null;
	}

	private SoftReference kandidaten = null;

	public boolean hasKandidaten() throws SQLException, PoolPropsException {
		return (getKandidaten() != null);
	}

	public Kandidaten getKandidaten() throws SQLException, PoolPropsException {

		Kandidaten t;

		if (kandidaten == null) {
			t = ch.ess.pbroker.db.Db.getKandidatenTable().selectOne("KandidatId = " + getKandidatid());
			kandidaten = new SoftReference(t);
		} else {
			t = (Kandidaten)kandidaten.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getKandidatenTable().selectOne("KandidatId = " + getKandidatid());
				kandidaten = new SoftReference(t);
			}
		}

		return t;
	}

	public void setKandidaten(Kandidaten newKandidaten) throws SQLException, PoolPropsException {
		if (kandidaten != null) {
			Kandidaten t = (Kandidaten)kandidaten.get();

			if (t != null) {
				t.invalidateOfferten();
			}
		}

		kandidaten = new SoftReference(newKandidaten);

		if (newKandidaten != null) {
			newKandidaten.invalidateOfferten();
			kandidatid = newKandidaten.getKandidatid();
		}
	}

	public void invalidateKandidaten() {
		kandidaten = null;
	}

}
