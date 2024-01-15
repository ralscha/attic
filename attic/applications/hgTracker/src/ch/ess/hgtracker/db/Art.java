package ch.ess.hgtracker.db;

import java.util.Set;

public class Art {

  private Integer id;
  private String spielArt;
  private Set<Spiel> spiele;
  private Boolean meisterschaft;

  public Boolean getMeisterschaft() {
    return meisterschaft;
  }

  public void setMeisterschaft(Boolean meisterschaft) {
    this.meisterschaft = meisterschaft;
  }

  public Set<Spiel> getSpiele() {
    return spiele;
  }

  public void setSpiele(Set<Spiel> spiele) {
    this.spiele = spiele;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSpielArt() {
    return spielArt;
  }

  public void setSpielArt(String spielArt) {
    this.spielArt = spielArt;
  }
}
