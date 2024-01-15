package ch.ess;

import java.io.InputStream;
import org.jboss.seam.annotations.Name;

@Name("inputData")
public class InputData {

  private String bestellnummer;
  private String vertragsnummer;
  private String von;
  private String bis;
  private String outputFormat;
  private InputStream template;

  public String getBestellnummer() {
    return bestellnummer;
  }

  public void setBestellnummer(String bestellnummer) {
    this.bestellnummer = bestellnummer;
  }

  public String getVertragsnummer() {
    return vertragsnummer;
  }

  public void setVertragsnummer(String vertragsnummer) {
    this.vertragsnummer = vertragsnummer;
  }

  public String getVon() {
    return von;
  }

  public void setVon(String von) {
    this.von = von;
  }

  public String getBis() {
    return bis;
  }

  public void setBis(String bis) {
    this.bis = bis;
  }

  public void setOutputFormat(String outputFormat) {
    this.outputFormat = outputFormat;
  }

  public String getOutputFormat() {
    return outputFormat;
  }

  public void setTemplate(InputStream template) {
    this.template = template;
  }

  public InputStream getTemplate() {
    return template;
  }

}
