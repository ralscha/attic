package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Rekrutierungsangaben {

	private int rekid;
	private int anfrageid;
	private String pensum;
	private String projekt;
	private String taetigkeitsgebiet;
	private String skills;
	private String von;
	private String bis;
	private String bemerkung;
	private String ansprechspartner;
	private String ansprechspartnertel;
	private String ansprechspartneremail;
	private String aufgaben;
	private String offertebis;
	private String oe;

	public _Rekrutierungsangaben() {
		this.rekid = 0;
		this.anfrageid = 0;
		this.pensum = null;
		this.projekt = null;
		this.taetigkeitsgebiet = null;
		this.skills = null;
		this.von = null;
		this.bis = null;
		this.bemerkung = null;
		this.ansprechspartner = null;
		this.ansprechspartnertel = null;
		this.ansprechspartneremail = null;
		this.aufgaben = null;
		this.offertebis = null;
		this.oe = null;
	}

	public _Rekrutierungsangaben(int rekid, int anfrageid, String pensum, String projekt, String taetigkeitsgebiet, String skills, String von, String bis, String bemerkung, String ansprechspartner, String ansprechspartnertel, String ansprechspartneremail, String aufgaben, String offertebis, String oe) {
		this.rekid = rekid;
		this.anfrageid = anfrageid;
		this.pensum = pensum;
		this.projekt = projekt;
		this.taetigkeitsgebiet = taetigkeitsgebiet;
		this.skills = skills;
		this.von = von;
		this.bis = bis;
		this.bemerkung = bemerkung;
		this.ansprechspartner = ansprechspartner;
		this.ansprechspartnertel = ansprechspartnertel;
		this.ansprechspartneremail = ansprechspartneremail;
		this.aufgaben = aufgaben;
		this.offertebis = offertebis;
		this.oe = oe;
	}

	public int getRekid() {
		return rekid;
	}

	public void setRekid(int rekid) {
		this.rekid = rekid;
	}

	public int getAnfrageid() {
		return anfrageid;
	}

	public void setAnfrageid(int anfrageid) {
		this.anfrageid = anfrageid;
	}

	public String getPensum() {
		return pensum;
	}

	public void setPensum(String pensum) {
		this.pensum = pensum;
	}

	public String getProjekt() {
		return projekt;
	}

	public void setProjekt(String projekt) {
		this.projekt = projekt;
	}

	public String getTaetigkeitsgebiet() {
		return taetigkeitsgebiet;
	}

	public void setTaetigkeitsgebiet(String taetigkeitsgebiet) {
		this.taetigkeitsgebiet = taetigkeitsgebiet;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getVon() {
		return von;
	}

	public void setVon(String von) {
		this.von = von;
	}

	public String getBis() {
		return bis;
	}

	public void setBis(String bis) {
		this.bis = bis;
	}

	public String getBemerkung() {
		return bemerkung;
	}

	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}

	public String getAnsprechspartner() {
		return ansprechspartner;
	}

	public void setAnsprechspartner(String ansprechspartner) {
		this.ansprechspartner = ansprechspartner;
	}

	public String getAnsprechspartnertel() {
		return ansprechspartnertel;
	}

	public void setAnsprechspartnertel(String ansprechspartnertel) {
		this.ansprechspartnertel = ansprechspartnertel;
	}

	public String getAnsprechspartneremail() {
		return ansprechspartneremail;
	}

	public void setAnsprechspartneremail(String ansprechspartneremail) {
		this.ansprechspartneremail = ansprechspartneremail;
	}

	public String getAufgaben() {
		return aufgaben;
	}

	public void setAufgaben(String aufgaben) {
		this.aufgaben = aufgaben;
	}

	public String getOffertebis() {
		return offertebis;
	}

	public void setOffertebis(String offertebis) {
		this.offertebis = offertebis;
	}

	public String getOe() {
		return oe;
	}

	public void setOe(String oe) {
		this.oe = oe;
	}


	public String toString() {
		return "_Rekrutierungsangaben("+ rekid + " " + anfrageid + " " + pensum + " " + projekt + " " + taetigkeitsgebiet + " " + skills + " " + von + " " + bis + " " + bemerkung + " " + ansprechspartner + " " + ansprechspartnertel + " " + ansprechspartneremail + " " + aufgaben + " " + offertebis + " " + oe+")";
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
				t.invalidateRekrutierungsangaben();
			}
		}

		anfragen = new SoftReference(newAnfragen);

		if (newAnfragen != null) {
			newAnfragen.invalidateRekrutierungsangaben();
			anfrageid = newAnfragen.getAnfrageid();
		}
	}

	public void invalidateAnfragen() {
		anfragen = null;
	}

}
