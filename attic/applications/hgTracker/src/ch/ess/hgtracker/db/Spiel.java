package ch.ess.hgtracker.db;

import java.util.Date;
import java.util.Set;

public class Spiel {

  private Integer id;
  private Date datum;
  private String ort;
  private String gegner;
  private String kampfrichter;
  private String kampfrichterGegner;
  private Integer totalNr;
  private Integer schlagPunkteGegner;
  private Club club;
  private Art art;
  private Set<Punkte> punkte;
  private Integer totalNrGegner;
  private Integer schlagPunkte;

  public Integer getSchlagPunkte() {
    return schlagPunkte;
  }

  public void setSchlagPunkte(Integer schlagPunkte) {
    this.schlagPunkte = schlagPunkte;
  }

  public Integer getTotalNrGegner() {
    return totalNrGegner;
  }

  public void setTotalNrGegner(Integer totalNrGegner) {
    this.totalNrGegner = totalNrGegner;
  }

  public Set<Punkte> getPunkte() {
    return punkte;
  }

  public void setPunkte(Set<Punkte> punkte) {
    this.punkte = punkte;
  }

  public Art getArt() {
    return art;
  }

  public void setArt(Art art) {
    this.art = art;
  }

  public Club getClub() {
    return club;
  }

  public void setClub(Club club) {
    this.club = club;
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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getKampfrichter() {
    return kampfrichter;
  }

  public void setKampfrichter(String kampfrichter) {
    this.kampfrichter = kampfrichter;
  }

  public String getKampfrichterGegner() {
    return kampfrichterGegner;
  }

  public void setKampfrichterGegner(String kampfrichterGegner) {
    this.kampfrichterGegner = kampfrichterGegner;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(String ort) {
    this.ort = ort;
  }

  public Integer getSchlagPunkteGegner() {
    return schlagPunkteGegner;
  }

  public void setSchlagPunkteGegner(Integer schlagPunkteGegner) {
    this.schlagPunkteGegner = schlagPunkteGegner;
  }

  public Integer getTotalNr() {
    return totalNr;
  }

  public void setTotalNr(Integer totalNr) {
    this.totalNr = totalNr;
  }
}
