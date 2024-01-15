package ch.ess.hgtracker.db;

import java.util.Set;

public class Club {

  private Integer id;
  private String hgName;
  private String benutzername;
  private String passwort;
  private String praesident;
  private String strasse;
  private String plz;
  private String ort;
  private Boolean admin;
  private Set<Spiel> spiele;
  private Set<Spieler> spieler;
  private String webLogin;
  private Integer punkteMS;

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
}
