package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Benutzerrechte {

	private int benutzerid;
	private int rechtid;
	private boolean lesen;
	private boolean schreiben;

	public _Benutzerrechte() {
		this.benutzerid = 0;
		this.rechtid = 0;
		this.lesen = false;
		this.schreiben = false;
	}

	public _Benutzerrechte(int benutzerid, int rechtid, boolean lesen, boolean schreiben) {
		this.benutzerid = benutzerid;
		this.rechtid = rechtid;
		this.lesen = lesen;
		this.schreiben = schreiben;
	}

	public int getBenutzerid() {
		return benutzerid;
	}

	public void setBenutzerid(int benutzerid) {
		this.benutzerid = benutzerid;
	}

	public int getRechtid() {
		return rechtid;
	}

	public void setRechtid(int rechtid) {
		this.rechtid = rechtid;
	}

	public boolean getLesen() {
		return lesen;
	}

	public void setLesen(boolean lesen) {
		this.lesen = lesen;
	}

	public boolean getSchreiben() {
		return schreiben;
	}

	public void setSchreiben(boolean schreiben) {
		this.schreiben = schreiben;
	}


	public String toString() {
		return "_Benutzerrechte("+ benutzerid + " " + rechtid + " " + lesen + " " + schreiben+")";
	}
	private SoftReference benutzer = null;

	public boolean hasBenutzer() throws SQLException, PoolPropsException {
		return (getBenutzer() != null);
	}

	public Benutzer getBenutzer() throws SQLException, PoolPropsException {

		Benutzer t;

		if (benutzer == null) {
			t = ch.ess.pbroker.db.Db.getBenutzerTable().selectOne("BenutzerId = " + getBenutzerid());
			benutzer = new SoftReference(t);
		} else {
			t = (Benutzer)benutzer.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getBenutzerTable().selectOne("BenutzerId = " + getBenutzerid());
				benutzer = new SoftReference(t);
			}
		}

		return t;
	}

	public void setBenutzer(Benutzer newBenutzer) throws SQLException, PoolPropsException {
		if (benutzer != null) {
			Benutzer t = (Benutzer)benutzer.get();

			if (t != null) {
				t.invalidateBenutzerrechte();
			}
		}

		benutzer = new SoftReference(newBenutzer);

		if (newBenutzer != null) {
			newBenutzer.invalidateBenutzerrechte();
			benutzerid = newBenutzer.getBenutzerid();
		}
	}

	public void invalidateBenutzer() {
		benutzer = null;
	}

	private SoftReference rechte = null;

	public boolean hasRechte() throws SQLException, PoolPropsException {
		return (getRechte() != null);
	}

	public Rechte getRechte() throws SQLException, PoolPropsException {

		Rechte t;

		if (rechte == null) {
			t = ch.ess.pbroker.db.Db.getRechteTable().selectOne("RechtId = " + getRechtid());
			rechte = new SoftReference(t);
		} else {
			t = (Rechte)rechte.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getRechteTable().selectOne("RechtId = " + getRechtid());
				rechte = new SoftReference(t);
			}
		}

		return t;
	}

	public void setRechte(Rechte newRechte) throws SQLException, PoolPropsException {
		if (rechte != null) {
			Rechte t = (Rechte)rechte.get();

			if (t != null) {
				t.invalidateBenutzerrechte();
			}
		}

		rechte = new SoftReference(newRechte);

		if (newRechte != null) {
			newRechte.invalidateBenutzerrechte();
			rechtid = newRechte.getRechtid();
		}
	}

	public void invalidateRechte() {
		rechte = null;
	}

}
