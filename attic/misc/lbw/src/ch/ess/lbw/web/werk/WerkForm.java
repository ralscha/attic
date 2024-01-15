package ch.ess.lbw.web.werk;

import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.web.AbstractForm;

public class WerkForm extends AbstractForm {
  
  private String name;
  

  @ValidatorField(key="werk.name", required=true)
  public void setName(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
  

}
