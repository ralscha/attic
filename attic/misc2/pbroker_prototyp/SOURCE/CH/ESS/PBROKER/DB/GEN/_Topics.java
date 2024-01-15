package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Topics {

	private int topicid;
	private String topic;
	private int kategorieid;
	private int skilllevelgroupid;
	private boolean hasskilllevelfield;
	private boolean hasexperiencefield;
	private boolean allownewskills;

	public _Topics() {
		this.topicid = 0;
		this.topic = null;
		this.kategorieid = 0;
		this.skilllevelgroupid = 0;
		this.hasskilllevelfield = false;
		this.hasexperiencefield = false;
		this.allownewskills = false;
	}

	public _Topics(int topicid, String topic, int kategorieid, int skilllevelgroupid, boolean hasskilllevelfield, boolean hasexperiencefield, boolean allownewskills) {
		this.topicid = topicid;
		this.topic = topic;
		this.kategorieid = kategorieid;
		this.skilllevelgroupid = skilllevelgroupid;
		this.hasskilllevelfield = hasskilllevelfield;
		this.hasexperiencefield = hasexperiencefield;
		this.allownewskills = allownewskills;
	}

	public int getTopicid() {
		return topicid;
	}

	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getKategorieid() {
		return kategorieid;
	}

	public void setKategorieid(int kategorieid) {
		this.kategorieid = kategorieid;
	}

	public int getSkilllevelgroupid() {
		return skilllevelgroupid;
	}

	public void setSkilllevelgroupid(int skilllevelgroupid) {
		this.skilllevelgroupid = skilllevelgroupid;
	}

	public boolean getHasskilllevelfield() {
		return hasskilllevelfield;
	}

	public void setHasskilllevelfield(boolean hasskilllevelfield) {
		this.hasskilllevelfield = hasskilllevelfield;
	}

	public boolean getHasexperiencefield() {
		return hasexperiencefield;
	}

	public void setHasexperiencefield(boolean hasexperiencefield) {
		this.hasexperiencefield = hasexperiencefield;
	}

	public boolean getAllownewskills() {
		return allownewskills;
	}

	public void setAllownewskills(boolean allownewskills) {
		this.allownewskills = allownewskills;
	}


	public String toString() {
		return "_Topics("+ topicid + " " + topic + " " + kategorieid + " " + skilllevelgroupid + " " + hasskilllevelfield + " " + hasexperiencefield + " " + allownewskills+")";
	}

	private SoftReference skills = null;

	public int getSkillsSize() throws SQLException, PoolPropsException {
		List t;

		if ( (skills != null) && ((t = (List)skills.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getSkillsTable().count("TopicId = " + getTopicid());
		}
	}

	public List getSkills(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getSkillsTable().select("TopicId = " + getTopicid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getSkillsTable().select("TopicId = " + getTopicid());
	}

	public boolean hasSkills() throws SQLException, PoolPropsException {
		return (getSkillsSize() > 0);
	}

	public List getSkills() throws SQLException, PoolPropsException {
		List resultList;

		if (skills == null) {
			resultList = ch.ess.pbroker.db.Db.getSkillsTable().select("TopicId = " + getTopicid());
			skills = new SoftReference(resultList);
		} else {
			resultList = (List)skills.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getSkillsTable().select("TopicId = " + getTopicid());
				skills = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateSkills() {
		skills = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getSkills();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Skills)it.next()).invalidateTopics();
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
				t.invalidateTopics();
			}
		}

		skilllevelgroups = new SoftReference(newSkilllevelgroups);

		if (newSkilllevelgroups != null) {
			newSkilllevelgroups.invalidateTopics();
			skilllevelgroupid = newSkilllevelgroups.getSkilllevelgroupid();
		}
	}

	public void invalidateSkilllevelgroups() {
		skilllevelgroups = null;
	}

	private SoftReference kategorien = null;

	public boolean hasKategorien() throws SQLException, PoolPropsException {
		return (getKategorien() != null);
	}

	public Kategorien getKategorien() throws SQLException, PoolPropsException {

		Kategorien t;

		if (kategorien == null) {
			t = ch.ess.pbroker.db.Db.getKategorienTable().selectOne("KategorieId = " + getKategorieid());
			kategorien = new SoftReference(t);
		} else {
			t = (Kategorien)kategorien.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getKategorienTable().selectOne("KategorieId = " + getKategorieid());
				kategorien = new SoftReference(t);
			}
		}

		return t;
	}

	public void setKategorien(Kategorien newKategorien) throws SQLException, PoolPropsException {
		if (kategorien != null) {
			Kategorien t = (Kategorien)kategorien.get();

			if (t != null) {
				t.invalidateTopics();
			}
		}

		kategorien = new SoftReference(newKategorien);

		if (newKategorien != null) {
			newKategorien.invalidateTopics();
			kategorieid = newKategorien.getKategorieid();
		}
	}

	public void invalidateKategorien() {
		kategorien = null;
	}

}
