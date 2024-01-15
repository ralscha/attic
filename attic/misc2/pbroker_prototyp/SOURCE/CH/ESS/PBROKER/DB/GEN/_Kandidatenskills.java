package ch.ess.pbroker.db.gen;

import java.sql.*;
import java.util.*;
import java.lang.ref.SoftReference;
import com.codestudio.util.PoolPropsException;
import ch.ess.pbroker.db.*;

public class _Kandidatenskills {

	private int kandidatid;
	private int skillid;
	private int skilllevelid;
	private double experience;

	public _Kandidatenskills() {
		this.kandidatid = 0;
		this.skillid = 0;
		this.skilllevelid = 0;
		this.experience = 0.0;
	}

	public _Kandidatenskills(int kandidatid, int skillid, int skilllevelid, double experience) {
		this.kandidatid = kandidatid;
		this.skillid = skillid;
		this.skilllevelid = skilllevelid;
		this.experience = experience;
	}

	public int getKandidatid() {
		return kandidatid;
	}

	public void setKandidatid(int kandidatid) {
		this.kandidatid = kandidatid;
	}

	public int getSkillid() {
		return skillid;
	}

	public void setSkillid(int skillid) {
		this.skillid = skillid;
	}

	public int getSkilllevelid() {
		return skilllevelid;
	}

	public void setSkilllevelid(int skilllevelid) {
		this.skilllevelid = skilllevelid;
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
	}


	public String toString() {
		return "_Kandidatenskills("+ kandidatid + " " + skillid + " " + skilllevelid + " " + experience+")";
	}
	private SoftReference kandidaten = null;

	public boolean hasKandidaten() throws SQLException, PoolPropsException {
		return (getKandidaten() != null);
	}

	public Kandidaten getKandidaten() throws SQLException, PoolPropsException {

		Kandidaten t;

		if (kandidaten == null) {
			t = ch.ess.pbroker.db.Db.getKandidatenTable().selectOne("KandidatId = " + getKandidatid());
			kandidaten = new SoftReference(t);
		} else {
			t = (Kandidaten)kandidaten.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getKandidatenTable().selectOne("KandidatId = " + getKandidatid());
				kandidaten = new SoftReference(t);
			}
		}

		return t;
	}

	public void setKandidaten(Kandidaten newKandidaten) throws SQLException, PoolPropsException {
		if (kandidaten != null) {
			Kandidaten t = (Kandidaten)kandidaten.get();

			if (t != null) {
				t.invalidateKandidatenskills();
			}
		}

		kandidaten = new SoftReference(newKandidaten);

		if (newKandidaten != null) {
			newKandidaten.invalidateKandidatenskills();
			kandidatid = newKandidaten.getKandidatid();
		}
	}

	public void invalidateKandidaten() {
		kandidaten = null;
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
				t.invalidateKandidatenskills();
			}
		}

		skills = new SoftReference(newSkills);

		if (newSkills != null) {
			newSkills.invalidateKandidatenskills();
			skillid = newSkills.getSkillid();
		}
	}

	public void invalidateSkills() {
		skills = null;
	}

	private SoftReference skilllevels = null;

	public boolean hasSkilllevels() throws SQLException, PoolPropsException {
		return (getSkilllevels() != null);
	}

	public Skilllevels getSkilllevels() throws SQLException, PoolPropsException {

		Skilllevels t;

		if (skilllevels == null) {
			t = ch.ess.pbroker.db.Db.getSkilllevelsTable().selectOne("SkillLevelId = " + getSkilllevelid());
			skilllevels = new SoftReference(t);
		} else {
			t = (Skilllevels)skilllevels.get();

			if (t == null) {
				t = ch.ess.pbroker.db.Db.getSkilllevelsTable().selectOne("SkillLevelId = " + getSkilllevelid());
				skilllevels = new SoftReference(t);
			}
		}

		return t;
	}

	public void setSkilllevels(Skilllevels newSkilllevels) throws SQLException, PoolPropsException {
		if (skilllevels != null) {
			Skilllevels t = (Skilllevels)skilllevels.get();

			if (t != null) {
				t.invalidateKandidatenskills();
			}
		}

		skilllevels = new SoftReference(newSkilllevels);

		if (newSkilllevels != null) {
			newSkilllevels.invalidateKandidatenskills();
			skilllevelid = newSkilllevels.getSkilllevelid();
		}
	}

	public void invalidateSkilllevels() {
		skilllevels = null;
	}

}
