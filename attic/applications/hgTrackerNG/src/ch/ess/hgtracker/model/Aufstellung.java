package ch.ess.hgtracker.model;



public class Aufstellung implements Comparable<Aufstellung> {

  private int spielerId;
  private Integer punktId;
  private String nachname;
  private String vorname;
  private int jahrgang;
  private boolean ueberzaehlig;
  private Integer reihenfolge;

  public int getSpielerId() {
    return spielerId;
  }

  public void setSpielerId(int spielerId) {
    this.spielerId = spielerId;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public int getJahrgang() {
    return jahrgang;
  }

  public void setJahrgang(int jahrgang) {
    this.jahrgang = jahrgang;
  }

  public boolean isUeberzaehlig() {
    return ueberzaehlig;
  }

  public void setUeberzaehlig(boolean ueberzaehlig) {
    this.ueberzaehlig = ueberzaehlig;
  }

  public Integer getReihenfolge() {
    return reihenfolge;
  }

  public void setReihenfolge(Integer reihenfolge) {
    this.reihenfolge = reihenfolge;
  }

  public Integer getPunktId() {
    return punktId;
  }

  public void setPunktId(Integer punktId) {
    this.punktId = punktId;
  }

  @Override
  public int compareTo(Aufstellung o) {
    return getReihenfolge() - o.getReihenfolge();
  }

  
}
