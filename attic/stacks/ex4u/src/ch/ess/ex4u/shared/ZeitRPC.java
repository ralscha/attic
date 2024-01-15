package ch.ess.ex4u.shared;

import java.io.Serializable;
import java.util.Date;

public class ZeitRPC implements Serializable {

  private Long id;

  private String periode;

  private Date vonDatum;

  private Date bisDatum;

  private Date vonZeit;

  private Date bisZeit;

  private double stunden;

  private double spesen;

  private String bemerkung;

  private boolean genehmigt;

  private Long userId;

  private Long vertragId;

  private Long zielgefaessId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPeriode() {
    return periode;
  }

  public void setPeriode(String periode) {
    this.periode = periode;
  }

  public Date getVonDatum() {
    return vonDatum;
  }

  public void setVonDatum(Date vonDatum) {
    this.vonDatum = vonDatum;
  }

  public Date getBisDatum() {
    return bisDatum;
  }

  public void setBisDatum(Date bisDatum) {
    this.bisDatum = bisDatum;
  }

  public Date getVonZeit() {
    return vonZeit;
  }

  public void setVonZeit(Date vonZeit) {
    this.vonZeit = vonZeit;
  }

  public Date getBisZeit() {
    return bisZeit;
  }

  public void setBisZeit(Date bisZeit) {
    this.bisZeit = bisZeit;
  }

  public double getStunden() {
    return stunden;
  }

  public void setStunden(double stunden) {
    this.stunden = stunden;
  }

  public double getSpesen() {
    return spesen;
  }

  public void setSpesen(double spesen) {
    this.spesen = spesen;
  }

  public String getBemerkung() {
    return bemerkung;
  }

  public void setBemerkung(String bemerkung) {
    this.bemerkung = bemerkung;
  }

  public boolean isGenehmigt() {
    return genehmigt;
  }

  public void setGenehmigt(boolean genehmigt) {
    this.genehmigt = genehmigt;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getVertragId() {
    return vertragId;
  }

  public void setVertragId(Long vertragId) {
    this.vertragId = vertragId;
  }

  public Long getZielgefaessId() {
    return zielgefaessId;
  }

  public void setZielgefaessId(Long zielgefaessId) {
    this.zielgefaessId = zielgefaessId;
  }

}
