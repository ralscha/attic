package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Anfrageskills {

	private int skillid;
	private int anfrageid;

	public _Anfrageskills() {
		this.skillid = 0;
		this.anfrageid = 0;
	}

	public _Anfrageskills(int skillid, int anfrageid) {
		this.skillid = skillid;
		this.anfrageid = anfrageid;
	}

	public int getSkillid() {
		return skillid;
	}

	public void setSkillid(int skillid) {
		this.skillid = skillid;
	}

	public int getAnfrageid() {
		return anfrageid;
	}

	public void setAnfrageid(int anfrageid) {
		this.anfrageid = anfrageid;
	}


	public String toString() {
		return "_Anfrageskills("+ skillid + " " + anfrageid+")";
	}
	private SoftReference skills = null;

	public boolean hasSkills() throws SQLException, PoolPropsException {
		return (getSkills() != null);
	}

	public Skills getSkills() throws SQLException, PoolPropsException {

		Skills t;

		if (skills == null) {
			t = ch.ess.pbroker.db.Db.getSkillsTable().selectOne("SkillId = " + getSkillid());
			skills = new SoftReference(t);
		} else {
			t = (Skills)skills.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getSkillsTable().selectOne("SkillId = " + getSkillid());
				skills = new SoftReference(t);
			}
		}

		return t;
	}

	public void setSkills(Skills newSkills) throws SQLException, PoolPropsException {
		if (skills != null) {
			Skills t = (Skills)skills.get();

			if (t != null) {
				t.invalidateAnfrageskills();
			}
		}

		skills = new SoftReference(newSkills);

		if (newSkills != null) {
			newSkills.invalidateAnfrageskills();
			skillid = newSkills.getSkillid();
		}
	}

	public void invalidateSkills() {
		skills = null;
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
				t.invalidateAnfrageskills();
			}
		}

		anfragen = new SoftReference(newAnfragen);

		if (newAnfragen != null) {
			newAnfragen.invalidateAnfrageskills();
			anfrageid = newAnfragen.getAnfrageid();
		}
	}

	public void invalidateAnfragen() {
		anfragen = null;
	}

}
