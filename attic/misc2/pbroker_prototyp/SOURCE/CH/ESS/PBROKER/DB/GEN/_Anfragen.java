package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Anfragen {

	private int anfragerid;
	private int anfrageid;
	private boolean aktiv;
	private java.sql.Timestamp anfragedate;

	public _Anfragen() {
		this.anfragerid = 0;
		this.anfrageid = 0;
		this.aktiv = false;
		this.anfragedate = null;
	}

	public _Anfragen(int anfragerid, int anfrageid, boolean aktiv, java.sql.Timestamp anfragedate) {
		this.anfragerid = anfragerid;
		this.anfrageid = anfrageid;
		this.aktiv = aktiv;
		this.anfragedate = anfragedate;
	}

	public int getAnfragerid() {
		return anfragerid;
	}

	public void setAnfragerid(int anfragerid) {
		this.anfragerid = anfragerid;
	}

	public int getAnfrageid() {
		return anfrageid;
	}

	public void setAnfrageid(int anfrageid) {
		this.anfrageid = anfrageid;
	}

	public boolean getAktiv() {
		return aktiv;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}

	public java.sql.Timestamp getAnfragedate() {
		return anfragedate;
	}

	public void setAnfragedate(java.sql.Timestamp anfragedate) {
		this.anfragedate = anfragedate;
	}


	public String toString() {
		return "_Anfragen("+ anfragerid + " " + anfrageid + " " + aktiv + " " + anfragedate+")";
	}

	private SoftReference vertraege = null;

	public int getVertraegeSize() throws SQLException, PoolPropsException {
		List t;

		if ( (vertraege != null) && ((t = (List)vertraege.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getVertraegeTable().count("AnfrageId = " + getAnfrageid());
		}
	}

	public List getVertraege(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getVertraegeTable().select("AnfrageId = " + getAnfrageid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getVertraegeTable().select("AnfrageId = " + getAnfrageid());
	}

	public boolean hasVertraege() throws SQLException, PoolPropsException {
		return (getVertraegeSize() > 0);
	}

	public List getVertraege() throws SQLException, PoolPropsException {
		List resultList;

		if (vertraege == null) {
			resultList = ch.ess.pbroker.db.Db.getVertraegeTable().select("AnfrageId = " + getAnfrageid());
			vertraege = new SoftReference(resultList);
		} else {
			resultList = (List)vertraege.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getVertraegeTable().select("AnfrageId = " + getAnfrageid());
				vertraege = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateVertraege() {
		vertraege = null;
	}


	private SoftReference anfragekandidaten = null;

	public int getAnfragekandidatenSize() throws SQLException, PoolPropsException {
		List t;

		if ( (anfragekandidaten != null) && ((t = (List)anfragekandidaten.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getAnfragekandidatenTable().count("AnfrageId = " + getAnfrageid());
		}
	}

	public List getAnfragekandidaten(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getAnfragekandidatenTable().select("AnfrageId = " + getAnfrageid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getAnfragekandidatenTable().select("AnfrageId = " + getAnfrageid());
	}

	public boolean hasAnfragekandidaten() throws SQLException, PoolPropsException {
		return (getAnfragekandidatenSize() > 0);
	}

	public List getAnfragekandidaten() throws SQLException, PoolPropsException {
		List resultList;

		if (anfragekandidaten == null) {
			resultList = ch.ess.pbroker.db.Db.getAnfragekandidatenTable().select("AnfrageId = " + getAnfrageid());
			anfragekandidaten = new SoftReference(resultList);
		} else {
			resultList = (List)anfragekandidaten.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getAnfragekandidatenTable().select("AnfrageId = " + getAnfrageid());
				anfragekandidaten = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateAnfragekandidaten() {
		anfragekandidaten = null;
	}


	private SoftReference anfragelieferanten = null;

	public int getAnfragelieferantenSize() throws SQLException, PoolPropsException {
		List t;

		if ( (anfragelieferanten != null) && ((t = (List)anfragelieferanten.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getAnfragelieferantenTable().count("AnfrageId = " + getAnfrageid());
		}
	}

	public List getAnfragelieferanten(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getAnfragelieferantenTable().select("AnfrageId = " + getAnfrageid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getAnfragelieferantenTable().select("AnfrageId = " + getAnfrageid());
	}

	public boolean hasAnfragelieferanten() throws SQLException, PoolPropsException {
		return (getAnfragelieferantenSize() > 0);
	}

	public List getAnfragelieferanten() throws SQLException, PoolPropsException {
		List resultList;

		if (anfragelieferanten == null) {
			resultList = ch.ess.pbroker.db.Db.getAnfragelieferantenTable().select("AnfrageId = " + getAnfrageid());
			anfragelieferanten = new SoftReference(resultList);
		} else {
			resultList = (List)anfragelieferanten.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getAnfragelieferantenTable().select("AnfrageId = " + getAnfrageid());
				anfragelieferanten = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateAnfragelieferanten() {
		anfragelieferanten = null;
	}


	private SoftReference anfrageskills = null;

	public int getAnfrageskillsSize() throws SQLException, PoolPropsException {
		List t;

		if ( (anfrageskills != null) && ((t = (List)anfrageskills.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getAnfrageskillsTable().count("AnfrageId = " + getAnfrageid());
		}
	}

	public List getAnfrageskills(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getAnfrageskillsTable().select("AnfrageId = " + getAnfrageid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getAnfrageskillsTable().select("AnfrageId = " + getAnfrageid());
	}

	public boolean hasAnfrageskills() throws SQLException, PoolPropsException {
		return (getAnfrageskillsSize() > 0);
	}

	public List getAnfrageskills() throws SQLException, PoolPropsException {
		List resultList;

		if (anfrageskills == null) {
			resultList = ch.ess.pbroker.db.Db.getAnfrageskillsTable().select("AnfrageId = " + getAnfrageid());
			anfrageskills = new SoftReference(resultList);
		} else {
			resultList = (List)anfrageskills.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getAnfrageskillsTable().select("AnfrageId = " + getAnfrageid());
				anfrageskills = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateAnfrageskills() {
		anfrageskills = null;
	}


	private SoftReference offerten = null;

	public int getOffertenSize() throws SQLException, PoolPropsException {
		List t;

		if ( (offerten != null) && ((t = (List)offerten.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getOffertenTable().count("AnfrageId = " + getAnfrageid());
		}
	}

	public List getOfferten(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getOffertenTable().select("AnfrageId = " + getAnfrageid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getOffertenTable().select("AnfrageId = " + getAnfrageid());
	}

	public boolean hasOfferten() throws SQLException, PoolPropsException {
		return (getOffertenSize() > 0);
	}

	public List getOfferten() throws SQLException, PoolPropsException {
		List resultList;

		if (offerten == null) {
			resultList = ch.ess.pbroker.db.Db.getOffertenTable().select("AnfrageId = " + getAnfrageid());
			offerten = new SoftReference(resultList);
		} else {
			resultList = (List)offerten.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getOffertenTable().select("AnfrageId = " + getAnfrageid());
				offerten = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateOfferten() {
		offerten = null;
	}


	private SoftReference rekrutierungsangaben = null;

	public int getRekrutierungsangabenSize() throws SQLException, PoolPropsException {
		List t;

		if ( (rekrutierungsangaben != null) && ((t = (List)rekrutierungsangaben.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getRekrutierungsangabenTable().count("AnfrageId = " + getAnfrageid());
		}
	}

	public List getRekrutierungsangaben(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getRekrutierungsangabenTable().select("AnfrageId = " + getAnfrageid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getRekrutierungsangabenTable().select("AnfrageId = " + getAnfrageid());
	}

	public boolean hasRekrutierungsangaben() throws SQLException, PoolPropsException {
		return (getRekrutierungsangabenSize() > 0);
	}

	public List getRekrutierungsangaben() throws SQLException, PoolPropsException {
		List resultList;

		if (rekrutierungsangaben == null) {
			resultList = ch.ess.pbroker.db.Db.getRekrutierungsangabenTable().select("AnfrageId = " + getAnfrageid());
			rekrutierungsangaben = new SoftReference(resultList);
		} else {
			resultList = (List)rekrutierungsangaben.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getRekrutierungsangabenTable().select("AnfrageId = " + getAnfrageid());
				rekrutierungsangaben = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateRekrutierungsangaben() {
		rekrutierungsangaben = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getVertraege();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Vertraege)it.next()).invalidateAnfragen();
			}
		}

		resultList = getAnfragekandidaten();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Anfragekandidaten)it.next()).invalidateAnfragen();
			}
		}

		resultList = getAnfragelieferanten();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Anfragelieferanten)it.next()).invalidateAnfragen();
			}
		}

		resultList = getAnfrageskills();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Anfrageskills)it.next()).invalidateAnfragen();
			}
		}

		resultList = getOfferten();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Offerten)it.next()).invalidateAnfragen();
			}
		}

		resultList = getRekrutierungsangaben();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Rekrutierungsangaben)it.next()).invalidateAnfragen();
			}
		}

	}

	private SoftReference benutzer = null;

	public boolean hasBenutzer() throws SQLException, PoolPropsException {
		return (getBenutzer() != null);
	}

	public Benutzer getBenutzer() throws SQLException, PoolPropsException {

		Benutzer t;

		if (benutzer == null) {
			t = ch.ess.pbroker.db.Db.getBenutzerTable().selectOne("BenutzerId = " + getAnfragerid());
			benutzer = new SoftReference(t);
		} else {
			t = (Benutzer)benutzer.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getBenutzerTable().selectOne("BenutzerId = " + getAnfragerid());
				benutzer = new SoftReference(t);
			}
		}

		return t;
	}

	public void setBenutzer(Benutzer newBenutzer) throws SQLException, PoolPropsException {
		if (benutzer != null) {
			Benutzer t = (Benutzer)benutzer.get();

			if (t != null) {
				t.invalidateAnfragen();
			}
		}

		benutzer = new SoftReference(newBenutzer);

		if (newBenutzer != null) {
			newBenutzer.invalidateAnfragen();
			anfragerid = newBenutzer.getBenutzerid();
		}
	}

	public void invalidateBenutzer() {
		benutzer = null;
	}

}
