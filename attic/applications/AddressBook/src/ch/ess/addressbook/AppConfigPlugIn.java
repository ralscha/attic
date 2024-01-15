

package ch.ess.addressbook;

import java.util.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResourcesInitializer;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ApplicationConfig;
import com.objectmatter.bsf.*;
import ch.ess.util.pool.*;


public class AppConfigPlugIn implements PlugIn {

 
  private String schema = null; 
  private String dbURL = null;
  private String dbUser = null;
  private String dbPassword = null;
  

  public void setSchema(String schema) {
    this.schema = schema; 
  }

  public void setDbURL(String dbURL) {
    this.dbURL = dbURL; 
  }

  public void setDbUser(String dbUser) {
    this.dbUser = dbUser; 
  }

  public void setDbPassword(String dbPassword) {
    this.dbPassword = dbPassword; 
  }
  public String getSchema() {
    return (this.schema); 
  }

  public String getDbURL() {
    return (this.dbURL); 
  }

  public String getDbUser() {
    return (this.dbUser); 
  }

  public String getDbPassword() {
    return (this.dbPassword); 
  }

  public void init(ApplicationConfig config) throws ServletException {

    Locale.setDefault(new Locale("de", "CH"));

    //Debug.setDebugging(Debugger.DATABASE+Debugger.PERSISTENCE+Debugger.DEEP, false, false); 

    //Init DB    
    PoolManager.initPool(schema, dbURL, dbUser, dbPassword);

  }


  public void destroy() {
  }

  


}
