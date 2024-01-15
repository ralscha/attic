package ch.ess.business.db.obj;

import java.util.Date;

/**
 * @author sr
 */
public class MlMassnahme extends BaseObject {
  private String kriterium;
  private Integer hauptkriterium;
  private String was;
  private String wer;
  private Date wann;
  private Date erledigt;
  private Long bewertungId;

  public Integer getHauptkriterium() {
    return hauptkriterium;
  }

  public void setHauptkriterium(Integer hauptkriterium) {
    this.hauptkriterium = hauptkriterium;
  }

  public Long getBewertungId() {
    return bewertungId;
  }

  public void setBewertungId(Long bewertungId) {
    this.bewertungId = bewertungId;
  }

  public Date getErledigt() {
    return erledigt;
  }

  public void setErledigt(Date erledigt) {
    this.erledigt = erledigt;
  }

  public String getKriterium() {
    return kriterium;
  }

  public void setKriterium(String kriterium) {
    this.kriterium = kriterium;
  }

  public Date getWann() {
    return wann;
  }

  public void setWann(Date wann) {
    this.wann = wann;
  }

  public String getWas() {
    return was;
  }

  public void setWas(String was) {
    this.was = was;
  }

  public String getWer() {
    return wer;
  }

  public void setWer(String wer) {
    this.wer = wer;
  }
}