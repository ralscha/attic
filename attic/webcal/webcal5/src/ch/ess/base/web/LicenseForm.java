package ch.ess.base.web;

import org.apache.struts.action.ActionForm;

public class LicenseForm extends ActionForm {
  private String license;

  public String getLicense() {
    return license;
  }

  public void setLicense(String license) {
    this.license = license;
  }
  
  
}
