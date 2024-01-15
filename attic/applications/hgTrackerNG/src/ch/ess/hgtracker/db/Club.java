package ch.ess.hgtracker.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "club")
public class Club implements Serializable {

  @Id
  @GeneratedValue
  private Integer id;
  private String hgName;
  private String benutzername;
  private String passwort;
  private String praesident;
  private String strasse;
  private String plz;
  private String ort;
  private Boolean admin;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "club")
  @LazyCollection(LazyCollectionOption.EXTRA)
  private Set<Spiel> spiele = new HashSet<Spiel>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "club")
  @LazyCollection(LazyCollectionOption.EXTRA)
  private Set<Spieler> spieler = new HashSet<Spieler>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "club")
  @LazyCollection(LazyCollectionOption.EXTRA)
  private Set<Art> art = new HashSet<Art>();

  private String webLogin;
  private Integer punkteMS;

  @Transient
  private String sessionId;

  public Integer getPunkteMS() {
    return punkteMS;
  }

  public void setPunkteMS(Integer punkteMS) {
    this.punkteMS = punkteMS;
  }

  public String getWebLogin() {
    return webLogin;
  }

  public void setWebLogin(String webLogin) {
    this.webLogin = webLogin;
  }

  public Set<Spiel> getSpiele() {
    return spiele;
  }

  public void setSpiele(Set<Spiel> spiele) {
    this.spiele = spiele;
  }

  public Set<Spieler> getSpieler() {
    return spieler;
  }

  public void setSpieler(Set<Spieler> spieler) {
    this.spieler = spieler;
  }

  public Set<Art> getArt() {
    return art;
  }

  public void setArt(Set<Art> art) {
    this.art = art;
  }

  public Boolean getAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  public String getBenutzername() {
    return benutzername;
  }

  public void setBenutzername(String benutzername) {
    this.benutzername = benutzername;
  }

  public String getHgName() {
    return hgName;
  }

  public void setHgName(String hgName) {
    this.hgName = hgName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(String ort) {
    this.ort = ort;
  }

  public String getPasswort() {
    return passwort;
  }

  public void setPasswort(String passwort) {
    this.passwort = passwort;
  }

  public String getPlz() {
    return plz;
  }

  public void setPlz(String plz) {
    this.plz = plz;
  }

  public String getPraesident() {
    return praesident;
  }

  public void setPraesident(String praesident) {
    this.praesident = praesident;
  }

  public String getStrasse() {
    return strasse;
  }

  public void setStrasse(String strasse) {
    this.strasse = strasse;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
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
    if (!(obj instanceof Club)) {
      return false;
    }

    Club other = (Club)obj;
    if ((other.getId() == null) || (getId() == null)) {
      return super.equals(obj);
    }

    return other.getId().equals(getId());

  }
}
