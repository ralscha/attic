import org.apache.struts.action.ActionForm;

public final class Rekrutierung implements ActionForm  {

  private String pensum = "";
  private boolean swisscomerfahrung;
  private String projekt = "";
  private String termin = "";
  private String save = "";
  private String taetigkeitsgebiete = "";
  private String skills = "";
  private String beschreibung = "";
  private String bemerkung = "";

  public String getPensum() {
    return pensum;
  }

  public void setPensum(String pensum) {
    if (pensum != null)
      this.pensum = pensum;
    else
      this.pensum = "";
  }

  public boolean getSwisscomerfahrung() {
    return swisscomerfahrung;
  }

  public void setSwisscomerfahrung(boolean swisscomerfahrung) {
    this.swisscomerfahrung = swisscomerfahrung;
  }

  public String getProjekt() {
    return projekt;
  }

  public void setProjekt(String projekt) {
    if (projekt != null)
      this.projekt = projekt;
    else
      this.projekt = "";
  }

  public String getTermin() {
    return termin;
  }

  public void setTermin(String termin) {
    if (termin != null)
      this.termin = termin;
    else
      this.termin = "";
  }

  public String getSave() {
    return save;
  }

  public void setSave(String save) {
    if (save != null)
      this.save = save;
    else
      this.save = "";
  }

  public String getTaetigkeitsgebiete() {
    return taetigkeitsgebiete;
  }

  public void setTaetigkeitsgebiete(String taetigkeitsgebiete) {
    if (taetigkeitsgebiete != null)
      this.taetigkeitsgebiete = taetigkeitsgebiete;
    else
      this.taetigkeitsgebiete = "";
  }

  public String getSkills() {
    return skills;
  }

  public void setSkills(String skills) {
    if (skills != null)
      this.skills = skills;
    else
      this.skills = "";
  }

  public String getBeschreibung() {
    return beschreibung;
  }

  public void setBeschreibung(String beschreibung) {
    if (beschreibung != null)
      this.beschreibung = beschreibung;
    else
      this.beschreibung = "";
  }

  public String getBemerkung() {
    return bemerkung;
  }

  public void setBemerkung(String bemerkung) {
    if (bemerkung != null)
      this.bemerkung = bemerkung;
    else
      this.bemerkung = "";
  }

}
