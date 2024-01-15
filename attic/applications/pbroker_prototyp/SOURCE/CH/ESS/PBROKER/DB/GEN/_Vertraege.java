package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Vertraege {

	private int vertragid;
	private int kandidatid;
	private int anfrageid;
	private int offerteid;
	private java.sql.Timestamp arbeitsbeginn;
	private java.sql.Timestamp arbeitsende;
	private boolean weiterverpflichtung;
	private int stundenzahl;
	private int tage;
	private double stundensatz;
	private double tagessatz;
	private String besonderes;
	private String arbeitsort;
	private String vertragsnummer;
	private String ma_anrede;
	private String ma_vorname;
	private String ma_name;
	private java.sql.Timestamp ma_geburtsdatum;
	private String ma_strasse;
	private String ma_land;
	private String ma_plz;
	private String ma_ort;
	private String k1_anrede;
	private String k1_vorname;
	private String k1_name;
	private String k1_titel;
	private String k1_gezeichnetam;
	private String k1_gezeichnetort;
	private String k2_anrede;
	private String k2_vorname;
	private String k2_name;
	private String k2_titel;
	private String k2_gezeichnetam;
	private String k2_gezeichnetort;
	private String l1_anrede;
	private String l1_vorname;
	private String l1_name;
	private String l1_titel;
	private String l1_gezeichnetam;
	private String l1_gezeichnetort;
	private String l2_anrede;
	private String l2_vorname;
	private String l2_name;
	private String l2_titel;
	private String l2_gezeichnetam;
	private String l2_gezeichnetort;

	public _Vertraege() {
		this.vertragid = 0;
		this.kandidatid = 0;
		this.anfrageid = 0;
		this.offerteid = 0;
		this.arbeitsbeginn = null;
		this.arbeitsende = null;
		this.weiterverpflichtung = false;
		this.stundenzahl = 0;
		this.tage = 0;
		this.stundensatz = 0.0;
		this.tagessatz = 0.0;
		this.besonderes = null;
		this.arbeitsort = null;
		this.vertragsnummer = null;
		this.ma_anrede = null;
		this.ma_vorname = null;
		this.ma_name = null;
		this.ma_geburtsdatum = null;
		this.ma_strasse = null;
		this.ma_land = null;
		this.ma_plz = null;
		this.ma_ort = null;
		this.k1_anrede = null;
		this.k1_vorname = null;
		this.k1_name = null;
		this.k1_titel = null;
		this.k1_gezeichnetam = null;
		this.k1_gezeichnetort = null;
		this.k2_anrede = null;
		this.k2_vorname = null;
		this.k2_name = null;
		this.k2_titel = null;
		this.k2_gezeichnetam = null;
		this.k2_gezeichnetort = null;
		this.l1_anrede = null;
		this.l1_vorname = null;
		this.l1_name = null;
		this.l1_titel = null;
		this.l1_gezeichnetam = null;
		this.l1_gezeichnetort = null;
		this.l2_anrede = null;
		this.l2_vorname = null;
		this.l2_name = null;
		this.l2_titel = null;
		this.l2_gezeichnetam = null;
		this.l2_gezeichnetort = null;
	}

	public _Vertraege(int vertragid, int kandidatid, int anfrageid, int offerteid, java.sql.Timestamp arbeitsbeginn, java.sql.Timestamp arbeitsende, boolean weiterverpflichtung, int stundenzahl, int tage, double stundensatz, double tagessatz, String besonderes, String arbeitsort, String vertragsnummer, String ma_anrede, String ma_vorname, String ma_name, java.sql.Timestamp ma_geburtsdatum, String ma_strasse, String ma_land, String ma_plz, String ma_ort, String k1_anrede, String k1_vorname, String k1_name, String k1_titel, String k1_gezeichnetam, String k1_gezeichnetort, String k2_anrede, String k2_vorname, String k2_name, String k2_titel, String k2_gezeichnetam, String k2_gezeichnetort, String l1_anrede, String l1_vorname, String l1_name, String l1_titel, String l1_gezeichnetam, String l1_gezeichnetort, String l2_anrede, String l2_vorname, String l2_name, String l2_titel, String l2_gezeichnetam, String l2_gezeichnetort) {
		this.vertragid = vertragid;
		this.kandidatid = kandidatid;
		this.anfrageid = anfrageid;
		this.offerteid = offerteid;
		this.arbeitsbeginn = arbeitsbeginn;
		this.arbeitsende = arbeitsende;
		this.weiterverpflichtung = weiterverpflichtung;
		this.stundenzahl = stundenzahl;
		this.tage = tage;
		this.stundensatz = stundensatz;
		this.tagessatz = tagessatz;
		this.besonderes = besonderes;
		this.arbeitsort = arbeitsort;
		this.vertragsnummer = vertragsnummer;
		this.ma_anrede = ma_anrede;
		this.ma_vorname = ma_vorname;
		this.ma_name = ma_name;
		this.ma_geburtsdatum = ma_geburtsdatum;
		this.ma_strasse = ma_strasse;
		this.ma_land = ma_land;
		this.ma_plz = ma_plz;
		this.ma_ort = ma_ort;
		this.k1_anrede = k1_anrede;
		this.k1_vorname = k1_vorname;
		this.k1_name = k1_name;
		this.k1_titel = k1_titel;
		this.k1_gezeichnetam = k1_gezeichnetam;
		this.k1_gezeichnetort = k1_gezeichnetort;
		this.k2_anrede = k2_anrede;
		this.k2_vorname = k2_vorname;
		this.k2_name = k2_name;
		this.k2_titel = k2_titel;
		this.k2_gezeichnetam = k2_gezeichnetam;
		this.k2_gezeichnetort = k2_gezeichnetort;
		this.l1_anrede = l1_anrede;
		this.l1_vorname = l1_vorname;
		this.l1_name = l1_name;
		this.l1_titel = l1_titel;
		this.l1_gezeichnetam = l1_gezeichnetam;
		this.l1_gezeichnetort = l1_gezeichnetort;
		this.l2_anrede = l2_anrede;
		this.l2_vorname = l2_vorname;
		this.l2_name = l2_name;
		this.l2_titel = l2_titel;
		this.l2_gezeichnetam = l2_gezeichnetam;
		this.l2_gezeichnetort = l2_gezeichnetort;
	}

	public int getVertragid() {
		return vertragid;
	}

	public void setVertragid(int vertragid) {
		this.vertragid = vertragid;
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

	public int getOfferteid() {
		return offerteid;
	}

	public void setOfferteid(int offerteid) {
		this.offerteid = offerteid;
	}

	public java.sql.Timestamp getArbeitsbeginn() {
		return arbeitsbeginn;
	}

	public void setArbeitsbeginn(java.sql.Timestamp arbeitsbeginn) {
		this.arbeitsbeginn = arbeitsbeginn;
	}

	public java.sql.Timestamp getArbeitsende() {
		return arbeitsende;
	}

	public void setArbeitsende(java.sql.Timestamp arbeitsende) {
		this.arbeitsende = arbeitsende;
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

	public String getArbeitsort() {
		return arbeitsort;
	}

	public void setArbeitsort(String arbeitsort) {
		this.arbeitsort = arbeitsort;
	}

	public String getVertragsnummer() {
		return vertragsnummer;
	}

	public void setVertragsnummer(String vertragsnummer) {
		this.vertragsnummer = vertragsnummer;
	}

	public String getMa_anrede() {
		return ma_anrede;
	}

	public void setMa_anrede(String ma_anrede) {
		this.ma_anrede = ma_anrede;
	}

	public String getMa_vorname() {
		return ma_vorname;
	}

	public void setMa_vorname(String ma_vorname) {
		this.ma_vorname = ma_vorname;
	}

	public String getMa_name() {
		return ma_name;
	}

	public void setMa_name(String ma_name) {
		this.ma_name = ma_name;
	}

	public java.sql.Timestamp getMa_geburtsdatum() {
		return ma_geburtsdatum;
	}

	public void setMa_geburtsdatum(java.sql.Timestamp ma_geburtsdatum) {
		this.ma_geburtsdatum = ma_geburtsdatum;
	}

	public String getMa_strasse() {
		return ma_strasse;
	}

	public void setMa_strasse(String ma_strasse) {
		this.ma_strasse = ma_strasse;
	}

	public String getMa_land() {
		return ma_land;
	}

	public void setMa_land(String ma_land) {
		this.ma_land = ma_land;
	}

	public String getMa_plz() {
		return ma_plz;
	}

	public void setMa_plz(String ma_plz) {
		this.ma_plz = ma_plz;
	}

	public String getMa_ort() {
		return ma_ort;
	}

	public void setMa_ort(String ma_ort) {
		this.ma_ort = ma_ort;
	}

	public String getK1_anrede() {
		return k1_anrede;
	}

	public void setK1_anrede(String k1_anrede) {
		this.k1_anrede = k1_anrede;
	}

	public String getK1_vorname() {
		return k1_vorname;
	}

	public void setK1_vorname(String k1_vorname) {
		this.k1_vorname = k1_vorname;
	}

	public String getK1_name() {
		return k1_name;
	}

	public void setK1_name(String k1_name) {
		this.k1_name = k1_name;
	}

	public String getK1_titel() {
		return k1_titel;
	}

	public void setK1_titel(String k1_titel) {
		this.k1_titel = k1_titel;
	}

	public String getK1_gezeichnetam() {
		return k1_gezeichnetam;
	}

	public void setK1_gezeichnetam(String k1_gezeichnetam) {
		this.k1_gezeichnetam = k1_gezeichnetam;
	}

	public String getK1_gezeichnetort() {
		return k1_gezeichnetort;
	}

	public void setK1_gezeichnetort(String k1_gezeichnetort) {
		this.k1_gezeichnetort = k1_gezeichnetort;
	}

	public String getK2_anrede() {
		return k2_anrede;
	}

	public void setK2_anrede(String k2_anrede) {
		this.k2_anrede = k2_anrede;
	}

	public String getK2_vorname() {
		return k2_vorname;
	}

	public void setK2_vorname(String k2_vorname) {
		this.k2_vorname = k2_vorname;
	}

	public String getK2_name() {
		return k2_name;
	}

	public void setK2_name(String k2_name) {
		this.k2_name = k2_name;
	}

	public String getK2_titel() {
		return k2_titel;
	}

	public void setK2_titel(String k2_titel) {
		this.k2_titel = k2_titel;
	}

	public String getK2_gezeichnetam() {
		return k2_gezeichnetam;
	}

	public void setK2_gezeichnetam(String k2_gezeichnetam) {
		this.k2_gezeichnetam = k2_gezeichnetam;
	}

	public String getK2_gezeichnetort() {
		return k2_gezeichnetort;
	}

	public void setK2_gezeichnetort(String k2_gezeichnetort) {
		this.k2_gezeichnetort = k2_gezeichnetort;
	}

	public String getL1_anrede() {
		return l1_anrede;
	}

	public void setL1_anrede(String l1_anrede) {
		this.l1_anrede = l1_anrede;
	}

	public String getL1_vorname() {
		return l1_vorname;
	}

	public void setL1_vorname(String l1_vorname) {
		this.l1_vorname = l1_vorname;
	}

	public String getL1_name() {
		return l1_name;
	}

	public void setL1_name(String l1_name) {
		this.l1_name = l1_name;
	}

	public String getL1_titel() {
		return l1_titel;
	}

	public void setL1_titel(String l1_titel) {
		this.l1_titel = l1_titel;
	}

	public String getL1_gezeichnetam() {
		return l1_gezeichnetam;
	}

	public void setL1_gezeichnetam(String l1_gezeichnetam) {
		this.l1_gezeichnetam = l1_gezeichnetam;
	}

	public String getL1_gezeichnetort() {
		return l1_gezeichnetort;
	}

	public void setL1_gezeichnetort(String l1_gezeichnetort) {
		this.l1_gezeichnetort = l1_gezeichnetort;
	}

	public String getL2_anrede() {
		return l2_anrede;
	}

	public void setL2_anrede(String l2_anrede) {
		this.l2_anrede = l2_anrede;
	}

	public String getL2_vorname() {
		return l2_vorname;
	}

	public void setL2_vorname(String l2_vorname) {
		this.l2_vorname = l2_vorname;
	}

	public String getL2_name() {
		return l2_name;
	}

	public void setL2_name(String l2_name) {
		this.l2_name = l2_name;
	}

	public String getL2_titel() {
		return l2_titel;
	}

	public void setL2_titel(String l2_titel) {
		this.l2_titel = l2_titel;
	}

	public String getL2_gezeichnetam() {
		return l2_gezeichnetam;
	}

	public void setL2_gezeichnetam(String l2_gezeichnetam) {
		this.l2_gezeichnetam = l2_gezeichnetam;
	}

	public String getL2_gezeichnetort() {
		return l2_gezeichnetort;
	}

	public void setL2_gezeichnetort(String l2_gezeichnetort) {
		this.l2_gezeichnetort = l2_gezeichnetort;
	}


	public String toString() {
		return "_Vertraege("+ vertragid + " " + kandidatid + " " + anfrageid + " " + offerteid + " " + arbeitsbeginn + " " + arbeitsende + " " + weiterverpflichtung + " " + stundenzahl + " " + tage + " " + stundensatz + " " + tagessatz + " " + besonderes + " " + arbeitsort + " " + vertragsnummer + " " + ma_anrede + " " + ma_vorname + " " + ma_name + " " + ma_geburtsdatum + " " + ma_strasse + " " + ma_land + " " + ma_plz + " " + ma_ort + " " + k1_anrede + " " + k1_vorname + " " + k1_name + " " + k1_titel + " " + k1_gezeichnetam + " " + k1_gezeichnetort + " " + k2_anrede + " " + k2_vorname + " " + k2_name + " " + k2_titel + " " + k2_gezeichnetam + " " + k2_gezeichnetort + " " + l1_anrede + " " + l1_vorname + " " + l1_name + " " + l1_titel + " " + l1_gezeichnetam + " " + l1_gezeichnetort + " " + l2_anrede + " " + l2_vorname + " " + l2_name + " " + l2_titel + " " + l2_gezeichnetam + " " + l2_gezeichnetort+")";
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
				t.invalidateVertraege();
			}
		}

		kandidaten = new SoftReference(newKandidaten);

		if (newKandidaten != null) {
			newKandidaten.invalidateVertraege();
			kandidatid = newKandidaten.getKandidatid();
		}
	}

	public void invalidateKandidaten() {
		kandidaten = null;
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
				t.invalidateVertraege();
			}
		}

		anfragen = new SoftReference(newAnfragen);

		if (newAnfragen != null) {
			newAnfragen.invalidateVertraege();
			anfrageid = newAnfragen.getAnfrageid();
		}
	}

	public void invalidateAnfragen() {
		anfragen = null;
	}

	private SoftReference offerten = null;

	public boolean hasOfferten() throws SQLException, PoolPropsException {
		return (getOfferten() != null);
	}

	public Offerten getOfferten() throws SQLException, PoolPropsException {

		Offerten t;

		if (offerten == null) {
			t = ch.ess.pbroker.db.Db.getOffertenTable().selectOne("OfferteId = " + getOfferteid());
			offerten = new SoftReference(t);
		} else {
			t = (Offerten)offerten.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getOffertenTable().selectOne("OfferteId = " + getOfferteid());
				offerten = new SoftReference(t);
			}
		}

		return t;
	}

	public void setOfferten(Offerten newOfferten) throws SQLException, PoolPropsException {
		if (offerten != null) {
			Offerten t = (Offerten)offerten.get();

			if (t != null) {
				t.invalidateVertraege();
			}
		}

		offerten = new SoftReference(newOfferten);

		if (newOfferten != null) {
			newOfferten.invalidateVertraege();
			offerteid = newOfferten.getOfferteid();
		}
	}

	public void invalidateOfferten() {
		offerten = null;
	}

}
