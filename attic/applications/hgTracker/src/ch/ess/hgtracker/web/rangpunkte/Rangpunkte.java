package ch.ess.hgtracker.web.rangpunkte;

import java.math.BigDecimal;

public class Rangpunkte {

  private String nachname;
  private String vorname;
  private Integer jahrgang;
  private Integer punkte;
  private Integer striche;
  private BigDecimal schnitt;
  private Integer punkteProSpiel;
  private Integer rangpunkte;

  public Integer getPunkteProSpiel() {
    return punkteProSpiel;
  }

  public void setPunkteProSpiel(Integer punkteProSpiel) {
    this.punkteProSpiel = punkteProSpiel;
  }

  public Integer getRangpunkte() {
    return rangpunkte;
  }

  public void setRangpunkte(Integer rangpunkte) {
    this.rangpunkte = rangpunkte;
  }

  public Integer getJahrgang() {
    return jahrgang;
  }

  public void setJahrgang(Integer jahrgang) {
    this.jahrgang = jahrgang;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  public Integer getPunkte() {
    return punkte;
  }

  public void setPunkte(Integer punkte) {
    this.punkte = punkte;
  }

  public BigDecimal getSchnitt() {
    return schnitt;
  }

  public void setSchnitt(BigDecimal schnitt) {
    this.schnitt = schnitt;
  }

  public Integer getStriche() {
    return striche;
  }

  public void setStriche(Integer striche) {
    this.striche = striche;
  }

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }
}
