package ch.sr.lottowin.action;

import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;

import ch.sr.lottowin.*;

import com.objectmatter.bsf.*;

public class BaseAction extends Action {

  protected static final Logger LOG = Logger.getLogger(BaseAction.class.getName());

  public BaseAction() {
    //no action
  }

  protected Database getDb(HttpServletRequest request) {
    return (Database)request.getAttribute(Constants.DB_KEY);
  }

 

}
