package ch.ess.ex4u.shared;

import java.io.Serializable;
import java.util.HashSet;

public class ZielgefaessRPC implements Serializable {

  private Long id;

  private String nummer;

  private String beschreibung;

  private HashSet<Long> vertragIds = new HashSet<Long>();

  private HashSet<Long> zeitIds = new HashSet<Long>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNummer() {
    return nummer;
  }

  public void setNummer(String nummer) {
    this.nummer = nummer;
  }

  public String getBeschreibung() {
    return beschreibung;
  }

  public void setBeschreibung(String beschreibung) {
    this.beschreibung = beschreibung;
  }

  public HashSet<Long> getVertragIds() {
    return vertragIds;
  }

  public void setVertragIds(HashSet<Long> vertragIds) {
    this.vertragIds = vertragIds;
  }

  public void addVertragId(Long vertragId) {
    this.vertragIds.add(vertragId);
  }

  public HashSet<Long> getZeitIds() {
    return zeitIds;
  }

  public void setZeitIds(HashSet<Long> zeitIds) {
    this.zeitIds = zeitIds;
  }

  public void addZeitId(Long zeitId) {
    this.zeitIds.add(zeitId);
  }

}
