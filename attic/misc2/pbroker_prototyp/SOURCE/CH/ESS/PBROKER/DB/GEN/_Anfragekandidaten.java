package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Anfragekandidaten {

	private int kandidatid;
	private int anfrageid;
	private java.sql.Timestamp hardcopy;
	private java.sql.Timestamp vorstellung;
	private boolean ablehung;

	public _Anfragekandidaten() {
		this.kandidatid = 0;
		this.anfrageid = 0;
		this.hardcopy = null;
		this.vorstellung = null;
		this.ablehung = false;
	}

	public _Anfragekandidaten(int kandidatid, int anfrageid, java.sql.Timestamp hardcopy, java.sql.Timestamp vorstellung, boolean ablehung) {
		this.kandidatid = kandidatid;
		this.anfrageid = anfrageid;
		this.hardcopy = hardcopy;
		this.vorstellung = vorstellung;
		this.ablehung = ablehung;
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

	public java.sql.Timestamp getHardcopy() {
		return hardcopy;
	}

	public void setHardcopy(java.sql.Timestamp hardcopy) {
		this.hardcopy = hardcopy;
	}

	public java.sql.Timestamp getVorstellung() {
		return vorstellung;
	}

	public void setVorstellung(java.sql.Timestamp vorstellung) {
		this.vorstellung = vorstellung;
	}

	public boolean getAblehung() {
		return ablehung;
	}

	public void setAblehung(boolean ablehung) {
		this.ablehung = ablehung;
	}


	public String toString() {
		return "_Anfragekandidaten("+ kandidatid + " " + anfrageid + " " + hardcopy + " " + vorstellung + " " + ablehung+")";
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
				t.invalidateAnfragekandidaten();
			}
		}

		kandidaten = new SoftReference(newKandidaten);

		if (newKandidaten != null) {
			newKandidaten.invalidateAnfragekandidaten();
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
				t.invalidateAnfragekandidaten();
			}
		}

		anfragen = new SoftReference(newAnfragen);

		if (newAnfragen != null) {
			newAnfragen.invalidateAnfragekandidaten();
			anfrageid = newAnfragen.getAnfrageid();
		}
	}

	public void invalidateAnfragen() {
		anfragen = null;
	}

}
