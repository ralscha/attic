package ch.ess.hgtracker.model;


public class SpielForm {

  private int id;
  private String datum;
  private String ort;
  private String gegner;
  private String kampfrichter;
  private String kampfrichterGegner;
  private String totalNr;
  private String schlagPunkteGegner;
  private String spielArt;
  private Boolean ries1nr;
  private Boolean ries2nr;
  private Boolean ries3nr;
  private Boolean ries4nr;
  private Boolean ries5nr;
  private Boolean ries6nr;
  private boolean showries1nr;
  private boolean showries2nr;
  private boolean showries3nr;
  private boolean showries4nr;
  private boolean showries5nr;
  private boolean showries6nr;
  private int punkteId;
  private String name;
  private int totalNrAlt;
  private String totalNrHeim;
  private String minuten;
  private String stunden;

  public String getMinuten() {
    return minuten;
  }

  public void setMinuten(String minuten) {
    this.minuten = minuten;
  }

  public String getStunden() {
    return stunden;
  }

  public void setStunden(String stunden) {
    this.stunden = stunden;
  }

  public String getTotalNrHeim() {
    return totalNrHeim;
  }

  public void setTotalNrHeim(String totalNrHeim) {
    this.totalNrHeim = totalNrHeim;
  }

  public int getTotalNrAlt() {
    return totalNrAlt;
  }

  public void setTotalNrAlt(int totalNrAlt) {
    this.totalNrAlt = totalNrAlt;
  }

  public boolean getShowries1nr() {
    return showries1nr;
  }

  public void setShowries1nr(boolean showries1nr) {
    this.showries1nr = showries1nr;
  }

  public boolean getShowries2nr() {
    return showries2nr;
  }

  public void setShowries2nr(boolean showries2nr) {
    this.showries2nr = showries2nr;
  }

  public boolean getShowries3nr() {
    return showries3nr;
  }

  public void setShowries3nr(boolean showries3nr) {
    this.showries3nr = showries3nr;
  }

  public boolean getShowries4nr() {
    return showries4nr;
  }

  public void setShowries4nr(boolean showries4nr) {
    this.showries4nr = showries4nr;
  }

  public boolean getShowries5nr() {
    return showries5nr;
  }

  public void setShowries5nr(boolean showries5nr) {
    this.showries5nr = showries5nr;
  }

  public boolean getShowries6nr() {
    return showries6nr;
  }

  public void setShowries6nr(boolean showries6nr) {
    this.showries6nr = showries6nr;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPunkteId() {
    return punkteId;
  }

  public void setPunkteId(int punkteId) {
    this.punkteId = punkteId;
  }

  public Boolean getRies1nr() {
    return ries1nr;
  }

  public void setRies1nr(Boolean ries1nr) {
    this.ries1nr = ries1nr;
  }

  public Boolean getRies2nr() {
    return ries2nr;
  }

  public void setRies2nr(Boolean ries2nr) {
    this.ries2nr = ries2nr;
  }

  public Boolean getRies3nr() {
    return ries3nr;
  }

  public void setRies3nr(Boolean ries3nr) {
    this.ries3nr = ries3nr;
  }

  public Boolean getRies4nr() {
    return ries4nr;
  }

  public void setRies4nr(Boolean ries4nr) {
    this.ries4nr = ries4nr;
  }

  public Boolean getRies5nr() {
    return ries5nr;
  }

  public void setRies5nr(Boolean ries5nr) {
    this.ries5nr = ries5nr;
  }

  public Boolean getRies6nr() {
    return ries6nr;
  }

  public void setRies6nr(Boolean ries6nr) {
    this.ries6nr = ries6nr;
  }

  public String getDatum() {
    return datum;
  }

  public void setDatum(String datum) {
    this.datum = datum;
  }

  public String getGegner() {
    return gegner;
  }

  public void setGegner(String gegner) {
    this.gegner = gegner;
  }

  public int getId() {
    return id;
  }

  public String getSchlagPunkteGegner() {
    return schlagPunkteGegner;
  }

  public void setSchlagPunkteGegner(String schlagPunkteGegner) {
    this.schlagPunkteGegner = schlagPunkteGegner;
  }

  public String getTotalNr() {
    return totalNr;
  }

  public void setTotalNr(String totalNr) {
    this.totalNr = totalNr;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getKampfrichter() {
    return kampfrichter;
  }

  public void setKampfrichter(String kampfrichter) {
    this.kampfrichter = kampfrichter;
  }

  public String getKampfrichterGegner() {
    return kampfrichterGegner;
  }

  public void setKampfrichterGegner(String kampfrichterGegner) {
    this.kampfrichterGegner = kampfrichterGegner;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(String ort) {
    this.ort = ort;
  }

  public String getSpielArt() {
    return spielArt;
  }

  public void setSpielArt(String spielArt) {
    this.spielArt = spielArt;
  }
}
