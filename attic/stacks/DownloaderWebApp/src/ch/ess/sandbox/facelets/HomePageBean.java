package ch.ess.sandbox.facelets;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

/**
 * JSF backing bean for the view document homepage_chp3.xhtml.
 * This class is example 3-4 in the Facelets Shortcut.
 * @author Robert Swarr
 */
@Name("homePageBean")
public class HomePageBean {

  @Logger
  private Log log;
  
  private String motd = "Menu of the Days";
  private String chapter3Legend = "Chapter 3 Homepage";


  /**
   * Accessor method for the motd property.
   * An EL expression in homepage_chp3 binds to this property. 
   * @return String
   */
  public String getMotd() {
    log.debug("getMotd");
    return motd;    
  }

  /**
   * Mutator method for the motd property.
   * An EL expression in homepage_chp3 binds to this property. 
   * @param motd String
   */
  public void setMotd(String motd) {
    this.motd = motd;
  }

  /**
   * Accessor method for the motd property.
   * An EL expression in homepage_chp3 binds to this property. 
   * @return String chapter3Legend
   */
  public String getChapter3Legend() {
    return chapter3Legend;
  }

  /**
   * Mutator method for the motd property.
   * An EL expression in homepage_chp3 binds to this property. 
   * @param chapter3Legend String
   */
  public void setChapter3Legend(String chapter3Legend) {
    this.chapter3Legend = chapter3Legend;
  }

}
