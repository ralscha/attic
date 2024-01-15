package ch.sr.lottowin.resource;

import java.util.*;

import javax.servlet.*;

import org.apache.struts.action.*;
import org.apache.struts.config.*;

import ch.sr.lottowin.*;

public class Init implements PlugIn {
  
  private String dbURL;
  private String dbUser;
  private String dbPassword;
  private String driver;
  private String type;

  public void destroy() {
    //no action
  }

  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
    Locale.setDefault(new Locale("de", "CH"));
    //Debug.setDebugging(Debugger.DATABASE+Debugger.PERSISTENCE+Debugger.DEEP, false, false); 
    InitLog.init(servlet.getServletContext());
    //Init DB    
    DbPool.initPool("resource:/schrepository/lottowin.schema", type, driver, dbURL, dbUser, dbPassword);
  }


  public String getDbPassword() {
    return dbPassword;
  }

  public String getDbURL() {
    return dbURL;
  }

  public String getDbUser() {
    return dbUser;
  }



  public void setDbPassword(String dbPassword) {
    this.dbPassword = dbPassword;
  }

  public void setDbURL(String dbURL) {
    this.dbURL = dbURL;
  }

  public void setDbUser(String dbUser) {
    this.dbUser = dbUser;
  }


  

  public String getDriver() {
    return driver;
  }

  public String getType() {
    return type;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public void setType(String type) {
    this.type = type;
  }

}