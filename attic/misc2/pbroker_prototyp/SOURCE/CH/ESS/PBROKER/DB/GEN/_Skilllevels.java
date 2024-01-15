package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Skilllevels {

	private int skilllevelid;
	private String skilllevel;
	private int skilllevelgroupid;

	public _Skilllevels() {
		this.skilllevelid = 0;
		this.skilllevel = null;
		this.skilllevelgroupid = 0;
	}

	public _Skilllevels(int skilllevelid, String skilllevel, int skilllevelgroupid) {
		this.skilllevelid = skilllevelid;
		this.skilllevel = skilllevel;
		this.skilllevelgroupid = skilllevelgroupid;
	}

	public int getSkilllevelid() {
		return skilllevelid;
	}

	public void setSkilllevelid(int skilllevelid) {
		this.skilllevelid = skilllevelid;
	}

	public String getSkilllevel() {
		return skilllevel;
	}

	public void setSkilllevel(String skilllevel) {
		this.skilllevel = skilllevel;
	}

	public int getSkilllevelgroupid() {
		return skilllevelgroupid;
	}

	public void setSkilllevelgroupid(int skilllevelgroupid) {
		this.skilllevelgroupid = skilllevelgroupid;
	}


	public String toString() {
		return "_Skilllevels("+ skilllevelid + " " + skilllevel + " " + skilllevelgroupid+")";
	}

	private SoftReference kandidatenskills = null;

	public int getKandidatenskillsSize() throws SQLException, PoolPropsException {
		List t;

		if ( (kandidatenskills != null) && ((t = (List)kandidatenskills.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getKandidatenskillsTable().count("SkillLevelId = " + getSkilllevelid());
		}
	}

	public List getKandidatenskills(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("SkillLevelId = " + getSkilllevelid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("SkillLevelId = " + getSkilllevelid());
	}

	public boolean hasKandidatenskills() throws SQLException, PoolPropsException {
		return (getKandidatenskillsSize() > 0);
	}

	public List getKandidatenskills() throws SQLException, PoolPropsException {
		List resultList;

		if (kandidatenskills == null) {
			resultList = ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("SkillLevelId = " + getSkilllevelid());
			kandidatenskills = new SoftReference(resultList);
		} else {
			resultList = (List)kandidatenskills.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("SkillLevelId = " + getSkilllevelid());
				kandidatenskills = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateKandidatenskills() {
		kandidatenskills = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getKandidatenskills();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Kandidatenskills)it.next()).invalidateSkilllevels();
			}
		}

	}

	private SoftReference skilllevelgroups = null;

	public boolean hasSkilllevelgroups() throws SQLException, PoolPropsException {
		return (getSkilllevelgroups() != null);
	}

	public Skilllevelgroups getSkilllevelgroups() throws SQLException, PoolPropsException {

		Skilllevelgroups t;

		if (skilllevelgroups == null) {
			t = ch.ess.pbroker.db.Db.getSkilllevelgroupsTable().selectOne("SkillLevelGroupId = " + getSkilllevelgroupid());
			skilllevelgroups = new SoftReference(t);
		} else {
			t = (Skilllevelgroups)skilllevelgroups.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getSkilllevelgroupsTable().selectOne("SkillLevelGroupId = " + getSkilllevelgroupid());
				skilllevelgroups = new SoftReference(t);
			}
		}

		return t;
	}

	public void setSkilllevelgroups(Skilllevelgroups newSkilllevelgroups) throws SQLException, PoolPropsException {
		if (skilllevelgroups != null) {
			Skilllevelgroups t = (Skilllevelgroups)skilllevelgroups.get();

			if (t != null) {
				t.invalidateSkilllevels();
			}
		}

		skilllevelgroups = new SoftReference(newSkilllevelgroups);

		if (newSkilllevelgroups != null) {
			newSkilllevelgroups.invalidateSkilllevels();
			skilllevelgroupid = newSkilllevelgroups.getSkilllevelgroupid();
		}
	}

	public void invalidateSkilllevelgroups() {
		skilllevelgroups = null;
	}

}
