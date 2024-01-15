package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Anfragelieferanten {

	private int lieferantid;
	private int anfrageid;
	private boolean allgemein;

	public _Anfragelieferanten() {
		this.lieferantid = 0;
		this.anfrageid = 0;
		this.allgemein = false;
	}

	public _Anfragelieferanten(int lieferantid, int anfrageid, boolean allgemein) {
		this.lieferantid = lieferantid;
		this.anfrageid = anfrageid;
		this.allgemein = allgemein;
	}

	public int getLieferantid() {
		return lieferantid;
	}

	public void setLieferantid(int lieferantid) {
		this.lieferantid = lieferantid;
	}

	public int getAnfrageid() {
		return anfrageid;
	}

	public void setAnfrageid(int anfrageid) {
		this.anfrageid = anfrageid;
	}

	public boolean getAllgemein() {
		return allgemein;
	}

	public void setAllgemein(boolean allgemein) {
		this.allgemein = allgemein;
	}


	public String toString() {
		return "_Anfragelieferanten("+ lieferantid + " " + anfrageid + " " + allgemein+")";
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
				t.invalidateAnfragelieferanten();
			}
		}

		lieferanten = new SoftReference(newLieferanten);

		if (newLieferanten != null) {
			newLieferanten.invalidateAnfragelieferanten();
			lieferantid = newLieferanten.getLieferantid();
		}
	}

	public void invalidateLieferanten() {
		lieferanten = null;
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
				t.invalidateAnfragelieferanten();
			}
		}

		anfragen = new SoftReference(newAnfragen);

		if (newAnfragen != null) {
			newAnfragen.invalidateAnfragelieferanten();
			anfrageid = newAnfragen.getAnfrageid();
		}
	}

	public void invalidateAnfragen() {
		anfragen = null;
	}

}
