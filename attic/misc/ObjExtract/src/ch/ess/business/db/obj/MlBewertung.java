package ch.ess.business.db.obj;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sr
 */
public class MlBewertung extends BaseObject {
  private Integer lieferantGruppeId;
  
  private Date von;
  private Date bis;
  private Date per;
  private BigDecimal preis;
  private BigDecimal produktLeistung;
  private BigDecimal qualitaet;
  private BigDecimal termine;
  private BigDecimal unternehmen;
  private BigDecimal gesamt;



  public BigDecimal getGesamt() {
    return gesamt;
  }

  public void setGesamt(BigDecimal gesamt) {
    this.gesamt = gesamt;
  }

  public BigDecimal getPreis() {
    return preis;
  }

  public void setPreis(BigDecimal preis) {
    this.preis = preis;
  }

  public BigDecimal getProduktLeistung() {
    return produktLeistung;
  }

  public void setProduktLeistung(BigDecimal produktLeistung) {
    this.produktLeistung = produktLeistung;
  }

  public BigDecimal getQualitaet() {
    return qualitaet;
  }

  public void setQualitaet(BigDecimal qualitaet) {
    this.qualitaet = qualitaet;
  }

  public BigDecimal getTermine() {
    return termine;
  }

  public void setTermine(BigDecimal termine) {
    this.termine = termine;
  }

  public BigDecimal getUnternehmen() {
    return unternehmen;
  }

  public void setUnternehmen(BigDecimal unternehmen) {
    this.unternehmen = unternehmen;
  }

  public Date getBis() {
    return bis;
  }

  public void setBis(Date bis) {
    this.bis = bis;
  }

  public Date getPer() {
    return per;
  }

  public void setPer(Date per) {
    this.per = per;
  }

  public Date getVon() {
    return von;
  }

  public void setVon(Date von) {
    this.von = von;
  }

  public Integer getLieferantGruppeId() {
    return lieferantGruppeId;
  }

  public void setLieferantGruppeId(Integer lieferantGruppeId) {
    this.lieferantGruppeId = lieferantGruppeId;
  }
}