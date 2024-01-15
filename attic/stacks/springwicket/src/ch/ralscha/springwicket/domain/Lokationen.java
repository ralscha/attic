package ch.ralscha.springwicket.domain;

import javax.persistence.Entity;

@Entity
public class Lokationen extends AbstractEntity {

  private String name;
  private Integer adresse;
  private Integer artId;
  private String text;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAdresse() {
    return adresse;
  }

  public void setAdresse(Integer adresse) {
    this.adresse = adresse;
  }

  public Integer getArtId() {
    return artId;
  }

  public void setArtId(Integer artId) {
    this.artId = artId;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
