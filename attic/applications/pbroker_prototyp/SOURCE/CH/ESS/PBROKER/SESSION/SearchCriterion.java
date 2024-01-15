package ch.ess.pbroker.session;

import java.util.*;
import ch.ess.pbroker.db.*;
import java.sql.*;
import com.codestudio.util.*;

public class SearchCriterion {
  
  private Set kannSkillsSet;
	private Set mussSkillsSet;
  private Set tgKannSet;
  private Set tgMussSet;
  private String quicksearch;

  public SearchCriterion() {
    kannSkillsSet = new HashSet();
    mussSkillsSet = new HashSet();
    tgKannSet = new HashSet();
    tgMussSet = new HashSet();
    quicksearch = null;
  }
  
  public void reset() {
    clearAll();
    quicksearch = null;
  }

  public boolean isShowScore() {
    if (quicksearch != null)
      return false;
    else 
      return true;
  }

  public void setQuicksearch(String searchstr) {
    quicksearch = searchstr;
  }
  
  public String getQuicksearch() {
    return quicksearch;
  }

  public void addKannSkill(int skillid) throws SQLException, PoolPropsException {

    Skills skill = Db.getSkillsTable().selectOne("SkillId = " + skillid);
    Topics topic = skill.getTopics();
    if (!topic.getTopic().equals("Tätigkeitsgebiete"))
      kannSkillsSet.add(new Integer(skillid));
    else
      tgKannSet.add(new Integer(skillid));
  }

  public void addMussSkill(int skillid) throws SQLException, PoolPropsException {

    Skills skill = Db.getSkillsTable().selectOne("SkillId = " + skillid);
    Topics topic = skill.getTopics();
    if (!topic.getTopic().equals("Tätigkeitsgebiete"))
      mussSkillsSet.add(new Integer(skillid));
    else
      tgMussSet.add(new Integer(skillid));
  }

  public boolean containsMussSkill(int skillid) {
    Integer si = new Integer(skillid);
    return mussSkillsSet.contains(si) || tgMussSet.contains(si);
  }

  public boolean containsKannSkill(int skillid) {
    Integer si = new Integer(skillid);
    return kannSkillsSet.contains(si) || tgKannSet.contains(si);
  }

  public void clearAll() {
    kannSkillsSet.clear();
    mussSkillsSet.clear();
    tgMussSet.clear();
    tgKannSet.clear();
  }

  public void removeSkill(int skillid) {
    Integer id = new Integer(skillid);
    kannSkillsSet.remove(id);
    mussSkillsSet.remove(id);
    tgMussSet.remove(id);
    tgKannSet.remove(id);
  }

  public Set getKannSkillsSet() {
    return kannSkillsSet;
  }

  public Set getMussSkillsSet() {
    return mussSkillsSet;
  }

  public Set getTgMussSkillSet() {
    return tgMussSet;
  }

  public Set getTgKannSkillSet() {
    return tgKannSet;
  }

  public String getMussSkillsString() {
    StringBuffer sb = new StringBuffer();
    Set tmpSet = new HashSet();
    tmpSet.addAll(mussSkillsSet);
    tmpSet.addAll(tgMussSet);

    Iterator it = tmpSet.iterator();
    while(it.hasNext()) {
      sb.append((Integer)it.next());
      if (it.hasNext())
        sb.append(",");
    }
    return sb.toString();  
  }

  public String getKannSkillsString() {
    StringBuffer sb = new StringBuffer();

    Set tmpSet = new HashSet();
    tmpSet.addAll(kannSkillsSet);
    tmpSet.addAll(tgKannSet);

    Iterator it = tmpSet.iterator();
    while(it.hasNext()) {
      sb.append((Integer)it.next());
      if (it.hasNext())
        sb.append(",");
    }
    return sb.toString();
  }

  public String getTaetigkeitsgebiete() throws SQLException, PoolPropsException {

    StringBuffer sb = new StringBuffer();
    Set tmpSet = new HashSet();
    tmpSet.addAll(tgKannSet);
    tmpSet.addAll(tgMussSet);

    Iterator it = tmpSet.iterator();
    while(it.hasNext()) {
      Skills skill = Db.getSkillsTable().selectOne("SkillId = " + (Integer)it.next());
      sb.append(skill.getSkill());
      if (it.hasNext())
        sb.append(", ");
    }
    return sb.toString();    
  }

  public String getKannDescription() throws SQLException, PoolPropsException {
    StringBuffer sb = new StringBuffer();
    Iterator it = kannSkillsSet.iterator();
    while(it.hasNext()) {
      Skills skill = Db.getSkillsTable().selectOne("SkillId = " + (Integer)it.next());
      sb.append(skill.getSkill());
      if (it.hasNext())
        sb.append(", ");
    }
    return sb.toString();
  }

  public String getMussDescription() throws SQLException, PoolPropsException {
    StringBuffer sb = new StringBuffer();
    Iterator it = mussSkillsSet.iterator();
    while(it.hasNext()) {
      Skills skill = Db.getSkillsTable().selectOne("SkillId = " + (Integer)it.next());
      sb.append(skill.getSkill());
      if (it.hasNext())
        sb.append(", ");
    }
    return sb.toString();  
  }

}