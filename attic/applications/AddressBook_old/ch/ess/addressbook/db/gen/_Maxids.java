package ch.ess.addressbook.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import ch.ess.addressbook.db.*;

public class _Maxids {

	private String tablename;
	private int maxid;

	public _Maxids() {
		this.tablename = null;
		this.maxid = 0;
	}

	public _Maxids(String tablename, int maxid) {
		this.tablename = tablename;
		this.maxid = maxid;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public int getMaxid() {
		return maxid;
	}

	public void setMaxid(int maxid) {
		this.maxid = maxid;
	}


	public String toString() {
		return "_Maxids("+ tablename + " " + maxid+")";
	}
}
