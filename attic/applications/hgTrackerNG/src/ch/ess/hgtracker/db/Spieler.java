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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="spieler")
public class Spieler implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;
  private String vorname;
  private String nachname;
  private String strasse;
  private String plz;
  private String ort;
  private Integer jahrgang;
  private Boolean aufgestellt;
  private Integer reihenfolge;
  private String email;
  private String telNr;
  private String mobileNr;
  
  @ManyToOne
  @JoinColumn(name = "clubId", nullable = false)
  private Club club;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "spieler")
  @LazyCollection(LazyCollectionOption.EXTRA)
  private Set<Punkte> punkte = new HashSet<Punkte>();
  
  private Boolean aktiv;

  public Boolean getAktiv() {
    return aktiv;
  }

  public void setAktiv(Boolean aktiv) {
    this.aktiv = aktiv;
  }

  
  public Set<Punkte> getPunkte() {
    return punkte;
  }

  public void setPunkte(Set<Punkte> punkte) {
    this.punkte = punkte;
  }


  public Club getClub() {
    return club;
  }

  public void setClub(Club club) {
    this.club = club;
  }

  public Boolean getAufgestellt() {
    return aufgestellt;
  }

  public void setAufgestellt(Boolean aufgestellt) {
    this.aufgestellt = aufgestellt;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getJahrgang() {
    return jahrgang;
  }

  public void setJahrgang(Integer jahrgang) {
    this.jahrgang = jahrgang;
  }

  public String getMobileNr() {
    return mobileNr;
  }

  public void setMobileNr(String mobileNr) {
    this.mobileNr = mobileNr;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(String ort) {
    this.ort = ort;
  }

  public String getPlz() {
    return plz;
  }

  public void setPlz(String plz) {
    this.plz = plz;
  }

  public Integer getReihenfolge() {
    return reihenfolge;
  }

  public void setReihenfolge(Integer reihenfolge) {
    this.reihenfolge = reihenfolge;
  }

  public String getStrasse() {
    return strasse;
  }

  public void setStrasse(String strasse) {
    this.strasse = strasse;
  }

  public String getTelNr() {
    return telNr;
  }

  public void setTelNr(String telNr) {
    this.telNr = telNr;
  }

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
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
    if (!(obj instanceof Spieler)) {
      return false;
    }

    Spieler other = (Spieler)obj;
    if ((other.getId() == null) || (getId() == null)) {
      return super.equals(obj);
    }

    return other.getId().equals(getId());

  }
}
