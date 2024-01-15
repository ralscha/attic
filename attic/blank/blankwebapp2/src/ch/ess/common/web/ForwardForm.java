package ch.ess.common.web;

import org.apache.struts.action.ActionForm;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:14 $  
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