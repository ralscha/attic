package ch.ess.hgtracker.web.spiel;

import java.util.Date;

public class SpielAnzeige {

  private Date datum;
  private String gegner;
  private String art;
  private Integer totalNr;
  private Integer totalNrGegner;
  private Integer schlagPunkte;
  private Integer schlagPunkteGegner;
  private Integer id;
  private String ort;
  private String wochentag;

  public String getWochentag() {
    return wochentag;
  }

  public void setWochentag(String wochentag) {
    this.wochentag = wochentag;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(String ort) {
    this.ort = ort;
  }

  public String getArt() {
    return art;
  }

  public void setArt(String art) {
    this.art = art;
  }

  public Date getDatum() {
    return datum;
  }

  public void setDatum(Date datum) {
    this.datum = datum;
  }

  public String getGegner() {
    return gegner;
  }

  public void setGegner(String gegner) {
    this.gegner = gegner;
  }

  public Integer getSchlagPunkteGegner() {
    return schlagPunkteGegner;
  }

  public void setSchlagPunkteGegner(Integer schlagPunkteGegner) {
    this.schlagPunkteGegner = schlagPunkteGegner;
  }

  public Integer getSchlagPunkte() {
    return schlagPunkte;
  }

  public void setSchlagPunkte(Integer schlagPunkte) {
    this.schlagPunkte = schlagPunkte;
  }

  public Integer getTotalNr() {
    return totalNr;
  }

  public void setTotalNr(Integer totalNr) {
    this.totalNr = totalNr;
  }

  public Integer getTotalNrGegner() {
    return totalNrGegner;
  }

  public void setTotalNrGegner(Integer totalNrGegner) {
    this.totalNrGegner = totalNrGegner;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
