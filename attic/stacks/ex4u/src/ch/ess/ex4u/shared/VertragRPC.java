package ch.ess.ex4u.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

public class VertragRPC implements Serializable {

  private Long id;

  private String vertragsnummer;

  private String vertragsname;

  private Date von;

  private Date bis;

  private HashSet<Long> userIds = new HashSet<Long>();

  private HashSet<Long> zielgefaessIds = new HashSet<Long>();

  private HashSet<Long> zeitIds = new HashSet<Long>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getVertragsnummer() {
    return vertragsnummer;
  }

  public void setVertragsnummer(String vertragsnummer) {
    this.vertragsnummer = vertragsnummer;
  }

  public String getVertragsname() {
    return vertragsname;
  }

  public void setVertragsname(String vertragsname) {
    this.vertragsname = vertragsname;
  }

  public Date getVon() {
    return von;
  }

  public void setVon(Date von) {
    this.von = von;
  }

  public Date getBis() {
    return bis;
  }

  public void setBis(Date bis) {
    this.bis = bis;
  }

  public HashSet<Long> getUserIds() {
    return userIds;
  }

  public void setUserIds(HashSet<Long> userIds) {
    this.userIds = userIds;
  }

  public void addUserId(Long userId) {
    this.userIds.add(userId);
  }

  public HashSet<Long> getZielgefaessIds() {
    return zielgefaessIds;
  }

  public void setZielgefaessIds(HashSet<Long> zielgefaessIds) {
    this.zielgefaessIds = zielgefaessIds;
  }

  public void addZielgefaessId(Long zielgefaessId) {
    this.zielgefaessIds.add(zielgefaessId);
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
