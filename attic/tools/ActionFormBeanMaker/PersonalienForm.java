import javax.servlet.http.*;
import org.apache.struts.action.*;

public final class PersonalienForm extends ActionForm  {

  private String titel;
  private String name;
  private String vorname;
  private String strasse;
  private String plz;
  private String ort;
  private String land;
  private String telefon;
  private String telefonmobil;
  private String fax;
  private String email;
  private String geburts;
  private String sozial;
  private String anrede;

  public PersonalienForm() {
    reset();
  }

  public String getTitel() {
    return titel;
  }

  public void setTitel(String titel) {
    this.titel = titel;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public String getStrasse() {
    return strasse;
  }

  public void setStrasse(String strasse) {
    this.strasse = strasse;
  }

  public String getPlz() {
    return plz;
  }

  public void setPlz(String plz) {
    this.plz = plz;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(String ort) {
    this.ort = ort;
  }

  public String getLand() {
    return land;
  }

  public void setLand(String land) {
    this.land = land;
  }

  public String getTelefon() {
    return telefon;
  }

  public void setTelefon(String telefon) {
    this.telefon = telefon;
  }

  public String getTelefonmobil() {
    return telefonmobil;
  }

  public void setTelefonmobil(String telefonmobil) {
    this.telefonmobil = telefonmobil;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGeburts() {
    return geburts;
  }

  public void setGeburts(String geburts) {
    this.geburts = geburts;
  }

  public String getSozial() {
    return sozial;
  }

  public void setSozial(String sozial) {
    this.sozial = sozial;
  }

  public String getAnrede() {
    return anrede;
  }

  public void setAnrede(String anrede) {
    this.anrede = anrede;
  }

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    reset();
  }

  private void reset() {
    titel = null;
    name = null;
    vorname = null;
    strasse = null;
    plz = null;
    ort = null;
    land = null;
    telefon = null;
    telefonmobil = null;
    fax = null;
    email = null;
    geburts = null;
    sozial = null;
    anrede = null;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();
    //errors.add("joker", new ActionError("joker.no.input"));
    //errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("joker.wrong.input"));
    return errors;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer(500);
    buffer.append(super.toString()).append(";");
    buffer.append("titel = ").append(this.titel).append(";");
    buffer.append("name = ").append(this.name).append(";");
    buffer.append("vorname = ").append(this.vorname).append(";");
    buffer.append("strasse = ").append(this.strasse).append(";");
    buffer.append("plz = ").append(this.plz).append(";");
    buffer.append("ort = ").append(this.ort).append(";");
    buffer.append("land = ").append(this.land).append(";");
    buffer.append("telefon = ").append(this.telefon).append(";");
    buffer.append("telefonmobil = ").append(this.telefonmobil).append(";");
    buffer.append("fax = ").append(this.fax).append(";");
    buffer.append("email = ").append(this.email).append(";");
    buffer.append("geburts = ").append(this.geburts).append(";");
    buffer.append("sozial = ").append(this.sozial).append(";");
    buffer.append("anrede = ").append(this.anrede).append(";");
    return buffer.toString();
  }

}
