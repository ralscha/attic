package ch.ess.hgtracker.web.spieler;

import org.apache.struts.validator.ValidatorForm;

public class SpielerForm extends ValidatorForm {

  private String nachname;
  private String vorname;
  private String strasse;
  private String plz;
  private String ort;
  private String jahrgang;
  private String reihenfolge;
  private Boolean aufgestellt;
  private String email;
  private String telNr;
  private String mobileNr;
  private int id;
  private Boolean aktiv;

  public Boolean getAktiv() {
    return aktiv;
  }

  public void setAktiv(Boolean aktiv) {
    this.aktiv = aktiv;
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getJahrgang() {
    return jahrgang;
  }

  public void setJahrgang(String jahrgang) {
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

  public String getReihenfolge() {
    return reihenfolge;
  }

  public void setReihenfolge(String reihenfolge) {
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
}
