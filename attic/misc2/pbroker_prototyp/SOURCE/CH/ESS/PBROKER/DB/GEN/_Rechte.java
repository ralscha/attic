package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Rechte {

	private int rechtid;
	private String recht;

	public _Rechte() {
		this.rechtid = 0;
		this.recht = null;
	}

	public _Rechte(int rechtid, String recht) {
		this.rechtid = rechtid;
		this.recht = recht;
	}

	public int getRechtid() {
		return rechtid;
	}

	public void setRechtid(int rechtid) {
		this.rechtid = rechtid;
	}

	public String getRecht() {
		return recht;
	}

	public void setRecht(String recht) {
		this.recht = recht;
	}


	public String toString() {
		return "_Rechte("+ rechtid + " " + recht+")";
	}

	private SoftReference benutzerrechte = null;

	public int getBenutzerrechteSize() throws SQLException, PoolPropsException {
		List t;

		if ( (benutzerrechte != null) && ((t = (List)benutzerrechte.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getBenutzerrechteTable().count("RechtId = " + getRechtid());
		}
	}

	public List getBenutzerrechte(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getBenutzerrechteTable().select("RechtId = " + getRechtid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getBenutzerrechteTable().select("RechtId = " + getRechtid());
	}

	public boolean hasBenutzerrechte() throws SQLException, PoolPropsException {
		return (getBenutzerrechteSize() > 0);
	}

	public List getBenutzerrechte() throws SQLException, PoolPropsException {
		List resultList;

		if (benutzerrechte == null) {
			resultList = ch.ess.pbroker.db.Db.getBenutzerrechteTable().select("RechtId = " + getRechtid());
			benutzerrechte = new SoftReference(resultList);
		} else {
			resultList = (List)benutzerrechte.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getBenutzerrechteTable().select("RechtId = " + getRechtid());
				benutzerrechte = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateBenutzerrechte() {
		benutzerrechte = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getBenutzerrechte();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Benutzerrechte)it.next()).invalidateRechte();
			}
		}

	}

}
