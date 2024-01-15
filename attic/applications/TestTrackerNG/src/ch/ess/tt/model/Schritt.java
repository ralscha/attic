package ch.ess.tt.model;

import java.util.List;

public class Schritt {

  private int id;
  private int nummer;
  private String titel;

  private List<Fall> fallList;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getNummer() {
    return nummer;
  }

  public void setNummer(int nummer) {
    this.nummer = nummer;
  }

  public String getTitel() {
    return titel;
  }

  public void setTitel(String title) {
    this.titel = title;
  }

  public List<Fall> getFallList() {
    return fallList;
  }

  public void setFallList(List<Fall> fallList) {
    this.fallList = fallList;
  }

}
