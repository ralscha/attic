package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Skilllevelgroups {

	private int skilllevelgroupid;
	private String skilllevelgroup;

	public _Skilllevelgroups() {
		this.skilllevelgroupid = 0;
		this.skilllevelgroup = null;
	}

	public _Skilllevelgroups(int skilllevelgroupid, String skilllevelgroup) {
		this.skilllevelgroupid = skilllevelgroupid;
		this.skilllevelgroup = skilllevelgroup;
	}

	public int getSkilllevelgroupid() {
		return skilllevelgroupid;
	}

	public void setSkilllevelgroupid(int skilllevelgroupid) {
		this.skilllevelgroupid = skilllevelgroupid;
	}

	public String getSkilllevelgroup() {
		return skilllevelgroup;
	}

	public void setSkilllevelgroup(String skilllevelgroup) {
		this.skilllevelgroup = skilllevelgroup;
	}


	public String toString() {
		return "_Skilllevelgroups("+ skilllevelgroupid + " " + skilllevelgroup+")";
	}

	private SoftReference skilllevels = null;

	public int getSkilllevelsSize() throws SQLException, PoolPropsException {
		List t;

		if ( (skilllevels != null) && ((t = (List)skilllevels.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getSkilllevelsTable().count("SkillLevelGroupId = " + getSkilllevelgroupid());
		}
	}

	public List getSkilllevels(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getSkilllevelsTable().select("SkillLevelGroupId = " + getSkilllevelgroupid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getSkilllevelsTable().select("SkillLevelGroupId = " + getSkilllevelgroupid());
	}

	public boolean hasSkilllevels() throws SQLException, PoolPropsException {
		return (getSkilllevelsSize() > 0);
	}

	public List getSkilllevels() throws SQLException, PoolPropsException {
		List resultList;

		if (skilllevels == null) {
			resultList = ch.ess.pbroker.db.Db.getSkilllevelsTable().select("SkillLevelGroupId = " + getSkilllevelgroupid());
			skilllevels = new SoftReference(resultList);
		} else {
			resultList = (List)skilllevels.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getSkilllevelsTable().select("SkillLevelGroupId = " + getSkilllevelgroupid());
				skilllevels = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateSkilllevels() {
		skilllevels = null;
	}


	private SoftReference topics = null;

	public int getTopicsSize() throws SQLException, PoolPropsException {
		List t;

		if ( (topics != null) && ((t = (List)topics.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getTopicsTable().count("SkillLevelGroupId = " + getSkilllevelgroupid());
		}
	}

	public List getTopics(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getTopicsTable().select("SkillLevelGroupId = " + getSkilllevelgroupid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getTopicsTable().select("SkillLevelGroupId = " + getSkilllevelgroupid());
	}

	public boolean hasTopics() throws SQLException, PoolPropsException {
		return (getTopicsSize() > 0);
	}

	public List getTopics() throws SQLException, PoolPropsException {
		List resultList;

		if (topics == null) {
			resultList = ch.ess.pbroker.db.Db.getTopicsTable().select("SkillLevelGroupId = " + getSkilllevelgroupid());
			topics = new SoftReference(resultList);
		} else {
			resultList = (List)topics.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getTopicsTable().select("SkillLevelGroupId = " + getSkilllevelgroupid());
				topics = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateTopics() {
		topics = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getSkilllevels();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Skilllevels)it.next()).invalidateSkilllevelgroups();
			}
		}

		resultList = getTopics();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Topics)it.next()).invalidateSkilllevelgroups();
			}
		}

	}

}
