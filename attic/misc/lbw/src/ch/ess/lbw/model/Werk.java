package ch.ess.lbw.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import ch.ess.base.model.BaseObject;

@Entity
public class Werk extends BaseObject {
  private String name;
  private Set<Bewertung> bewertungen = new HashSet<Bewertung>();
  private Set<UserWerk> userWerke = new HashSet<UserWerk>();
  private Set<LieferantWerk> lieferantWerke = new HashSet<LieferantWerk>();
  
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "werk")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)    
  public Set<Bewertung> getBewertungen() {
    return bewertungen;
  }

  public void setBewertungen(Set<Bewertung> bewertungen) {
    this.bewertungen = bewertungen;
  }

  @Column(nullable = false, length = 255)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "werk")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<UserWerk> getUserWerke() {
    return userWerke;
  }

  public void setUserWerke(Set<UserWerk> userWerke) {
    this.userWerke = userWerke;
  }

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "werk")
  @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
  public Set<LieferantWerk> getLieferantWerke() {
    return lieferantWerke;
  }

  public void setLieferantWerke(Set<LieferantWerk> lieferantWerke) {
    this.lieferantWerke = lieferantWerke;
  }
  
  
}
