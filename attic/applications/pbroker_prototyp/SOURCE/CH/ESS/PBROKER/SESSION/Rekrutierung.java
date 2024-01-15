package ch.ess.pbroker.session;

import org.apache.struts.action.ActionForm;
import ch.ess.pbroker.db.*;

public final class Rekrutierung implements ActionForm  {

  private String pensum = "0";
  private String projekt = "";
  private String bistermin = "";
  private String vontermin = "";
  private String taetigkeitsgebiete = "";
  private String skills = "";
  private String bemerkung = "";
  private String ansprech = "";
  private String ansprechtel = "";
  private String ansprechemail = "";
  private String oe = "";
  private String aufgaben = "";
  private String offertebis = "";

  public Rekrutierung(Benutzer benutzer) {
    ansprech = benutzer.getVorname() + " " + benutzer.getNachname();
  }

  public String getPensum() {
    return pensum;
  }

  public void setPensum(String pensum) {
    if (pensum != null)
      this.pensum = pensum;
    else
      this.pensum = "";
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

  public String getVontermin() {
    return vontermin;
  }

  public void setVontermin(String vontermin) {
    if (vontermin != null)
      this.vontermin = vontermin;
    else
      this.vontermin = "";
  }

  public String getBistermin() {
    return bistermin;
  }

  public void setBistermin(String bistermin) {
    if (bistermin != null)
      this.bistermin = bistermin;
    else
      this.bistermin = "";
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


  public String getBemerkung() {
    return bemerkung;
  }

  public void setBemerkung(String bemerkung) {
    if (bemerkung != null)
      this.bemerkung = bemerkung;
    else
      this.bemerkung = "";
  }

  public void setAnsprech(String ansprech) {
    if (ansprech != null) 
      this.ansprech = ansprech;
    else
      this.ansprech = "";
  }

  public void setAnsprechtel(String ansprech) {
    if (ansprechtel != null) 
      this.ansprechtel = ansprechtel;
    else
      this.ansprechtel = "";
  }

  public String getAnsprech() {
    return ansprech;
  }

  public String getAnsprechtel() {
    return ansprechtel;
  }


  public void setAnsprechemail(String email) {
    if (ansprechemail != null) 
      this.ansprechemail = email;
    else
      this.ansprechemail = "";
  }
  
  public String getAnsprechemail() {
    return this.ansprechemail;
  }

  public void setOe(String oe) {
    if (oe != null) 
      this.oe = oe;
    else
      this.oe = "";
  }

  public String getOe() {
    return this.oe;
  }

  public void setAufgaben(String aufgaben) {
    if (aufgaben != null) 
      this.aufgaben = aufgaben;
    else
      this.aufgaben = "";
  }

  public String getAufgaben() {
    return this.aufgaben;
  }
  
  public void setOffertebis(String ob) {
    if (offertebis != null) 
      this.offertebis = ob;
    else
      this.offertebis = "";
  }

  public String getOffertebis() {
    return this.offertebis;
  }


}
