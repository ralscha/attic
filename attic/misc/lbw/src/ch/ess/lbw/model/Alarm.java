package ch.ess.lbw.model;

import java.util.Date;

import javax.persistence.Entity;

import ch.ess.base.model.BaseObject;

@Entity
public class Alarm extends BaseObject {
  private Integer targetId;
  private String typ;

  private String empfaenger;
  private Date datum;
  private String subjekt;
  private String text;

  public Date getDatum() {
    return datum;
  }

  public void setDatum(Date datum) {
    this.datum = datum;
  }

  public String getEmpfaenger() {
    return empfaenger;
  }

  public void setEmpfaenger(String empfaenger) {
    this.empfaenger = empfaenger;
  }

  public String getSubjekt() {
    return subjekt;
  }

  public void setSubjekt(String subjekt) {
    this.subjekt = subjekt;
  }

  public Integer getTargetId() {
    return targetId;
  }

  public void setTargetId(Integer targetId) {
    this.targetId = targetId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getTyp() {
    return typ;
  }

  public void setTyp(String typ) {
    this.typ = typ;
  }

}
