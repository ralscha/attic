package ch.ess.hgtracker.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="art")
public class Art implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;
  private String spielArt;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "art")
  @LazyCollection(LazyCollectionOption.EXTRA)
  private Set<Spiel> spiele = new HashSet<Spiel>();
  private Boolean meisterschaft;

  @ManyToOne
  @JoinColumn(name = "clubId")
  private Club club;
  
  @Transient
  private int anzahlSpiele;

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

  public int getAnzahlSpiele() {
    return anzahlSpiele;
  }

  public void setAnzahlSpiele(int anzahlSpiele) {
    this.anzahlSpiele = anzahlSpiele;
  }

  public Club getClub() {
    return club;
  }

  public void setClub(Club club) {
    this.club = club;
  }
  
  @Override
  public int hashCode() {
    if (id != null) {
      return id.hashCode();
    }
    return super.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof Art)) {
      return false;
    }

    Art other = (Art)obj;
    if ((other.getId() == null) || (getId() == null)) {
      return super.equals(obj);
    }

    return other.getId().equals(getId());

  }
}
