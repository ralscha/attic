package ch.ess.hgtracker.model;

import java.util.List;

public class PunkteResult {

  private List<PunkteAnzeige> punkteAnzeige;
  private List<PunkteAnzeige> ueberzaehligePunkteAnzeige;

  private int total;
  private int total1;
  private int total2;
  private int total3;
  private int total4;
  private int total5;
  private int total6;

  private boolean meisterschaft;

  public List<PunkteAnzeige> getPunkteAnzeige() {
    return punkteAnzeige;
  }

  public void setPunkteAnzeige(List<PunkteAnzeige> punkteAnzeige) {
    this.punkteAnzeige = punkteAnzeige;
  }

  public List<PunkteAnzeige> getUeberzaehligePunkteAnzeige() {
    return ueberzaehligePunkteAnzeige;
  }

  public void setUeberzaehligePunkteAnzeige(List<PunkteAnzeige> ueberzaehligePunkteAnzeige) {
    this.ueberzaehligePunkteAnzeige = ueberzaehligePunkteAnzeige;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getTotal1() {
    return total1;
  }

  public void setTotal1(int total1) {
    this.total1 = total1;
  }

  public int getTotal2() {
    return total2;
  }

  public void setTotal2(int total2) {
    this.total2 = total2;
  }

  public int getTotal3() {
    return total3;
  }

  public void setTotal3(int total3) {
    this.total3 = total3;
  }

  public int getTotal4() {
    return total4;
  }

  public void setTotal4(int total4) {
    this.total4 = total4;
  }

  public int getTotal5() {
    return total5;
  }

  public void setTotal5(int total5) {
    this.total5 = total5;
  }

  public int getTotal6() {
    return total6;
  }

  public void setTotal6(int total6) {
    this.total6 = total6;
  }

  public boolean isMeisterschaft() {
    return meisterschaft;
  }

  public void setMeisterschaft(boolean meisterschaft) {
    this.meisterschaft = meisterschaft;
  }

}
