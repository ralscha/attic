package ch.ess.common.web;

import org.apache.struts.action.ActionForm;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $  
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