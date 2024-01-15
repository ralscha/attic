package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Skills {

	private String skill;
	private int topicid;
	private int skillid;

	public _Skills() {
		this.skill = null;
		this.topicid = 0;
		this.skillid = 0;
	}

	public _Skills(String skill, int topicid, int skillid) {
		this.skill = skill;
		this.topicid = topicid;
		this.skillid = skillid;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public int getTopicid() {
		return topicid;
	}

	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}

	public int getSkillid() {
		return skillid;
	}

	public void setSkillid(int skillid) {
		this.skillid = skillid;
	}


	public String toString() {
		return "_Skills("+ skill + " " + topicid + " " + skillid+")";
	}

	private SoftReference kandidatenskills = null;

	public int getKandidatenskillsSize() throws SQLException, PoolPropsException {
		List t;

		if ( (kandidatenskills != null) && ((t = (List)kandidatenskills.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getKandidatenskillsTable().count("SkillId = " + getSkillid());
		}
	}

	public List getKandidatenskills(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("SkillId = " + getSkillid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("SkillId = " + getSkillid());
	}

	public boolean hasKandidatenskills() throws SQLException, PoolPropsException {
		return (getKandidatenskillsSize() > 0);
	}

	public List getKandidatenskills() throws SQLException, PoolPropsException {
		List resultList;

		if (kandidatenskills == null) {
			resultList = ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("SkillId = " + getSkillid());
			kandidatenskills = new SoftReference(resultList);
		} else {
			resultList = (List)kandidatenskills.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getKandidatenskillsTable().select("SkillId = " + getSkillid());
				kandidatenskills = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateKandidatenskills() {
		kandidatenskills = null;
	}


	private SoftReference anfrageskills = null;

	public int getAnfrageskillsSize() throws SQLException, PoolPropsException {
		List t;

		if ( (anfrageskills != null) && ((t = (List)anfrageskills.get()) != null) ) {
			return t.size();
		} else {
			return ch.ess.pbroker.db.Db.getAnfrageskillsTable().count("SkillId = " + getSkillid());
		}
	}

	public List getAnfrageskills(String whereClause) throws SQLException, PoolPropsException {
		if (whereClause != null)
			return ch.ess.pbroker.db.Db.getAnfrageskillsTable().select("SkillId = " + getSkillid() + " AND " + whereClause);
		else
			return ch.ess.pbroker.db.Db.getAnfrageskillsTable().select("SkillId = " + getSkillid());
	}

	public boolean hasAnfrageskills() throws SQLException, PoolPropsException {
		return (getAnfrageskillsSize() > 0);
	}

	public List getAnfrageskills() throws SQLException, PoolPropsException {
		List resultList;

		if (anfrageskills == null) {
			resultList = ch.ess.pbroker.db.Db.getAnfrageskillsTable().select("SkillId = " + getSkillid());
			anfrageskills = new SoftReference(resultList);
		} else {
			resultList = (List)anfrageskills.get();

			if (resultList == null) {
				resultList = ch.ess.pbroker.db.Db.getAnfrageskillsTable().select("SkillId = " + getSkillid());
				anfrageskills = new SoftReference(resultList);
			}
		}
		return resultList;
	}

	public void invalidateAnfrageskills() {
		anfrageskills = null;
	}

	public void invalidateBackRelations() throws SQLException, PoolPropsException {
		List resultList = getKandidatenskills();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Kandidatenskills)it.next()).invalidateSkills();
			}
		}

		resultList = getAnfrageskills();
		if (resultList != null) {
			Iterator it = resultList.iterator();
			while(it.hasNext()) {
				((Anfrageskills)it.next()).invalidateSkills();
			}
		}

	}

	private SoftReference topics = null;

	public boolean hasTopics() throws SQLException, PoolPropsException {
		return (getTopics() != null);
	}

	public Topics getTopics() throws SQLException, PoolPropsException {

		Topics t;

		if (topics == null) {
			t = ch.ess.pbroker.db.Db.getTopicsTable().selectOne("TopicId = " + getTopicid());
			topics = new SoftReference(t);
		} else {
			t = (Topics)topics.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getTopicsTable().selectOne("TopicId = " + getTopicid());
				topics = new SoftReference(t);
			}
		}

		return t;
	}

	public void setTopics(Topics newTopics) throws SQLException, PoolPropsException {
		if (topics != null) {
			Topics t = (Topics)topics.get();

			if (t != null) {
				t.invalidateSkills();
			}
		}

		topics = new SoftReference(newTopics);

		if (newTopics != null) {
			newTopics.invalidateSkills();
			topicid = newTopics.getTopicid();
		}
	}

	public void invalidateTopics() {
		topics = null;
	}

}
