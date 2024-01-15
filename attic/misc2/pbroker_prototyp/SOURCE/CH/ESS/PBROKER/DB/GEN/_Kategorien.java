package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Kategorien {

	private int kategorieid;
	private String kategorie;
	private boolean hastopics;
	private boolean isonqueryform;
	private boolean ishorizontal;
	private boolean showand;
	private boolean showor;

	public _Kategorien() {
		this.kategorieid = 0;
		this.kategorie = null;
		this.hastopics = false;
		this.isonqueryform = false;
		this.ishorizontal = false;
		this.showand = false;
		this.showor = false;
	}

	public _Kategorien(int kategorieid, String kategorie, boolean hastopics, boolean isonqueryform, boolean ishorizontal, boolean showand, boolean showor) {
		this.kategorieid = kategorieid;
		this.kategorie = kategorie;
		this.hastopics = hastopics;
		this.isonqueryform = isonqueryform;
		this.ishorizontal = ishorizontal;
		this.showand = showand;
		this.showor = showor;
	}

	public int getKategorieid() {
		return kategorieid;
	}

	public void setKategorieid(int kategorieid) {
		this.kategorieid = kategorieid;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	public boolean getHastopics() {
		return hastopics;
	}

	public void setHastopics(boolean hastopics) {
		this.hastopics = hastopics;
	}

	public boolean getIsonqueryform() {
		return isonqueryform;
	}

	public void setIsonqueryform(boolean isonqueryform) {
		this.isonqueryform = isonqueryform;
	}

	public boolean getIshorizontal() {
		return ishorizontal;
	}

	public void setIshorizontal(boolean ishorizontal) {
		this.ishorizontal = ishorizontal;
	}

	public boolean getShowand() {
		return showand;
	}

	public void setShowand(boolean showand) {
		this.showand = showand;
	}

	public boolean getShowor() {
		return showor;
	}

	public void setShowor(boolean showor) {
		this.showor = showor;
	}


	public String toString() {
		return "_Kategorien("+ kategorieid + " " + kategorie + " " + hastopics + " " + isonqueryform + " " + ishorizontal + " " + showand + " " + showor+")";
	}

	private SoftReference topics = null;

	public int getTopicsSize() throws SQLException, PoolPropsException {
		List t;

		if ( (topics != null) && ((t = (List)topics.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getTopicsTable().count("KategorieId = " + getKategorieid());
		}
	}

	public List getTopics(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getTopicsTable().select("KategorieId = " + getKategorieid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getTopicsTable().select("KategorieId = " + getKategorieid());
	}

	public boolean hasTopics() throws SQLException, PoolPropsException {
		return (getTopicsSize() > 0);
	}

	public List getTopics() throws SQLException, PoolPropsException {
		List resultList;

		if (topics == null) {
			resultList = ch.ess.pbroker.db.Db.getTopicsTable().select("KategorieId = " + getKategorieid());
			topics = new SoftReference(resultList);
		} else {
			resultList = (List)topics.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getTopicsTable().select("KategorieId = " + getKategorieid());
				topics = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateTopics() {
		topics = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getTopics();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Topics)it.next()).invalidateKategorien();
			}
		}

	}

}
