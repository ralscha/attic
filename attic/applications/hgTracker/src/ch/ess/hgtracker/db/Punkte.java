package ch.ess.hgtracker.db;

public class Punkte {

  private Integer id;
  private Integer ries1;
  private Integer ries2;
  private Integer ries3;
  private Integer ries4;
  private Integer ries5;
  private Integer ries6;
  private Boolean nr1;
  private Boolean nr2;
  private Boolean nr3;
  private Boolean nr4;
  private Boolean nr5;
  private Boolean nr6;
  private Boolean ueberzaehligeSpieler;
  private Integer reihenfolge;
  private Spiel spiel;
  private Spieler spieler;
  private Integer totalStrich;
  private Integer rangpunkte;

  public Integer getRangpunkte() {
    return rangpunkte;
  }

  public void setRangpunkte(Integer rangpunkte) {
    this.rangpunkte = rangpunkte;
  }

  public Integer getTotalStrich() {
    return totalStrich;
  }

  public void setTotalStrich(Integer totalStrich) {
    this.totalStrich = totalStrich;
  }

  public Spiel getSpiel() {
    return spiel;
  }

  public void setSpiel(Spiel spiel) {
    this.spiel = spiel;
  }

  public Spieler getSpieler() {
    return spieler;
  }

  public void setSpieler(Spieler spieler) {
    this.spieler = spieler;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Boolean getNr1() {
    return nr1;
  }

  public void setNr1(Boolean nr1) {
    this.nr1 = nr1;
  }

  public Boolean getNr2() {
    return nr2;
  }

  public void setNr2(Boolean nr2) {
    this.nr2 = nr2;
  }

  public Boolean getNr3() {
    return nr3;
  }

  public void setNr3(Boolean nr3) {
    this.nr3 = nr3;
  }

  public Boolean getNr4() {
    return nr4;
  }

  public void setNr4(Boolean nr4) {
    this.nr4 = nr4;
  }

  public Boolean getNr5() {
    return nr5;
  }

  public void setNr5(Boolean nr5) {
    this.nr5 = nr5;
  }

  public Boolean getNr6() {
    return nr6;
  }

  public void setNr6(Boolean nr6) {
    this.nr6 = nr6;
  }

  public Integer getReihenfolge() {
    return reihenfolge;
  }

  public void setReihenfolge(Integer reihenfolge) {
    this.reihenfolge = reihenfolge;
  }

  public Integer getRies1() {
    return ries1;
  }

  public void setRies1(Integer ries1) {
    this.ries1 = ries1;
  }

  public Integer getRies2() {
    return ries2;
  }

  public void setRies2(Integer ries2) {
    this.ries2 = ries2;
  }

  public Integer getRies3() {
    return ries3;
  }

  public void setRies3(Integer ries3) {
    this.ries3 = ries3;
  }

  public Integer getRies4() {
    return ries4;
  }

  public void setRies4(Integer ries4) {
    this.ries4 = ries4;
  }

  public Integer getRies5() {
    return ries5;
  }

  public void setRies5(Integer ries5) {
    this.ries5 = ries5;
  }

  public Integer getRies6() {
    return ries6;
  }

  public void setRies6(Integer ries6) {
    this.ries6 = ries6;
  }

  public Boolean getUeberzaehligeSpieler() {
    return ueberzaehligeSpieler;
  }

  public void setUeberzaehligeSpieler(Boolean ueberzaehligeSpieler) {
    this.ueberzaehligeSpieler = ueberzaehligeSpieler;
  }
}
