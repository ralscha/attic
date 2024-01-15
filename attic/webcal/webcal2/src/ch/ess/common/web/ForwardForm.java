package ch.ess.common.web;

import org.apache.struts.action.ActionForm;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:50 $  
  * 
  * @struts.form name="forwardForm" 
  */
public class ForwardForm extends ActionForm {

  private String path;

  public String getPath() {
    return path;
  }
  public void setPath(String path) {
    this.path = path;
  }
}