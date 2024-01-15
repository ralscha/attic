package ch.ess.lbw.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ch.ess.base.model.BaseObject;

@Entity
public class Bewertung extends BaseObject {

  private Werk werk;
  private Lieferant lieferant;
  private int quartal;
  private int jahr;
  private BigDecimal preis;
  private BigDecimal service;
  private BigDecimal teilequalitaet;
  private BigDecimal qaudit;
  private BigDecimal manuelleBewertung;
  private BigDecimal sapMengentreue;
  private BigDecimal sapLiefertreue;
  private BigDecimal gesamtNote;

  public BigDecimal getGesamtNote() {
    return gesamtNote;
  }

  public void setGesamtNote(BigDecimal gesamtNote) {
    this.gesamtNote = gesamtNote;
  }

  public int getJahr() {
    return jahr;
  }

  public void setJahr(int jahr) {
    this.jahr = jahr;
  }

  public BigDecimal getManuelleBewertung() {
    return manuelleBewertung;
  }

  public void setManuelleBewertung(BigDecimal manuelleBewertung) {
    this.manuelleBewertung = manuelleBewertung;
  }

  public int getQuartal() {
    return quartal;
  }

  public void setQuartal(int monat) {
    this.quartal = monat;
  }

  public BigDecimal getPreis() {
    return preis;
  }

  public void setPreis(BigDecimal preis) {
    this.preis = preis;
  }

  public BigDecimal getQaudit() {
    return qaudit;
  }

  public void setQaudit(BigDecimal qaudit) {
    this.qaudit = qaudit;
  }

  public BigDecimal getSapLiefertreue() {
    return sapLiefertreue;
  }

  public void setSapLiefertreue(BigDecimal sapLiefertreue) {
    this.sapLiefertreue = sapLiefertreue;
  }

  public BigDecimal getSapMengentreue() {
    return sapMengentreue;
  }

  public void setSapMengentreue(BigDecimal sapMengentreue) {
    this.sapMengentreue = sapMengentreue;
  }

  public BigDecimal getService() {
    return service;
  }

  public void setService(BigDecimal service) {
    this.service = service;
  }

  public BigDecimal getTeilequalitaet() {
    return teilequalitaet;
  }

  public void setTeilequalitaet(BigDecimal teilequalitaet) {
    this.teilequalitaet = teilequalitaet;
  }

  @ManyToOne
  @JoinColumn(name = "werkId", nullable = false)
  public Werk getWerk() {
    return werk;
  }

  public void setWerk(Werk werk) {
    this.werk = werk;
  }

  @ManyToOne
  @JoinColumn(name = "lieferantId", nullable = false)
  public Lieferant getLieferant() {
    return lieferant;
  }

  public void setLieferant(Lieferant lieferant) {
    this.lieferant = lieferant;
  }

}
